package com.example.mywhatsappclone;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.mywhatsappclone.chat.ChatAdapter;
import com.example.mywhatsappclone.chat.ChatItem;
import com.example.mywhatsappclone.chat.ChatItem;
import com.example.mywhatsappclone.user.UserAdapter;
import com.example.mywhatsappclone.user.UserItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainFlowActivity extends AppCompatActivity {
    private Button logout,findUser;
    private RecyclerView recView;
    private RecyclerView.Adapter adapter;

    private ArrayList<ChatItem> chatList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flow);
        findUser=findViewById(R.id.find_user);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener((view)->{
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
            return;
        });
        findUser.setOnClickListener((v)->
        {
            startActivity(new Intent(getApplicationContext(),FindUserActivity.class));
            return;
        });
        checkPermission();
        instantiateRecyclerView();
        getUserChats();
    }

    private void getUserChats() {
        DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot datasnaphot:
                         dataSnapshot.getChildren()) {
                        boolean existsInArray=false;
                        String key=datasnaphot.getKey();
                        for ( ChatItem item:
                           chatList  ) {
                            if(existsInArray=key.equals(item.getChatId()))
                                break;
                        }
                        if(existsInArray)
                            continue;
                        chatList.add(new ChatItem(key));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void instantiateRecyclerView() {
        recView=findViewById(R.id.recyler_view);
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recView.setAdapter(adapter=new ChatAdapter(chatList));
    }

    private void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
    }

}
