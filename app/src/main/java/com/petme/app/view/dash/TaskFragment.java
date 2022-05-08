package com.petme.app.view.dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentTaskBinding;

public class TaskFragment extends BaseFragment<FragmentTaskBinding> {

    @Override
    public FragmentTaskBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentTaskBinding.inflate(inflater,container,false);
    }
}