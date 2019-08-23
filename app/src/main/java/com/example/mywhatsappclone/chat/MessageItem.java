package com.example.mywhatsappclone.chat;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class MessageItem {
    String text,uid,senderUid;
    ArrayList<String> mediaUrlList;

    public MessageItem(String text, String uid, String senderUid,@Nullable ArrayList<String> mediaUrlList) {
        this.text = text;
        this.uid = uid;
        this.senderUid = senderUid;
        this.mediaUrlList=mediaUrlList;

    }
}
