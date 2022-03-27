package com.example.petme.view.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petme.R;
import com.example.petme.view.dash.DashActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.onboarding_button);
        button.setOnClickListener(view -> openActivity_login1());
    }
    public void openActivity_login1() {
        Intent intent = new Intent(this, DashActivity.class);
        startActivity(intent);
    }
}