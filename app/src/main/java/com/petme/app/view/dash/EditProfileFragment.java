package com.petme.app.view.dash;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentEditProfileBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.model.UserModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

@SuppressWarnings ( "ConstantConditions" )
public class EditProfileFragment extends BaseFragment < FragmentEditProfileBinding > {

	Uri userImgUri;

	ActivityResultLauncher < Intent > launcher = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( ) , ( ActivityResult result ) -> {
		if ( result.getResultCode ( ) == RESULT_OK ) {
			userImgUri = result.getData ( ).getData ( );
			// Use the uri to load the image
			bind.userImg.setImageURI ( userImgUri );
		}
		else if ( result.getResultCode ( ) == ImagePicker.RESULT_ERROR ) {

		}
	} );

	@Override
	public FragmentEditProfileBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentEditProfileBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "Edit Profile" );

		bind.userImg.setOnClickListener ( v -> launcher.launch ( getImagePicker ( false ) ) );

		bind.save.setOnClickListener ( v -> {
			if ( userImgUri != null ) {
				uploadImage ( userImgUri );
			}
			else {
				updateProfile ( "" );
			}
		} );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
		fetchUser ( );
	}

	private void fetchUser ( ) {
		FireRef.userDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				UserModel user = snap.getValue ( UserModel.class );

				bind.name.setText ( user.getName ( ) );
				bind.email.setText ( user.getEmail ( ) );
				bind.phone.setText ( user.getPhone ( ) );

				if ( ! user.getImage ( ).isEmpty ( ) ) {
					Picasso.get ( ).load ( user.getImage ( ) ).placeholder ( R.drawable.pet ).error ( R.drawable.pet ).into ( bind.userImg );
				}
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	private void uploadImage ( Uri uri ) {
		hideKeyboard ();
		StorageMetadata metadata = new StorageMetadata.Builder ( ).setContentType ( "image/jpg" ).build ( );
		String name = new Prefs ( mCtx ).getUserId ( ) + "_" + System.currentTimeMillis ( ) + ".jpeg";
		StorageReference ref = FireRef.userProfileImageRef.child ( name );

		ref.putFile ( uri , metadata ).continueWithTask ( task -> {
			if ( ! task.isSuccessful ( ) ) {
				throw task.getException ( );
			}
			return ref.getDownloadUrl ( );
		} ).addOnCompleteListener ( task -> {
			String downloadUri = "";
			if ( task.isSuccessful ( ) ) {
				downloadUri = task.getResult ( ).toString ( );
			}

			updateProfile ( downloadUri );
		} );
	}

	private void updateProfile ( String image ) {
		hideKeyboard ();
		HashMap < String, Object > userMap = new HashMap <> ( );
		userMap.put ( "email" , bind.email.getText ( ).toString ( ).trim ( ) );
		userMap.put ( "phone" , bind.phone.getText ( ).toString ( ).trim ( ) );
		userMap.put ( "name" , bind.name.getText ( ).toString ( ).trim ( ) );
		userMap.put ( "image" , image );

		FireRef.userDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).updateChildren ( userMap ).addOnSuccessListener ( task ->
				Alerts.showAlert ( mCtx , "Success!" , "Your Profile has been updated Successfully" , false , false , new AlertClicks ( ) {
					@Override
					public void positiveClick ( DialogInterface alert ) {
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}

					@Override
					public void negativeClick ( DialogInterface alert ) {
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}
				} ) ).addOnFailureListener ( e -> e.printStackTrace ( )
		);
	}
}