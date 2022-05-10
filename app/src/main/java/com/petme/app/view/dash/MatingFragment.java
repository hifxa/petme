package com.petme.app.view.dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentMatingBinding;

public class MatingFragment extends BaseFragment<FragmentMatingBinding> {

    @Override
    public FragmentMatingBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentMatingBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.header.getBack().setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());
        bind.header.getTitle().setText("Mating");
    }
}