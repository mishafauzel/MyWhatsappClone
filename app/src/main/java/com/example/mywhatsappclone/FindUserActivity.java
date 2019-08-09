package com.example.mywhatsappclone;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {
    private RecyclerView recView;
    private RecyclerView.Adapter adapter;

    private ArrayList<UserItem> contactList =new ArrayList<>();
    private ArrayList<UserItem> userList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        instantiateRecyclerView();
        findUsers();
    }
    private void findUsers()
    {
        Cursor cursor=this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null
        ,null,null);
        while (cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactList.add(new UserItem(name,phone));

        }
        adapter.notifyDataSetChanged();
    }

    private String getCountryISO()
    {
        String countryISO;
        TelephonyManager telecomManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        
    }

    private void instantiateRecyclerView() {
        recView=findViewById(R.id.recyler_view);
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recView.setAdapter(adapter=new UserAdapter(userList));
    }

}
