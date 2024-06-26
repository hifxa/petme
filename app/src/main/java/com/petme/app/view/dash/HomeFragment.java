package com.petme.app.view.dash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.HomeOptionsAdapter;
import com.petme.app.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressLint ( "SetTextI18n" )
public class HomeFragment extends BaseFragment < FragmentHomeBinding > {

	List < String > homeOptions = new ArrayList <> ( Arrays.asList ( "Shops / Vet" , "tasks" , "Lost / Found" , "adopt" ) );
	List < String > quotesList = new ArrayList <> ( );
	HomeOptionsAdapter mAdapter;

	@Override
	public FragmentHomeBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentHomeBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		mAdapter = new HomeOptionsAdapter ( mCtx , homeOptions , ( pos , type ) -> {
			switch ( type ) {
				case "Shops / Vet":
					requestPerms ( granted -> {
						if ( granted ) {
							Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToVetFragment );
						}
					} );
					break;
				case "tasks":
					Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToTaskFragment );
					break;
				case "Lost / Found":
					Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToLostFoundFragment );
					break;
				case "adopt":
					Navigation.findNavController ( bind.getRoot ( ) ).navigate ( R.id.goToAdoptFragment );
					break;
			}
		} );

		bind.optionsRecycler.setAdapter ( mAdapter );

		FireRef.quotesDbRef.get ( ).addOnCompleteListener ( task -> {
					if ( task.isSuccessful ( ) ) {
						DataSnapshot snap = task.getResult ( );

						for ( DataSnapshot mData : snap.getChildren ( ) ) {
							quotesList.add ( mData.getValue ( ).toString ( ) );
						}

						bind.fadeText.setTexts ( quotesList.toArray ( new String[ 0 ] ) );
					}
				} )
				.addOnFailureListener ( e -> e.printStackTrace ( ) );

		bind.fadeText.setTimeout ( 5 , TimeUnit.SECONDS );
	}

}