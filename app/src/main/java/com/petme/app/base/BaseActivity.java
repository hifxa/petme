package com.petme.app.base;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.model.UserModel;
import com.petme.app.utils.Alerts;

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
		inputManager.hideSoftInputFromWindow ( this.getWindow ().getDecorView ().getWindowToken ( ) , InputMethodManager.HIDE_NOT_ALWAYS );
	}
}
