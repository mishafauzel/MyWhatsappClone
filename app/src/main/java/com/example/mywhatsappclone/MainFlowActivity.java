package com.example.mywhatsappclone;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainFlowActivity extends AppCompatActivity {
    private Button logout,findUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flow);
        findUser=findViewById(R.id.find_user);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener((view)->{
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
            return;
        });
        findUser.setOnClickListener((v)->
        {
            startActivity(new Intent(getApplicationContext(),FindUserActivity.class));
            return;
        });
        checkPermission();
    }
    private void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},0);
        }
    }
}
