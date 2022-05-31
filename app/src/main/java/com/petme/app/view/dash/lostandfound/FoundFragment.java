package com.petme.app.view.dash.lostandfound;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentFoundBinding;


public class FoundFragment extends BaseFragment<FragmentFoundBinding> {

    NavController ctrl;

    public FoundFragment(NavController ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public FragmentFoundBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentFoundBinding.inflate(inflater,container,false);
    }
}