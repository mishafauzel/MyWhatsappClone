package com.example.mywhatsappclone.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywhatsappclone.R;
import com.example.mywhatsappclone.user.UserItem;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    ArrayList<ChatItem> chatList;

    public ChatAdapter(ArrayList<ChatItem> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChatViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
        chatViewHolder.setItem(chatList.get(i));

    }

    @Override
    public int getItemCount() {
        return this.chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder
    {   TextView title;
        LinearLayout layout;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            layout=itemView.findViewById(R.id.layout);
        }

        public void setItem(ChatItem chatItem) {
            title.setText(chatItem.getChatId());
            layout.setOnClickListener((view)->
            {

            });
        }
    }
}
