package com.example.mywhatsappclone.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywhatsappclone.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    ArrayList<MessageItem> messageList;
    private Context context;
    public MessageAdapter(ArrayList<MessageItem> messageList) {
        this.messageList=messageList;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageViewHolder(LayoutInflater.from(context=viewGroup.getContext()).inflate(R.layout.message_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.setItem(messageList.get(i));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView sender,messageText;
        LinearLayout mediaLinearLayout;
        CardView cardView;
        MessageItem item;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sender=itemView.findViewById(R.id.sender);
            cardView=itemView.findViewById(R.id.layout);
            messageText=itemView.findViewById(R.id.message);
            mediaLinearLayout =itemView.findViewById(R.id.media_container);
        }
        public void setItem(MessageItem item)
        {
            this.item=item;
            sender.setText(item.senderUid);
            messageText.setText(item.text);
            if(item.mediaUrlList!=null)
            {
                for (String url:item.mediaUrlList
                     ) {

                }
                mediaLinearLayout.addView(new ImageView(context));

            }
    }}
}
