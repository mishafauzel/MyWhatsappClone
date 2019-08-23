package com.example.mywhatsappclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.mywhatsappclone.chat.MessageAdapter;
import com.example.mywhatsappclone.chat.MessageItem;
import com.example.mywhatsappclone.media.MediaAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagingActivity extends AppCompatActivity {
    private RecyclerView recView,mediaRecView;
    private RecyclerView.Adapter adapter,mediaAdapter;



    private RecyclerView.LayoutManager mLayoutManger;
    private ImageButton mediaButton;
    FloatingActionButton send;
    EditText message;
    String chatId;

    private ArrayList<MessageItem> MessageList =new ArrayList<>();
    private ArrayList<String> uriList=new ArrayList<>();
    private ArrayList<String> mediaIdList =new ArrayList<>();
    int mediaIterator=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        chatId=getIntent().getStringExtra("chatId");

        instantiateRecyclerView();
        instantiateMediaRecyclerView();
        message=findViewById(R.id.messageText);
        send=findViewById(R.id.send);
        mediaButton=findViewById(R.id.add_media);
        getMessages();


        mediaButton.setOnClickListener((view)-> openGallery());
        send.setOnClickListener((v)-> sendMessage());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.i("result",String.valueOf(requestCode));
        if(resultCode==RESULT_OK) {
            if (data.getClipData() == null)
                uriList.add(data.getData().toString());
            else
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    uriList.add(data.getClipData().getItemAt(i).getUri().toString());
                }

                mediaAdapter.notifyDataSetChanged();

        }

    }


    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("image/*");
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR1)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(intent.resolveActivity(this.getPackageManager())!=null)
            startActivityForResult(Intent.createChooser(intent,"media"),123);
    }

    private void getMessages() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("chat").child(chatId);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    Log.i("message","messageAdded");
                    String text="",
                            sender="";
                    ArrayList<String> listOfMedia=null;
                    if(dataSnapshot.child("text").exists())
                        text=dataSnapshot.child("text").getValue().toString();
                    if(dataSnapshot.child("sender").exists())
                        sender=dataSnapshot.child("sender").getValue().toString();
                    if(dataSnapshot.child("media").getChildrenCount()>0) {
                        Iterable<DataSnapshot> mediaSnaphot = dataSnapshot.child("media").getChildren();
                        listOfMedia=new ArrayList<>();
                        for (DataSnapshot snapshot:mediaSnaphot
                             ) {
                            listOfMedia.add( snapshot.getValue().toString());
                            Log.i("url",snapshot.getValue().toString());
                        }
                    }
                    MessageItem mMessage=new MessageItem(text,dataSnapshot.getKey(),sender,listOfMedia);
                    MessageList.add(mMessage);
                    mLayoutManger.scrollToPosition(MessageList.size()-1);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {

           mediaIterator=0;
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("chat").child(chatId).push();

            Map newMessage=new HashMap();
            newMessage.put("sender",FirebaseAuth.getInstance().getUid());
            if(!message.getText().toString().isEmpty())
            newMessage.put("text",message.getText().toString());

            if(!uriList.isEmpty())
            {
                for(String mediaUrl:uriList)
                {
                    String mediaId=databaseReference.child("media").push().getKey();
                    mediaIdList.add(mediaId);
                    final StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference().child("chat").child(chatId)
                            .child(databaseReference.getKey()).child(mediaId);

                    UploadTask uploadTask=firebaseStorage.putFile(Uri.parse(mediaUrl));
                    uploadTask.addOnSuccessListener((taskSnapshot ->
                    firebaseStorage.getDownloadUrl().addOnSuccessListener((uri -> {
                        newMessage.put("/media/"+mediaIdList.get(mediaIterator)+'/',uri.toString());
                        mediaIterator++;
                        if(mediaIterator==mediaIdList.size())
                        {
                            updateMessageDatabase(databaseReference,newMessage);
                        }
                    }))));

                }
            }
            else if(!message.getText().toString().isEmpty())
            {
                updateMessageDatabase(databaseReference,newMessage);
            }
            message.setText(null);

    }
    private void updateMessageDatabase(DatabaseReference messageDb, Map updatingMessage)
    {
        messageDb.updateChildren(updatingMessage);
        message.setText(null);
        mediaIdList.clear();
        uriList.clear();
        mediaAdapter.notifyDataSetChanged();
    }
    private void instantiateRecyclerView() {
        recView=findViewById(R.id.recyler_view);
        recView.setLayoutManager(mLayoutManger=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        recView.setAdapter(adapter=new MessageAdapter(MessageList));
    }
    private void instantiateMediaRecyclerView() {
        mediaRecView=findViewById(R.id.galery_rec_view);
        mediaRecView.setLayoutManager(mLayoutManger=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        mediaRecView.setAdapter(mediaAdapter=new MediaAdapter(uriList));
    }
}
