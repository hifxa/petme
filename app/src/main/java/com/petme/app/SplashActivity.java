package com.petme.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.petme.app.R;
import com.petme.app.databinding.ActivitySplashBinding;
import com.petme.app.view.dash.DashActivity;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding bind;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
        bind = ActivitySplashBinding.inflate ( getLayoutInflater () );
		setContentView (bind.getRoot() );

		getWindow ( ).setStatusBarColor ( ContextCompat.getColor ( this , R.color.background ) );

		bind.onboardingButton.setOnClickListener ( view -> {
            Intent intent = new Intent ( this , DashActivity.class );
            startActivity ( intent );
        } );
	}

}