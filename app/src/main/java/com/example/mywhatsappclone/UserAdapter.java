package com.example.mywhatsappclone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        UserItem item;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
           name= itemView.findViewById(R.id.name);
           phone=itemView.findViewById(R.id.phone);
        }
        public void setItem(UserItem item)
        {
            this.item=item;
            phone.setText(item.getPhone());
            name.setText(item.getName());
        }
    }
}
