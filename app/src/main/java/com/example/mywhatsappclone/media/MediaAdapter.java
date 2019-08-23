package com.example.mywhatsappclone.media;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mywhatsappclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ImageViewViewHolder> {
    ArrayList<String> uriList;
    Picasso picasso;

    public MediaAdapter(ArrayList<String> uriList) {
        this.uriList = uriList;
        picasso=Picasso.get();
    }

    @NonNull
    @Override
    public ImageViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ImageViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_item,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewViewHolder imageViewViewHolder, int i) {
        imageViewViewHolder.setItem(uriList.get(i));
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class ImageViewViewHolder extends RecyclerView.ViewHolder
    {
        ImageView mediaItem;
        String uri;
        public ImageViewViewHolder(@NonNull View itemView) {
            super(itemView);
            mediaItem=itemView.findViewById(R.id.media_item);
        }

        public void setItem(String s) {

            picasso.load(Uri.parse(s)).into(mediaItem);
        mediaItem.setOnClickListener((v)->
        {

        });
        }


    }
}
