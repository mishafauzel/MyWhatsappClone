package com.example.mywhatsappclone;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.mywhatsappclone.user.UserAdapter;
import com.example.mywhatsappclone.user.UserItem;
import com.example.mywhatsappclone.utills.Iso2Phone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        String ISOprefix=getCountryISO();
        Cursor cursor=this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null
        ,null,null);
        while (cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone=phone.replace(" ","");
            phone=phone.replace("-","");
            phone=phone.replace("(","");
            phone=phone.replace(")","");
            if(!String.valueOf(phone.charAt(0)).equals("+"))
            {
                if(phone.length()==11)
                    phone=phone.replaceFirst("8",ISOprefix);
                else
                phone=ISOprefix+phone;}
            UserItem item;
            contactList.add(item=new UserItem("",name,phone));
            getUserDetail(item);


        }

    }

    private void getUserDetail(UserItem item) {
        DatabaseReference userDb= FirebaseDatabase.getInstance().getReference().child("user");
        Query query=userDb.orderByChild("phone").equalTo(item.getPhone());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                String phone="",name="";
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    if(snapshot.child("name").getValue()!=null)
                    name=snapshot.child("name").getValue().toString();
                    Log.i("name is",name);
                    if(snapshot.child("phone").getValue()!=null)
                    phone=snapshot.child("phone").getValue().toString();
                    UserItem user=new UserItem(snapshot.getKey(),name,phone);
                    if(user.getName().equals(user.getPhone()))
                    for (UserItem userItem:contactList
                         ) {
                        if(user.getPhone().equals(userItem.getPhone()))
                        {

                                user.setName(userItem.getName());
                        }

                    }
                    userList.add(user);

                } adapter.notifyDataSetChanged();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String getCountryISO()
    {
        String countryISO=null;
        TelephonyManager telecomManager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        if(telecomManager.getNetworkCountryIso()!=null)
            if(!telecomManager.getNetworkCountryIso().equals(""))
                countryISO=telecomManager.getNetworkCountryIso();
        return Iso2Phone.getPhone(countryISO);
        
    }

    private void instantiateRecyclerView() {
        recView=findViewById(R.id.recyler_view);
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recView.setAdapter(adapter=new UserAdapter(userList));
    }

}
