package com.example.mywhatsappclone.chatting.chats.chat;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

public class ChatDataSourse extends PositionalDataSource<ChatItem> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ChatItem> callback) {

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ChatItem> callback) {

    }
}
