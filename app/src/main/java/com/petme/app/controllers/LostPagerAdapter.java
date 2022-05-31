package com.petme.app.controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.petme.app.view.dash.LostFoundFragment;
import com.petme.app.view.dash.lostandfound.FoundFragment;
import com.petme.app.view.dash.lostandfound.LostFragment;

public class LostPagerAdapter extends FragmentStateAdapter {

    NavController ctrl;

    public LostPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, NavController ctrl) {
        super(fragmentManager, lifecycle);
        this.ctrl = ctrl;
    }

    public LostPagerAdapter(FragmentManager mManager, Lifecycle mLifecycle) {

        super();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LostFragment(ctrl);
            case 1:
               return new FoundFragment(ctrl);
            default:
                return new LostFragment(ctrl);
        }
    }

    @Override
    public int getItemCount() {

        return 2;
    }
}
