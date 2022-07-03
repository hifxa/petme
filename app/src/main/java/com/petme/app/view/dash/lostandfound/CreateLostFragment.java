package com.petme.app.view.dash.lostandfound;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentCreateLostBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.utils.Alerts;

import java.util.HashMap;


@SuppressWarnings ( "ConstantConditions" )
public class CreateLostFragment extends BaseFragment < FragmentCreateLostBinding > {

	Uri petImgUri;
	ActivityResultLauncher < Intent > launcher = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( ) , ( ActivityResult result ) -> {
		if ( result.getResultCode ( ) == RESULT_OK ) {
			petImgUri = result.getData ( ).getData ( );
			bind.petImg.setImageTintList ( null );
			bind.petImg.setImageURI ( petImgUri );
		}
		else if ( result.getResultCode ( ) == ImagePicker.RESULT_ERROR ) {
		}
	} );

	@Override
	public FragmentCreateLostBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentCreateLostBinding.inflate ( inflater , container , false );
	}

	@SuppressLint ( "SetTextI18n" )
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "Lost Pet Ad" );

		bind.header.getRootLayout ( ).setBackgroundTintList ( ContextCompat.getColorStateList ( mCtx , R.color.lostFound ) );
		bind.header.getBack ( ).setImageTintList ( ContextCompat.getColorStateList ( mCtx , R.color.white ) );
		bind.header.getTitle ( ).setTextColor ( ContextCompat.getColorStateList ( mCtx , R.color.white ) );

		bind.petImg.setOnClickListener ( view1 -> launcher.launch ( getImagePicker ( false ) ) );

		bind.addLostAdv.setOnClickListener ( view1 -> {
			if ( bind.petAnimal.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Add your pet's name!" );
			}
			else if ( bind.lastSeen.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Add last seen location!" );
			}
			else if ( bind.petBreed.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Add breed too" );
			}
			else if ( bind.contactInfo.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Contact info cant be empty!" );
			}
			else if ( petImgUri != null ) {
				uploadImage ( petImgUri );
			}
			else {
				createLostAdv (
						bind.petAnimal.getText ( ).toString ( ).trim ( ) ,
						bind.petBreed.getText ( ).toString ( ).trim ( ) ,
						bind.lastSeen.getText ( ).toString ( ).trim ( ) ,
						bind.details.getText ( ).toString ( ).trim ( ) ,
						bind.contactInfo.getText ( ).toString ( ).trim ( ) ,
						"" );
			}
		} );
	}

	private void uploadImage ( Uri uri ) {
		hideKeyboard ( );
		StorageMetadata metadata = new StorageMetadata.Builder ( ).setContentType ( "image/jpg" ).build ( );

		String name = System.currentTimeMillis ( ) + ".jpeg";
		StorageReference ref = FireRef.foundPetImageRef.child ( name );

		ref.putFile ( uri , metadata )
				.continueWithTask ( task -> {
					if ( ! task.isSuccessful ( ) ) {
						throw task.getException ( );
					}
					return ref.getDownloadUrl ( );
				} )
				.addOnCompleteListener ( task -> {
					String downloadUri = "";
					if ( task.isSuccessful ( ) ) {
						downloadUri = task.getResult ( ).toString ( );
					}

					createLostAdv (
							bind.petAnimal.getText ( ).toString ( ).trim ( ) ,
							bind.petBreed.getText ( ).toString ( ).trim ( ) ,
							bind.lastSeen.getText ( ).toString ( ).trim ( ) ,
							bind.details.getText ( ).toString ( ).trim ( ) ,
							bind.contactInfo.getText ( ).toString ( ).trim ( ) ,
							downloadUri );
				} );
	}

	public void createLostAdv ( String animal , String breed , String lastSeen , String details , String contact , String img ) {
		hideKeyboard ( );
		HashMap < String, String > lostMap = new HashMap <> ( );
		lostMap.put ( "name" , animal );
		lostMap.put ( "breed" , breed );
		lostMap.put ( "lastSeen" , lastSeen );
		lostMap.put ( "details" , details );
		lostMap.put ( "contact" , contact );
		lostMap.put ( "image" , img );
		lostMap.put ( "timestamp" , "" + System.currentTimeMillis ( ) );

		String pushKey = FireRef.lostDbRef.push ( ).getKey ( );
		FireRef.lostDbRef.child ( pushKey ).setValue ( lostMap , ( error , ref ) -> {
			if ( error == null ) {

				Alerts.showAlert ( mCtx , "Success!" , "Pet Lost Ad Posted!" , false , false , new AlertClicks ( ) {
					@Override
					public void positiveClick ( DialogInterface alert ) {
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}

					@Override
					public void negativeClick ( DialogInterface alert ) {
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}
				} );
			}
			else {
				error.toException ( ).printStackTrace ( );
			}
		} );
	}

}