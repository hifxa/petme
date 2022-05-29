package com.petme.app.view.dash;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.PetsAdapter;
import com.petme.app.databinding.AddTaskSheetBinding;
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

	DatabaseReference mRef = FirebaseDatabase.getInstance ( ).getReference ( "users" );
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

		bind.edit.setOnClickListener ( v -> {

		} );

		bind.addPet.setOnClickListener ( v -> addNewPet ( ) );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
		fetchUser ( );
		getPets ( );
	}

	private void fetchUser ( ) {
		mRef.child ( new Prefs ( mCtx ).getUserId ( ) ).addValueEventListener ( new ValueEventListener ( ) {
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

	private void updateProfile ( ) {

		HashMap < String, Object > userMap = new HashMap <> ( );
		userMap.put ( "email" , bind.email.getText ( ).toString ( ).trim ( ) );
		userMap.put ( "phone" , bind.phone.getText ( ).toString ( ).trim ( ) );
		userMap.put ( "id" , new Prefs ( mCtx ).getUserId ( ) );
		userMap.put ( "name" , bind.name.getText ( ).toString ( ).trim ( ) );
		userMap.put ( "image" , "" );

		mRef.child ( new Prefs ( mCtx ).getUserId ( ) ).updateChildren ( userMap ).addOnSuccessListener ( task -> {
			setEnabled ( false );
		} ).addOnFailureListener ( e -> {
					setEnabled ( false );
					e.printStackTrace ( );
				}
		);
	}

	private void getPets ( ) {

		mRef.child ( new Prefs ( mCtx ).getUserId ( ) ).child ( "pets" ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				mList.clear ( );
				for ( DataSnapshot mData : snap.getChildren ( ) ) {
					mList.add ( mData.getValue ( PetModel.class ) );
				}
				mAdapter.notifyDataSetChanged ( );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	private void addNewPet ( ) {

		AddTaskSheetBinding sBind = AddTaskSheetBinding.bind ( getLayoutInflater ( ).inflate ( R.layout.add_task_sheet , bind.getRoot ( ) , false ) );
		BottomSheetDialog mSheet = new BottomSheetDialog ( mCtx );
		mSheet.setContentView ( sBind.getRoot ( ) );
		mSheet.setDismissWithAnimation ( true );
		mSheet.setCancelable ( false );
		mSheet.setCanceledOnTouchOutside ( false );

		sBind.addTask.setText ( "Add Pet" );
		sBind.heading.setText ( "Add a New Pet" );
		sBind.descView.setVisibility ( View.GONE );
		sBind.taskTitle.setHint ( "Breed" );

		sBind.addTask.setOnClickListener ( view1 -> {
			mSheet.dismiss ( );
			createPets ( sBind.petName.getText ( ).toString ( ).trim ( ) , sBind.taskTitle.getText ( ).toString ( ).trim ( ) , sBind.desc.getText ( ).toString ( ).trim ( ) );
		} );

		sBind.close.setOnClickListener ( v -> mSheet.dismiss ( ) );
		mSheet.show ( );
	}

	private void createPets ( String name , String breed , String type ) {

		HashMap < String, String > taskMap = new HashMap <> ( );
		taskMap.put ( "name" , name );
		taskMap.put ( "type" , type );
		taskMap.put ( "breed" , breed );
		taskMap.put ( "image" , "" );

		String pushKey = mRef.push ( ).getKey ( );

		mRef.child ( new Prefs ( mCtx ).getUserId ( ) ).child ( "pets" ).child ( pushKey ).setValue ( taskMap , ( error , ref ) -> {
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