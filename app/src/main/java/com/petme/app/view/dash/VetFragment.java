package com.petme.app.view.dash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentVetBinding;


public class VetFragment extends BaseFragment<FragmentVetBinding> {

    private GoogleMap map;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;


    @NonNull
    @Override
    public FragmentVetBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVetBinding.inflate(inflater, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        bind.header.getBack().setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        bind.header.getTitle().setText("Find Nearest Vet");


            Dexter.withContext(requireContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    if (permissionDeniedResponse.isPermanentlyDenied()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                        .setTitle("Permission Required. Go to settings to grant permission")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Ok", null);


                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            })
                    .check();

    }

    private void setContentView() {
    }
}