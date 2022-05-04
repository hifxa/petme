package com.example.petme.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.petme.R;
import com.example.petme.base.BaseFragment;
import com.example.petme.databinding.LoginTabFragmentBinding;

import androidx.navigation.Navigation;

public class LoginTabFragment extends BaseFragment<LoginTabFragmentBinding> {

    @NonNull
    @Override
    public LoginTabFragmentBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return LoginTabFragmentBinding.inflate(inflater,container,false);
    }

    float v=0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.username.setAlpha(v);
        bind.loginPass.setAlpha(v);
        bind.login.setAlpha(v);
        bind.dontHaveAcc.setAlpha(v);
        bind.signupLink.setAlpha(v);

        bind.username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        bind.loginPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        bind.login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        bind.dontHaveAcc.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        bind.signupLink.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        bind.signupLink.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.goToSignUp));
    }
}
