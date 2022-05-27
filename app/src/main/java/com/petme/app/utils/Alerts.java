package com.petme.app.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.petme.app.BuildConfig;
import com.petme.app.interfaces.AlertClicks;

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

	public static void showAlert ( Context mCtx , @Nullable String title , String message , boolean canDismiss , boolean showCancel , AlertClicks clicks ) {
		MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder ( mCtx );
		builder.setTitle ( title == null ? "Message" : title );
		builder.setMessage ( message );
		builder.setCancelable ( canDismiss );
		if ( showCancel ) {
			builder.setNegativeButton ( "Cancel" , ( dialog , which ) -> clicks.negativeClick ( dialog ) );
		}
		builder.setPositiveButton ( "Confirm" , ( dialog , which ) -> clicks.positiveClick ( dialog ) );
		AlertDialog dialog = builder.create ( );

		try {
			if ( dialog.isShowing ( ) ) {
				dialog.dismiss ( );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
		dialog.show ( );
	}
}
