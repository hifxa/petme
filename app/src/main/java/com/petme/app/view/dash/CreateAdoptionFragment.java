package com.petme.app.view.dash;

import static android.app.Activity.RESULT_OK;

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
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentCreateAdoptionBinding;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.HashMap;

@SuppressWarnings ( "ConstantConditions" )
public class CreateAdoptionFragment extends BaseFragment < FragmentCreateAdoptionBinding > {

	Uri petImgUri;

	ActivityResultLauncher < Intent > launcher = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( ) , ( ActivityResult result ) -> {
		if ( result.getResultCode ( ) == RESULT_OK ) {
			petImgUri = result.getData ( ).getData ( );
			// Use the uri to load the image
			bind.petImg.setImageURI ( petImgUri );
		}
		else if ( result.getResultCode ( ) == ImagePicker.RESULT_ERROR ) {
		}
	} );

	@Override
	public FragmentCreateAdoptionBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentCreateAdoptionBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "Adopt Ads" );

		bind.petImg.setOnClickListener ( v -> launcher.launch ( getImagePicker ( false ) ) );

		bind.addAdoptionAdv.setOnClickListener ( v -> {
			if ( petImgUri != null ) {
				uploadImage ( petImgUri );
			}
			else {
				createAdoptAdv (
						bind.petName.getText ( ).toString ( ).trim ( ) ,
						bind.petBreed.getText ( ).toString ( ).trim ( ) ,
						bind.age.getText ( ).toString ( ).trim ( ) ,
						bind.miscDetails.getText ( ).toString ( ).trim ( ) ,
						bind.contactInfo.getText ( ).toString ( ).trim ( ) , "" );
			}
		} );
	}

	private void uploadImage ( Uri uri ) {
		StorageMetadata metadata = new StorageMetadata.Builder ( ).setContentType ( "image/jpg" ).build ( );

		String name = System.currentTimeMillis ( ) + ".jpeg";
		StorageReference ref = FireRef.adoptPetImageRef.child ( name );

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

			createAdoptAdv (
					bind.petName.getText ( ).toString ( ).trim ( ) ,
					bind.petBreed.getText ( ).toString ( ).trim ( ) ,
					bind.age.getText ( ).toString ( ).trim ( ) ,
					bind.miscDetails.getText ( ).toString ( ).trim ( ) ,
					bind.contactInfo.getText ( ).toString ( ).trim ( ) ,
					downloadUri );
		} );
	}

	private void createAdoptAdv ( String name , String breed , String age , String details , String contact , String image ) {
		HashMap < String, String > adoptMap = new HashMap <> ( );
		adoptMap.put ( "name" , name );
		adoptMap.put ( "breed" , breed );
		adoptMap.put ( "age" , age );
		adoptMap.put ( "details" , details );
		adoptMap.put ( "contact" , contact );

		if ( ! image.isEmpty ( ) ) {
			adoptMap.put ( "image" , image );
		}

		adoptMap.put ( "timestamp" , "" + System.currentTimeMillis ( ) );

		String pushKey = FireRef.adoptDbRef.push ( ).getKey ( );
		FireRef.adoptDbRef.child ( pushKey ).setValue ( adoptMap , ( error , ref ) -> {
			if ( error == null ) {
				Alerts.success ( mCtx , "Task Added Successfully!" );
			}
			else {
				error.toException ( ).printStackTrace ( );
			}
		} );
	}
}
