package com.petme.app.view.dash.lostandfound;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentCreateFoundBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.utils.Alerts;

import java.util.HashMap;


public class CreateFoundFragment extends BaseFragment <FragmentCreateFoundBinding> {

    Uri petImgUri;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult ( ) , (ActivityResult result ) -> {
        if ( result.getResultCode ( ) == RESULT_OK ) {
            petImgUri = result.getData ( ).getData ( );

            bind.petImg.setImageURI ( petImgUri );
        }
        else if ( result.getResultCode ( ) == ImagePicker.RESULT_ERROR ) {
        }
    } );

    @Override
    public FragmentCreateFoundBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentCreateFoundBinding.inflate(inflater, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated ( view , savedInstanceState );

        bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );
        bind.header.getTitle ( ).setText ( "Found Pets" );

        bind.header.getRootLayout().setBackgroundTintList(ContextCompat.getColorStateList(mCtx,R.color.lostFound));
        bind.header.getBack ( ).setImageTintList(ContextCompat.getColorStateList(mCtx,R.color.white));
        bind.header.getTitle ( ).setTextColor(ContextCompat.getColorStateList(mCtx,R.color.white));

        bind.petImg.setOnClickListener ( v -> launcher.launch ( getImagePicker ( false ) ) );

        bind.addFoundAdv.setOnClickListener ( v -> {
            if ( petImgUri != null ) {
                uploadImage ( petImgUri );
            }
            else if(bind.petAnimal.getText().toString().isEmpty()){
                Alerts.error(mCtx, "Add a type!");
            }
            else if(bind.foundLocation.getText().toString().isEmpty()){
                Alerts.error(mCtx, "Add location");
            }
            else if(bind.contactInfo.getText().toString().isEmpty()){
                Alerts.error(mCtx, "Contact info is required!");
            }
            else {
                createFoundAdv (
                        bind.petAnimal.getText ( ).toString ( ).trim ( ) ,
                        bind.petBreed.getText ( ).toString ( ).trim ( ) ,
                        bind.foundLocation.getText ( ).toString ( ).trim ( ) ,
                        bind.currentlyAt.getText ( ).toString ( ).trim ( ) ,
                        bind.contactInfo.getText ( ).toString ( ).trim ( ) , "" );
            }
        } );
    }

    private void createFoundAdv ( String animal , String breed , String foundLoc , String currentlyAt , String contact , String image ) {
        HashMap<String, String> foundMap = new HashMap<>();
        foundMap.put("name", animal);
        foundMap.put("breed", breed);
        foundMap.put("foundLocation", foundLoc);
        foundMap.put("currentLocation", currentlyAt);
        foundMap.put("contact", contact);
        foundMap.put("image", image);
        foundMap.put("timestamp", "" + System.currentTimeMillis());

        String pushKey = FireRef.foundDbRef.push ( ).getKey ( );
        FireRef.foundDbRef.child ( pushKey ).setValue ( foundMap , ( error , ref ) -> {
            if ( error == null ) {

                Alerts.showAlert ( mCtx , "Success!" , "Pet Found Ad Posted!" , false , false , new AlertClicks( ) {
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

    private void uploadImage ( Uri uri ) {
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

                    createFoundAdv (
                            bind.petAnimal.getText ( ).toString ( ).trim ( ) ,
                            bind.petBreed.getText ( ).toString ( ).trim ( ) ,
                            bind.foundLocation.getText ( ).toString ( ).trim ( ) ,
                            bind.currentlyAt.getText ( ).toString ( ).trim ( ) ,
                            bind.contactInfo.getText ( ).toString ( ).trim ( ) ,
                            downloadUri );
                } );
    }

}