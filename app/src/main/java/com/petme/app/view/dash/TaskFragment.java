package com.petme.app.view.dash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.TaskAdapter;
import com.petme.app.databinding.AddTaskSheetBinding;
import com.petme.app.databinding.FragmentTaskBinding;
import com.petme.app.model.TaskModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings ( "ConstantConditions" )
public class TaskFragment extends BaseFragment < FragmentTaskBinding > {

	List < TaskModel > mList = new ArrayList <> ( );
	TaskAdapter mAdapter;

	@Override
	public FragmentTaskBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentTaskBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "My Tasks" );

		bind.newTaskButton.setOnClickListener ( view1 -> addNewTask ( ) );
		mAdapter = new TaskAdapter ( mCtx , mList , ( pos , type ) -> {

		} );
		bind.taskRecycler.setAdapter ( mAdapter );

	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
		getTasks ( );
	}

	/**
	 * This will fetch the tasks from the firebase db for the specific user
	 */
	private void getTasks ( ) {

		FireRef.taskDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				mList.clear ( );
				for ( DataSnapshot mData : snap.getChildren ( ) ) {
					mList.add ( mData.getValue ( TaskModel.class ) );
				}

				if ( mList.isEmpty ( ) ) {
					bind.noData.getRoot ( ).setVisibility ( View.VISIBLE );
					bind.taskRecycler.setVisibility ( View.GONE );
				}
				else {
					bind.noData.getRoot ( ).setVisibility ( View.GONE );
					bind.taskRecycler.setVisibility ( View.VISIBLE );
				}

				mAdapter.notifyDataSetChanged ( );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	private void addNewTask ( ) {

		AddTaskSheetBinding sBind = AddTaskSheetBinding.bind ( getLayoutInflater ( ).inflate ( R.layout.add_task_sheet , bind.getRoot ( ) , false ) );
		BottomSheetDialog mSheet = new BottomSheetDialog ( mCtx );
		mSheet.setContentView ( sBind.getRoot ( ) );
		mSheet.setDismissWithAnimation ( true );
		mSheet.setCancelable ( false );
		mSheet.setCanceledOnTouchOutside ( false );

		sBind.addTask.setOnClickListener ( view1 -> {
			mSheet.dismiss ( );
			if(sBind.petName.getText().toString().isEmpty()){
				Alerts.error(mCtx, "Your pet's name please!");
			}
			else if(sBind.taskTitle.getText().toString().isEmpty() || sBind.desc.getText().toString().isEmpty()){
				Alerts.error(mCtx, "You haven't added a task!");
			}
			else{
				createTask ( sBind.petName.getText ( ).toString ( ).trim ( ) , sBind.taskTitle.getText ( ).toString ( ).trim ( ) , sBind.desc.getText ( ).toString ( ).trim ( ) , "pending" );
			}
		} );

		sBind.close.setOnClickListener ( v -> mSheet.dismiss ( ) );
		mSheet.show ( );
	}

	private void createTask ( String name , String title , String desc , String status ) {
		HashMap < String, String > taskMap = new HashMap <> ( );
		taskMap.put ( "name" , name );
		taskMap.put ( "task" , title );
		taskMap.put ( "desc" , desc );
		taskMap.put ( "timestamp" , "" + System.currentTimeMillis ( ) );
		taskMap.put ( "status" , status );

		String pushKey = FireRef.taskDbRef.push ( ).getKey ( );

		FireRef.taskDbRef.child ( new Prefs ( mCtx ).getUserId ( ) ).child ( pushKey ).setValue ( taskMap , ( error , ref ) -> {
			if ( error == null ) {
					Alerts.success ( mCtx , "Task Added Successfully!" );
			}
			else {
				error.toException ( ).printStackTrace ( );
			}
		} );
	}

	public void onCheckBoxClicked(View view) {

	}
}