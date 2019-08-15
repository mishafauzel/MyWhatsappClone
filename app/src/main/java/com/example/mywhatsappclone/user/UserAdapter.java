package com.example.mywhatsappclone.user;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywhatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<UserItem> userList;

    public UserAdapter(ArrayList<UserItem> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.setItem(userList.get(i));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView phone,name;
        LinearLayout layout;
        UserItem item;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
           name= itemView.findViewById(R.id.name);
           phone=itemView.findViewById(R.id.phone);
            layout=itemView.findViewById(R.id.layout);
        }
        public void setItem(UserItem item)
        {
            this.item=item;
            phone.setText(item.getPhone());
            name.setText(item.getName());
            layout.setOnClickListener((view)->
            {
                String key= FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user").child(item.getUid()).child("chat").child(key).setValue(true);
                ((Activity) view.getContext()).finish();
            });
        }
    }
}
