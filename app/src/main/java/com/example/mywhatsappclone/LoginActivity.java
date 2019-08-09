package com.example.mywhatsappclone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private EditText code,phoneNumber;
    private Button mSend;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userIsLogin();
        code=this.findViewById(R.id.code);
        phoneNumber=this.findViewById(R.id.phone_number);
        mSend =findViewById(R.id.send);

        mSend.setOnClickListener((view)->
        {
            if(mVerificationId==null)
            startPhoneNumberVerification();
            else
                verifyPhoneNumberWithCode(mVerificationId,code.getText().toString());
        });
        mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
                Log.i("Verification","succses");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("Verification",e.getMessage());
            }

            @Override
            public void onCodeSent(String mVerificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(mVerificationId, forceResendingToken);
                LoginActivity.this.mVerificationId=mVerificationId;

                mSend.setText("Verify Code");
                Log.i("Verification","succsesWithCode");

            }
        };
    }

    private void verifyPhoneNumberWithCode(String verificationId,String verificationCode)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,verificationCode);
        signInWithPhoneAuthCredentials(credential);
    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this,task -> {
            if(task.isSuccessful())
            {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists())
                            {
                                Map<String,Object> userMap=new HashMap<>();
                                userMap.put("phone",user.getPhoneNumber());
                                userMap.put("name",user.getPhoneNumber());
                                reference.updateChildren(userMap);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
                userIsLogin();
        });
    }

    private void userIsLogin() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainFlowActivity.class));
            finish();
            return;
        }
    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber.getText().toString(),60, TimeUnit.SECONDS,this,mCallback);
    }
}
