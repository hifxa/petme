package com.petme.app.utils;

import android.content.Context;
import android.util.Log;

import com.petme.app.BuildConfig;

import es.dmoral.toasty.Toasty;

public class Alerts {

	public static void error ( Context mCtx , String message ) {
		Toasty.error ( mCtx , message , Toasty.LENGTH_SHORT , false ).show ( );
	}

	public static void success ( Context mCtx , String message ) {
		Toasty.success ( mCtx , message , Toasty.LENGTH_SHORT , false ).show ( );
	}

	public static void log ( String TAG , String message ) {
		String logStart = "\n\n<----------------------( LOG START )---------------------->\n\n";
		String logEnd = "\n\n<----------------------( LOG END )---------------------->\n\n";

		if ( BuildConfig.DEBUG ) {
			Log.d ( TAG , logStart + message + logEnd );
		}
	}

}
