package com.petme.app.view.dash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.HomeOptionsAdapter;
import com.petme.app.databinding.FragmentVetBinding;


public class VetFragment extends BaseFragment<FragmentVetBinding> {

    @NonNull
    @Override
    public FragmentVetBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVetBinding.inflate(inflater, container, false);
    }

}