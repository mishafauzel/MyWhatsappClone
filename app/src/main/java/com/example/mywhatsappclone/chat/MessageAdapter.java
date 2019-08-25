package com.example.mywhatsappclone.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywhatsappclone.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    ArrayList<MessageItem> messageList;
    private Context context;
    private Picasso picasso;
    public MessageAdapter(ArrayList<MessageItem> messageList) {
        this.messageList=messageList;
        picasso=Picasso.get();
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
        ArrayList<ImageView> imageViewList;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sender=itemView.findViewById(R.id.sender);
            cardView=itemView.findViewById(R.id.layout);
            messageText=itemView.findViewById(R.id.message);
            mediaLinearLayout =itemView.findViewById(R.id.media_container);
            imageViewList=new ArrayList<>(10);
        }
        public void setItem(MessageItem item)
        {
            this.item=item;
            sender.setText(item.senderUid);
            messageText.setText(item.text);

            if(item.mediaUrlList!=null)
            {
                mediaLinearLayout.setVisibility(View.VISIBLE);
                int sizeDifference=0;
                if((sizeDifference=item.mediaUrlList.size()-imageViewList.size())>0)
                    for(int i=0;i<sizeDifference;i++)
                    {
                        ImageView mImView;
                        mediaLinearLayout.addView(mImView=new ImageView(context));
                        imageViewList.add(mImView);
                    }
                else
                    if(sizeDifference<0)
                    {for(int i=imageViewList.size()-1;i<item.mediaUrlList.size();i++)
                    {
                        imageViewList.get(i).setVisibility(View.GONE);
                    }}
                int listIterator=0;
                for (String url:item.mediaUrlList
                     ) {
                    picasso.load(url).into(imageViewList.get(listIterator));
                    imageViewList.get(listIterator).setVisibility(View.VISIBLE);
                    final int startPosition=listIterator;
                    imageViewList.get(listIterator).setOnClickListener((view)->
                    {

                    });
                    listIterator++;

                }


            }
            else
                mediaLinearLayout.setVisibility(View.GONE);
    }}
}
