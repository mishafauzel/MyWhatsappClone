package com.example.mywhatsappclone.chatting.chats.chat;

import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywhatsappclone.chatting.messaging.MessagingActivity;
import com.example.mywhatsappclone.R;
import com.example.mywhatsappclone.databinding.ChatItemBinding;

import java.util.ArrayList;

public class ChatAdapter extends PagedListAdapter<ChatItem, ChatAdapter.ChatViewHolder> {


    protected ChatAdapter(@NonNull DiffUtil.ItemCallback<ChatItem> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        return new ChatViewHolder(DataBindingUtil.inflate(inflater,R.layout.chat_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
        chatViewHolder.setItem(getItem(i));

    }



    public class ChatViewHolder extends RecyclerView.ViewHolder
    {
        ChatItemBinding binding;
        public ChatViewHolder(@NonNull ChatItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }

        public void setItem(ChatItem chatItem) {
            binding.setChat(chatItem);
            binding.executePendingBindings();
        }
    }
}
