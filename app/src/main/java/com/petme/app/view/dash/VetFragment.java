package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentVetBinding;


public class VetFragment extends BaseFragment<FragmentVetBinding> {

    private GoogleMap map;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;

    @Override
    public FragmentVetBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVetBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.header.getBack().setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        bind.header.getTitle().setText("Find Nearest Vet");

    }

}