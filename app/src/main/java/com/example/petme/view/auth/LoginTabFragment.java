package com.example.petme.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.petme.R;

public class LoginTabFragment extends Fragment {

    TextView username;
    TextView password;
    TextView forgotPassword;
    Button login;
    float v=0;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);


        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        forgotPassword = root.findViewById(R.id.forgotPassword);
        login = root.findViewById(R.id.login);

        username.setAlpha(v);
        password.setAlpha(v);
        forgotPassword.setAlpha(v);
        login.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgotPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
