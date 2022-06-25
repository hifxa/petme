package com.petme.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.permissionx.guolindev.PermissionX;
import com.petme.app.R;
import com.petme.app.model.UserModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Consts;

import java.util.Locale;

public abstract class BaseFragment < BIND extends ViewBinding > extends Fragment {

	protected String TAG = "_TAG_";
	protected Context mCtx;
	protected BIND bind;

	public abstract BIND getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container );

	@Override
	public void onCreate ( @Nullable Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );

		setEnterTransition ( new MaterialFadeThrough ( ) );
		setExitTransition ( new MaterialFadeThrough ( ) );

	}

	@Override
	public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
		try {
			bind = getBind ( inflater , container );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}

		mCtx = inflater.getContext ( );

		if ( getTag ( ) != null ) {
			TAG = getTag ( ).toUpperCase ( Locale.ROOT );
		}

		return bind.getRoot ( );
	}

	protected Intent getImagePicker ( boolean isCamera ) {
		ImagePicker.Builder picker = ImagePicker.Companion.with ( ( Activity ) mCtx );
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

	protected UserModel getUserById ( String id ) {
		final UserModel[] model = { null };

		FireRef.userDbRef.child ( id ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				model[ 0 ] = snap.getValue ( UserModel.class );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );

		return model[ 0 ];
	}

	// this is just a helper method which we can call and pass a callback reference to check if the permissions needed by us are granted or not.
	protected void requestPerms ( PermissionCallback func ) {
		PermissionX.init ( requireActivity ( ) )
				.permissions ( Consts.getPerms ( ) )
				.explainReasonBeforeRequest ( )
				.onExplainRequestReason ( ( scope , deniedList ) -> {
					scope.showRequestReasonDialog ( deniedList , getString ( R.string.rationale ) , "OK" , "Cancel" );
				} )
				.onForwardToSettings ( ( scope , deniedList ) -> {
					scope.showForwardToSettingsDialog ( deniedList , "You need to allow necessary permissions in Settings manually" , "OK" , "Cancel" );
				} )
				.request ( ( allGranted , grantedList , deniedList ) -> {
					if ( allGranted ) {
						Alerts.log ( TAG , "GRANTED : $grantedList" );
						func.onGranted ( true );
					}
					else {
						Alerts.log ( TAG , "DENIED : $deniedList" );
						func.onGranted ( false );
					}
				} );
	}

	// this is callback that tell us if the permissions are granted or not.
	public interface PermissionCallback {
		void onGranted ( boolean granted );
	}

	public static class FireRef {
		public static final StorageReference userProfileImageRef = FirebaseStorage.getInstance ( ).getReference ( "users_profile_images" );
		public static final DatabaseReference userDbRef = FirebaseDatabase.getInstance ( ).getReference ( "users" );

		public static final StorageReference adoptPetImageRef = FirebaseStorage.getInstance ( ).getReference ( "adopt_pet_images" );
		public static final DatabaseReference adoptDbRef = FirebaseDatabase.getInstance ( ).getReference ( "adopt" );

		public static final DatabaseReference taskDbRef = FirebaseDatabase.getInstance ( ).getReference ( "task" );

		public static final DatabaseReference quotesDbRef = FirebaseDatabase.getInstance ( ).getReference ( "quotes" );

		public static final StorageReference foundPetImageRef = FirebaseStorage.getInstance ( ).getReference ( "found_pet_images" );
		public static final DatabaseReference foundDbRef = FirebaseDatabase.getInstance ( ).getReference ( "found" );

		public static final StorageReference chatImagesRef = FirebaseStorage.getInstance ( ).getReference ( "chat_images" );
		public static final DatabaseReference chatListRef = FirebaseDatabase.getInstance ( ).getReference ( "chat_list" );
		public static final DatabaseReference chatRef = FirebaseDatabase.getInstance ( ).getReference ( "chats" );

		public static final StorageReference lostPetImageRef = FirebaseStorage.getInstance ( ).getReference ( "lost_pet_images" );
		public static final DatabaseReference lostDbRef = FirebaseDatabase.getInstance ( ).getReference ( "lost" );

    }

}