package com.petme.app.view.dash;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentCreateAdoptionBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.HashMap;

@SuppressWarnings ( "ConstantConditions" )
@SuppressLint ( "SetTextI18n" )
public class CreateAdoptionFragment extends BaseFragment < FragmentCreateAdoptionBinding > {

	private Uri petImgUri;
	private final ActivityResultLauncher < Intent > launcher = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( ) , ( ActivityResult result ) -> {
		if ( result.getResultCode ( ) == RESULT_OK ) {
			petImgUri = result.getData ( ).getData ( );
			bind.petImg.setImageTintList ( null );
			bind.petImg.setImageURI ( petImgUri );
		}
		else if ( result.getResultCode ( ) == ImagePicker.RESULT_ERROR ) {
		}
	} );
	private String baseColor = "#E0E0E0";
	private String from = "adopt";

	@Override
	public FragmentCreateAdoptionBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentCreateAdoptionBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		if ( requireArguments ( ) != null ) {
			from = requireArguments ( ).getString ( "from" , "adopt" );
		}

		if ( from.equals ( "adopt" ) ) {
			bind.miscView.setVisibility ( View.VISIBLE );
			bind.contactView.setVisibility ( View.VISIBLE );
			bind.colorView.setVisibility ( View.GONE );
			bind.header.getTitle ( ).setText ( "Adopt Ads" );
			bind.addAdoptionAdv.setText ( "Post Adoption" );
		}
		else {
			bind.miscView.setVisibility ( View.GONE );
			bind.contactView.setVisibility ( View.GONE );
			bind.colorView.setVisibility ( View.VISIBLE );
			bind.header.getTitle ( ).setText ( "Add a Pet" );
			bind.addAdoptionAdv.setText ( "Add Pet" );

			bind.header.getRootLayout ( ).setBackgroundTintList ( ContextCompat.getColorStateList ( mCtx , R.color.primary ) );
			bind.header.getBack ( ).setImageTintList ( ContextCompat.getColorStateList ( mCtx , R.color.onPrimary ) );
			bind.header.getTitle ( ).setTextColor ( ContextCompat.getColorStateList ( mCtx , R.color.onPrimary ) );
			bind.addAdoptionAdv.setBackgroundTintList ( ContextCompat.getColorStateList ( mCtx , R.color.primary ) );
			bind.addAdoptionAdv.setTextColor ( ContextCompat.getColor ( mCtx , R.color.onPrimary ) );
			requireActivity ( ).getWindow ( ).setStatusBarColor ( ContextCompat.getColor ( mCtx , R.color.primary ) );
		}

		bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );

		bind.petColor.setCardBackgroundColor ( Color.parseColor ( baseColor ) );

		bind.petImg.setOnClickListener ( v -> launcher.launch ( getImagePicker ( false ) ) );

		bind.petColor.setOnClickListener ( v -> new MaterialColorPickerDialog
				.Builder ( mCtx )
				.setTitle ( "Pick your Pet's color" )
				.setColorShape ( ColorShape.CIRCLE )
				.setNegativeButton ( "Cancel" )
				.setPositiveButton ( "Select" )
				.setColorSwatch ( ColorSwatch._300 )
				.setDefaultColor ( baseColor )
				.setColorListener ( ( color , colorHex ) -> {
					baseColor = colorHex;
					bind.petColor.setCardBackgroundColor ( Color.parseColor ( colorHex ) );
				} )
				.showBottomSheet ( getChildFragmentManager ( ) ) );

		bind.addAdoptionAdv.setOnClickListener ( v -> {
			if ( bind.petName.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Pet's Name can't be empty" );
				bind.petName.requestFocus ( );
			}
			else if ( bind.petBreed.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Pet's Breed can't be empty" );
				bind.petBreed.requestFocus ( );
			}
			else if ( bind.petAge.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				Alerts.error ( mCtx , "Pet's Age can't be empty" );
				bind.petAge.requestFocus ( );
			}
			else if ( from.equals ( "adopt" ) ) {
				if ( bind.contactInfo.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
					Alerts.error ( mCtx , "Contact Info can't be empty" );
					bind.contactInfo.requestFocus ( );
				}
				else {
					if ( petImgUri != null ) {
						uploadImage ( petImgUri );
					}
					else {
						createAdoptionAd ( "" );
					}
				}
			}
			else {
				if ( petImgUri != null ) {
					uploadImage ( petImgUri );
				}
				else {
					addPet ( baseColor , "" );
				}
			}
		} );
	}

	private void uploadImage ( Uri uri ) {
		StorageMetadata metadata = new StorageMetadata.Builder ( ).setContentType ( "image/jpg" ).build ( );

		String name = System.currentTimeMillis ( ) + ".jpeg";
		StorageReference ref = FireRef.adoptPetImageRef.child ( name );

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

					if ( from.equals ( "adopt" ) ) {
						createAdoptionAd ( downloadUri );
					}
					else {
						addPet ( baseColor , downloadUri );
					}
				} );
	}

	private void createAdoptionAd ( String image ) {
		Alerts.log ( TAG , "lala" );
		HashMap < String, String > adoptMap = new HashMap <> ( );
		adoptMap.put ( "name" , bind.petName.getText ( ).toString ( ).trim ( ) );
		adoptMap.put ( "breed" , bind.petBreed.getText ( ).toString ( ).trim ( ) );
		adoptMap.put ( "age" , bind.petAge.getText ( ).toString ( ).trim ( ) );
		adoptMap.put ( "details" , bind.miscDetails.getText ( ).toString ( ).trim ( ) );
		adoptMap.put ( "contact" , bind.contactInfo.getText ( ).toString ( ).trim ( ) );
		adoptMap.put ( "userId", new Prefs(mCtx).getUserId());
		adoptMap.put ( "image" , image );
		adoptMap.put ( "timestamp" , "" + System.currentTimeMillis ( ) );

		String pushKey = FireRef.adoptDbRef.push ( ).getKey ( );
		FireRef.adoptDbRef.child ( pushKey ).setValue ( adoptMap , ( error , ref ) -> {
			if ( error == null ) {

				Alerts.showAlert ( mCtx , "Success!" , "Adoption Ad Created!" , false , false , new AlertClicks ( ) {
					@Override
					public void positiveClick ( DialogInterface alert ) {
						alert.dismiss ( );
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}

					@Override
					public void negativeClick ( DialogInterface alert ) {
						alert.dismiss ( );
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}
				} );
			}
			else {
				error.toException ( ).printStackTrace ( );
			}
		} );
	}

	private void addPet ( String color , String image ) {

		HashMap < String, String > taskMap = new HashMap <> ( );
		taskMap.put ( "name" , bind.petName.getText ( ).toString ( ).trim ( ) );
		taskMap.put ( "breed" , bind.petBreed.getText ( ).toString ( ).trim ( ) );
		taskMap.put ( "age" , bind.petAge.getText ( ).toString ( ).trim ( ) );
		taskMap.put ( "image" , image );
		taskMap.put ( "color" , color );

		String pushKey = FireRef.userDbRef.push ( ).getKey ( );

		FireRef.userDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).child ( "pets" ).child ( pushKey ).setValue ( taskMap , ( error , ref ) -> {
			if ( error == null ) {
				Alerts.showAlert ( mCtx , "Success!" , "Pet Added!" , false , false , new AlertClicks ( ) {
					@Override
					public void positiveClick ( DialogInterface alert ) {
						alert.dismiss ( );
						Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
					}

					@Override
					public void negativeClick ( DialogInterface alert ) {
						alert.dismiss ( );
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
