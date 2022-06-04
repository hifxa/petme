package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.AdoptAdapter;
import com.petme.app.databinding.FragmentAdoptBinding;
import com.petme.app.model.AdoptModel;
import com.petme.app.utils.Alerts;

import java.util.ArrayList;
import java.util.List;

public class AdoptFragment extends BaseFragment < FragmentAdoptBinding > {

	List < AdoptModel > mList = new ArrayList <> ( );
	AdoptAdapter mAdapter;

	@Override
	public FragmentAdoptBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentAdoptBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "Adopt" );

		bind.newAdoptButton.setOnClickListener ( v -> {
			Bundle mBundle = new Bundle ( );
			mBundle.putString ( "from" , "adopt" );

			Navigation.findNavController ( v ).navigate ( R.id.createAdoptionFragment , mBundle );
		} );

		mAdapter = new AdoptAdapter ( mCtx , mList , ( pos , type ) -> {
		} );

		bind.taskRecycler.setAdapter ( mAdapter );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
		fetchAdoptions ( );
		getAds ( );
	}

	private void getAds ( ) {

		FireRef.adoptDbRef.addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				mList.clear ( );
				for ( DataSnapshot mData : snap.getChildren ( ) ) {
					mList.add ( mData.getValue ( AdoptModel.class ) );
				}
				mAdapter.notifyDataSetChanged ( );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	private void fetchAdoptions ( ) {
		FireRef.adoptDbRef.addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				mList.clear ( );
				for ( DataSnapshot mData : snap.getChildren ( ) ) {

					Alerts.log ( TAG , "DATA: " + mData.getValue ( ) );

					mList.add ( mData.getValue ( AdoptModel.class ) );
				}
				mAdapter.notifyDataSetChanged ( );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

}