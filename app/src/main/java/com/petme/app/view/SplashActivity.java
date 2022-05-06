package com.petme.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.petme.app.R;
import com.petme.app.view.auth.AuthActivity;
import com.petme.app.view.dash.DashActivity;

public class SplashActivity extends AppCompatActivity {

    com.petme.app.databinding.ActivitySplashBinding bind;
	private Button button;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
        bind = com.petme.app.databinding.ActivitySplashBinding.inflate ( getLayoutInflater () );
		setContentView (bind.getRoot() );

		getWindow ( ).setStatusBarColor ( ContextCompat.getColor ( this , R.color.surface ) );

		bind.onboardingButton.setOnClickListener ( view -> {
            Intent intent = new Intent ( this , AuthActivity.class );
            startActivity ( intent );
        } );
	}
}