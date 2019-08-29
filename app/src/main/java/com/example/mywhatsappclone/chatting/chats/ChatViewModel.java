package com.example.mywhatsappclone.chatting.chats;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mywhatsappclone.Login.LoginActivity;
import com.example.mywhatsappclone.R;
import com.example.mywhatsappclone.UserSelection.FindUserActivity;
import com.example.mywhatsappclone.UserSelection.user.UserItem;
import com.example.mywhatsappclone.chatting.chats.chat.ChatAdapter;
import com.example.mywhatsappclone.chatting.chats.chat.ChatItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    WeakReference<MainFlowActivity> activity;
    private RecyclerView recView;
    private RecyclerView.Adapter adapter;
    ArrayList<ChatItem> chatList;

    public ChatViewModel(MainFlowActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    public void initOneSignal() {
        OneSignal.startInit(activity.get().getApplicationContext()).init();
        OneSignal.setSubscription(true);
        OneSignal.idsAvailable((userId, registrationId) -> {
            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("notificationId").setValue(userId);
        });
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
    }

    public void executeLogOut() {
        OneSignal.setSubscription(false);
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(activity.get().getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.get().startActivity(intent);
        activity.get().finish();
    }
    public void executeFindUser(View view)
    {
    activity.get().startActivity(new Intent(activity.get().getApplicationContext(), FindUserActivity.class));
        return;
    }
    public void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.get().requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
    }
    public void instantiateRecyclerView() {
        recView=activity.get().findViewById(R.id.recyler_view);
        recView.setLayoutManager(new LinearLayoutManager(activity.get(),LinearLayoutManager.VERTICAL,false));
        recView.setAdapter(adapter=new ChatAdapter(chatList));
    }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getChatData(String key) {
        DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("chat").child(key).child("info");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String chatId="";
                    if(dataSnapshot.child("id").getValue()!=null)
                        chatId=dataSnapshot.child("id").getValue().toString();
                    ChatItem chatItem=null;
                    for (ChatItem item:chatList
                    ) {
                        if(item.getChatId().equals(chatId))
                        {
                            chatItem=item;
                            break;
                        }
                        else
                            continue;
                    }
                    for (DataSnapshot users:dataSnapshot.child("user").getChildren()
                    ) {
                        UserItem userItem=new UserItem(users.getKey().toString());
                        chatItem.addUser(userItem);
                        getUserData(userItem);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserData(UserItem userItem) {
        DatabaseReference databaseRef=FirebaseDatabase.getInstance().getReference().child("user").child(userItem.getUid());
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    UserItem user=new UserItem(dataSnapshot.getKey());
                    if(dataSnapshot.child("notificationKey").getValue()!=null)

                        user.setNotificationKey(dataSnapshot.child("notificationKey").getValue().toString());
                    for (ChatItem chatItem:chatList) {
                        for (UserItem userItem1:chatItem.getUserList()
                        ) {
                            if(user.getUid().equals(userItem1.getUid()))
                                userItem1.setNotificationKey(user.getNotificationKey());

                        }

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
