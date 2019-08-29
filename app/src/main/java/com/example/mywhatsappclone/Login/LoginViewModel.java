package com.example.mywhatsappclone.Login;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mywhatsappclone.R;
import com.example.mywhatsappclone.chatting.chats.MainFlowActivity;
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

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginViewModel extends ViewModel {
    WeakReference<LoginActivity> activity;
    NavController mNavController;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String mVerificationId;
    public String phoneNumber;
    public String code;
    public ObservableField<String> buttonText=new ObservableField<>();

    public LoginViewModel(LoginActivity activity) {
        this.activity = new WeakReference<>(activity);
        buttonText.set(activity.getString(R.string.send_code));
        mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
                Log.i("Verification","succses");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginViewModel.this.activity.get().getApplicationContext(),R.string.cannot_login,Toast.LENGTH_LONG);
            }

            @Override
            public void onCodeSent(String mVerificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(mVerificationId, forceResendingToken);
                LoginViewModel.this.mVerificationId=mVerificationId;

               buttonText.set(LoginViewModel.this.activity.get().getString(R.string.verify_code));
                Log.i("Verification","succsesWithCode");

            }
        };
    }

     void userIsLogin() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            mNavController.navigate(R.id.mainFlowActivity3);
            activity.get().finish();
            return;
        }
    }

    void instantiateNavigation() {
        mNavController= Navigation.findNavController(activity.get(), R.id.navHost);
    }
    public void logIn()
    {
        if(mVerificationId==null)
            startPhoneNumberVerification();
        else
            verifyPhoneNumberWithCode(mVerificationId,code);
    }
    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,activity.get(),mCallback);
    }
    private void verifyPhoneNumberWithCode(String verificationId,String verificationCode)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,verificationCode);
        signInWithPhoneAuthCredentials(credential);
    }
    private void signInWithPhoneAuthCredentials(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(activity.get(),task -> {
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
                            userIsLogin();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(activity.get().getApplicationContext(),"Ooops! Something went wrong!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

        });
    }
}
