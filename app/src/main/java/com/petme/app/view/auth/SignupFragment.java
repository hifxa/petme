package com.petme.app.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petme.app.databinding.FragmentSignUpBinding;
import com.petme.app.base.BaseFragment;

public class SignupFragment extends BaseFragment< FragmentSignUpBinding > {

    @NonNull
    @Override
    public FragmentSignUpBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container){
        return FragmentSignUpBinding.inflate(inflater, container, false);
    }

    float v = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.email.setAlpha(v);
        bind.name.setAlpha(v);
        bind.phoneNumber.setAlpha(v);
        bind.signupPass.setAlpha(v);
        bind.confirmPass.setAlpha(v);
        bind.signup.setAlpha(v);

        bind.email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        bind.name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        bind.phoneNumber.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        bind.signupPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        bind.confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        bind.signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


    }
}
