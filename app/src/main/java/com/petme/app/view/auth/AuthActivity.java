package com.petme.app.view.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.petme.app.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

	ActivityAuthBinding bind;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );

		bind = ActivityAuthBinding.inflate ( getLayoutInflater ( ) );
		setContentView ( bind.getRoot ( ) );
	}
}