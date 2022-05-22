package com.petme.app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.TaskItemViewBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.PetModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PetsAdapter extends RecyclerView.Adapter < PetsAdapter.PetsAdapterHolder > {

	private final List < PetModel > mList;
	private final Context mCtx;
	private final RecyclerClicks mClicks;

	public PetsAdapter ( Context mCtx , List < PetModel > mList , RecyclerClicks mClicks ) {
		this.mList = mList;
		this.mCtx = mCtx;
		this.mClicks = mClicks;
	}

	@NonNull
	@Override
	public PetsAdapterHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new PetsAdapterHolder ( TaskItemViewBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.task_item_view , parent , false ) ) );
	}

	@Override
	public void onBindViewHolder ( @NonNull PetsAdapterHolder holder , int position ) {

		try {
			PetModel task = mList.get ( position );

			holder.bind.taskTitle.setText ( "Pet Name: " + task.getName ( ) );
			holder.bind.tasskDesc.setText ( "Breed: " + task.getBreed ( ) );

			holder.bind.taskTime.setVisibility ( View.GONE );
			holder.bind.petName.setVisibility ( View.GONE );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
	}

	public String formatTime ( long timestamp ) {
		try {
			return new SimpleDateFormat ( "EEEE MMM,yyy hh:mm a" , Locale.getDefault ( ) ).format ( new Date ( timestamp ) );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
		return "";
	}

	@Override
	public int getItemCount ( ) {
		return mList.size ( );
	}

	static class PetsAdapterHolder extends RecyclerView.ViewHolder {
		TaskItemViewBinding bind;

		public PetsAdapterHolder ( @NonNull TaskItemViewBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}


}
