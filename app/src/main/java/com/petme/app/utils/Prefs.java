package com.petme.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
	public static final String USER_ID = "user_id";
	public static final String USER_EMAIL = "user_email";

	Context mCtx;
	SharedPreferences mPrefs;

	public Prefs ( Context mCtx ) {
		this.mCtx = mCtx;
		mPrefs = mCtx.getSharedPreferences ( Consts.SHARED_PREF , Context.MODE_PRIVATE );
	}

	public void putString ( String key , String value ) {
		mPrefs.edit ( ).putString ( key , value ).apply ( );
	}

	public String getString ( String key ) {
		return mPrefs.getString ( key , "" );
	}

	public String getUserId ( ) {
		return mPrefs.getString ( USER_ID , "" );
	}

	public void nukeThemAll ( ) {
		mPrefs.edit ( ).clear ( ).apply ( );
	}
}
