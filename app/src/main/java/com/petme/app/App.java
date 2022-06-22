package com.petme.app;

import android.app.Application;
import android.view.Gravity;

import androidx.core.content.res.ResourcesCompat;

import es.dmoral.toasty.Toasty;

public class App extends Application {

	@Override
	public void onCreate ( ) {
		super.onCreate ( );

//		try {
//			Picasso.setSingletonInstance ( new Picasso.Builder ( getApplicationContext ( ) )
//					.loggingEnabled ( false )
//					.build ( ) );
//		}
//		catch ( Exception e ) {
//			e.printStackTrace ( );
//		}

		Toasty.Config.getInstance ( )
				.setToastTypeface ( ResourcesCompat.getFont ( getApplicationContext ( ) , R.font.poppins ) )
				.tintIcon ( true )
				.setTextSize ( 12 ) // optional
				.allowQueue ( false )
				.setGravity ( Gravity.TOP , 0 , 24 )
				.supportDarkTheme ( true )
				.setRTL ( false )
				.apply ( );
	}
}
