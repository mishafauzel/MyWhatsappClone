package com.example.mywhatsappclone.Login;


import android.databinding.DataBindingUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mywhatsappclone.R;
import com.example.mywhatsappclone.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {


    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLoginViewModel(mLoginViewModel);
        mLoginViewModel.instantiateNavigation();
        mLoginViewModel.userIsLogin();





    }








}
