package com.example.petme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    TextView email;
    TextView name;
    TextView editTextPhone;
    TextView pass;
    TextView confirmPass;
    Button signup;
    float v = 0;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        name = root.findViewById(R.id.name);
        editTextPhone = root.findViewById(R.id.editTextPhone);
        pass = root.findViewById(R.id.pass);
        confirmPass = root.findViewById(R.id.confirmPass);
        signup = root.findViewById(R.id.signup);

        email.setAlpha(v);
        name.setAlpha(v);
        editTextPhone.setAlpha(v);
        pass.setAlpha(v);
        confirmPass.setAlpha(v);
        signup.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        editTextPhone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }
}
