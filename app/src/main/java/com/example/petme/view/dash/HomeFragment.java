package com.example.petme.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.petme.R;
import com.example.petme.base.BaseFragment;
import com.example.petme.databinding.HomeFragmentBinding;

import androidx.navigation.Navigation;

public class HomeFragment extends BaseFragment<HomeFragmentBinding> {

    @NonNull
    @Override
    public HomeFragmentBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return HomeFragmentBinding.inflate(inflater,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind.vetCard
    }
}