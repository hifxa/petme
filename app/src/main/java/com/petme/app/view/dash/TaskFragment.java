package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentTaskBinding;

public class TaskFragment extends BaseFragment<FragmentTaskBinding> {

    @Override
    public FragmentTaskBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentTaskBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.header.getBack().setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        bind.header.getTitle().setText("My Tasks");

    }
}