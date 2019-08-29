package com.example.mywhatsappclone.chatting.chats.chat;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatItemsSourse {
    public void getUserChats() {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat");
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
                        getChatData(key);
                    }

                }
            }
}
