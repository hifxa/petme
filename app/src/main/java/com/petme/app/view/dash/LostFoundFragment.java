package com.petme.app.view.dash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.LostPagerAdapter;
import com.petme.app.databinding.FragmentLostFoundBinding;
import com.petme.app.view.dash.lostandfound.FoundFragment;

public class LostFoundFragment extends BaseFragment<FragmentLostFoundBinding> {

    Lifecycle mLifecycle;
    FragmentManager mManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    LostPagerAdapter mAdapter;


    @Override
    public FragmentLostFoundBinding getBind(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLostFoundBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind.header.getBack().setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());
        bind.header.getTitle().setText("Lost and Found");

        mManager = getActivity().getSupportFragmentManager();
        mAdapter = new LostPagerAdapter(mManager, mLifecycle);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void attach() {
    }

}