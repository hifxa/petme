package com.petme.app.base;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.drjacky.imagepicker.ImagePicker;

public abstract class BaseActivity extends AppCompatActivity {

	protected final String TAG = getClass ( ).getSimpleName ( );

	protected Intent getImagePicker ( boolean isCamera ) {
		ImagePicker.Builder picker = ImagePicker.Companion.with ( this );
		picker.cropSquare ( );

		if ( isCamera ) {
			picker.cameraOnly ( );
		}
		else {
			picker.galleryOnly ( );
			picker.galleryMimeTypes ( new String[] {
					"image/png" ,
					"image/jpg" ,
					"image/jpeg" } );
		}

		return picker.createIntent ( );
	}

	protected void hideKeyboard ( ) {
		InputMethodManager inputManager = ( InputMethodManager ) getSystemService ( Context.INPUT_METHOD_SERVICE );
		inputManager.hideSoftInputFromWindow ( getCurrentFocus ( ).getWindowToken ( ) , InputMethodManager.HIDE_NOT_ALWAYS );
	}
}
