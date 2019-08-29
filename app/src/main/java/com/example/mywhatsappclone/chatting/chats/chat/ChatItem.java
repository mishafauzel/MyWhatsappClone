package com.example.mywhatsappclone.chatting.chats.chat;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.mywhatsappclone.UserSelection.user.UserItem;
import com.example.mywhatsappclone.chatting.messaging.MessagingActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatItem implements Serializable {
  String name;
  ArrayList<UserItem> userList = new ArrayList<>();


  public String getChatId() {
    return name;
  }

  public ArrayList<UserItem> getUserList() {
    return userList;
  }

  public ChatItem(String name) {
    this.name = name;

  }

  public void addUser(UserItem userItem) {
    userList.add(userItem);
  }

  public void executeClick(View view) {
    Context context = view.getContext().getApplicationContext();
    Intent intent=new Intent(context, MessagingActivity.class);
    intent.putExtra("chatObject",this);
    context.startActivity(intent);
  }
}
