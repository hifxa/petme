package com.petme.app.view.dash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentVetBinding;


public class VetFragment extends BaseFragment<FragmentVetBinding> {

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

    }
}