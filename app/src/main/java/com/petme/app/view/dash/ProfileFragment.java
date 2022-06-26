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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.PetsAdapter;
import com.petme.app.databinding.FragmentProfileBinding;
import com.petme.app.model.PetModel;
import com.petme.app.model.UserModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings ( "ConstantConditions" )
@SuppressLint ( "SetTextI18n" )
public class ProfileFragment extends BaseFragment < FragmentProfileBinding > {

	List < PetModel > mList = new ArrayList <> ( );
	PetsAdapter mAdapter;

	@Override
	public FragmentProfileBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentProfileBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		mAdapter = new PetsAdapter ( mCtx , mList , ( pos , type ) -> {

		} );
		bind.petsRecycler.setAdapter ( mAdapter );

		setEnabled ( false );

		bind.edit.setOnClickListener ( v -> Navigation.findNavController ( v ).navigate ( R.id.editProfileFragment ) );

		bind.addPet.setOnClickListener ( v -> {
			Bundle mBundle = new Bundle ( );
			mBundle.putString ( "from" , "pet" );
			Navigation.findNavController ( v ).navigate ( R.id.createAdoptionFragment , mBundle );
		} );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
		fetchUser ( );
		getPets ( );
	}

	private void fetchUser ( ) {
		FireRef.userDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				UserModel user = snap.getValue ( UserModel.class );

				bind.name.setText ( user.getName ( ) );
				bind.email.setText ( user.getEmail ( ) );
				bind.phone.setText ( user.getPhone ( ) );

			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	private void getPets ( ) {

		FireRef.userDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).child ( "pets" ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				mList.clear ( );
				for ( DataSnapshot mData : snap.getChildren ( ) ) {
					mList.add ( mData.getValue ( PetModel.class ) );
				}

				if ( mList.isEmpty ( ) ) {
					bind.noData.getRoot ( ).setVisibility ( View.VISIBLE );
					bind.petsRecycler.setVisibility ( View.GONE );
				}
				else {
					bind.noData.getRoot ( ).setVisibility ( View.GONE );
					bind.petsRecycler.setVisibility ( View.VISIBLE );
				}

				mAdapter.notifyDataSetChanged ( );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	private void createPets ( String name , String breed , String type ) {

		HashMap < String, String > taskMap = new HashMap <> ( );
		taskMap.put ( "name" , name );
		taskMap.put ( "type" , type );
		taskMap.put ( "breed" , breed );
		taskMap.put ( "image" , "" );

		String pushKey = FireRef.userDbRef.push ( ).getKey ( );

		FireRef.userDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).child ( "pets" ).child ( pushKey ).setValue ( taskMap , ( error , ref ) -> {
			if ( error == null ) {
				Alerts.success ( mCtx , "Task Added Successfully!" );
			}
			else {
				error.toException ( ).printStackTrace ( );
			}
		} );
	}

	private void setEnabled ( boolean state ) {
		bind.name.setEnabled ( state );
		bind.email.setEnabled ( state );
		bind.phone.setEnabled ( state );
	}

}