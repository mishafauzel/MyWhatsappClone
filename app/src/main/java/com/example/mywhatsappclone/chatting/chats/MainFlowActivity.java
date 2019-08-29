package com.example.mywhatsappclone.chatting.chats;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.mywhatsappclone.Login.LoginActivity;
import com.example.mywhatsappclone.R;
import com.example.mywhatsappclone.UserSelection.FindUserActivity;
import com.example.mywhatsappclone.chatting.chats.chat.ChatAdapter;
import com.example.mywhatsappclone.chatting.chats.chat.ChatItem;
import com.example.mywhatsappclone.UserSelection.user.UserItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.util.ArrayList;

public class MainFlowActivity extends AppCompatActivity {


    ChatViewModel mViewModel;

    private ArrayList<ChatItem> chatList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flow);

        mViewModel.initOneSignal();
        mViewModel.checkPermission();
        mViewModel.instantiateRecyclerView();
       mViewModel.getUserChats();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.log_out:
            {mViewModel.executeLogOut();
            break;}

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }






}
