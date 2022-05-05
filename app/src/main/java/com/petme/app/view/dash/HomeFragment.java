package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.petme.databinding.FragmentHomeBinding;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.HomeOptionsAdapter;
import com.petme.app.view.custom.FadingTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends BaseFragment < FragmentHomeBinding > {

	List < String > homeOptions = new ArrayList <> ( Arrays.asList ( "vet" , "shop" , "tasks" , "lost/Found" , "adopt" , "mating" ) );
	HomeOptionsAdapter mAdapter;

	@NonNull
	@Override
	public FragmentHomeBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentHomeBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		mAdapter = new HomeOptionsAdapter ( mCtx , homeOptions );
		bind.optionsRecycler.setAdapter ( mAdapter );

		bind.fadeText.setTimeout(5, TimeUnit.SECONDS );

	}
}