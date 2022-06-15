package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayoutMediator;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.LostPagerAdapter;
import com.petme.app.databinding.FragmentLostFoundBinding;

public class LostFoundFragment extends BaseFragment < FragmentLostFoundBinding > {

	LostPagerAdapter mAdapter;
	TabLayoutMediator mediator;

	@Override
	public FragmentLostFoundBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentLostFoundBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( view1 -> Navigation.findNavController ( view1 ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "Lost and Found" );

		bind.header.getRootLayout().setBackgroundTintList(ContextCompat.getColorStateList(mCtx, R.color.lostFound));
		bind.header.getBack ( ).setImageTintList(ContextCompat.getColorStateList(mCtx,R.color.white));
		bind.header.getTitle ( ).setTextColor(ContextCompat.getColorStateList(mCtx,R.color.white));

		mAdapter = new LostPagerAdapter ( getChildFragmentManager ( ) , getLifecycle ( ) , Navigation.findNavController ( bind.getRoot ( ) ) );
		bind.viewPager.setAdapter ( mAdapter );

		mediator = new TabLayoutMediator ( bind.tabLayout , bind.viewPager , true , ( tab , position ) -> {
			switch ( position ) {
				case 0:
					tab.setText ( "Lost" );
					break;
				case 1:
					tab.setText ( "Found" );
					break;
			}
		} );
		mediator.attach ( );
	}

	@Override
	public void onDestroy ( ) {
		super.onDestroy ( );
		if ( mediator.isAttached ( ) ) {
			mediator.detach ( );
		}
	}
}