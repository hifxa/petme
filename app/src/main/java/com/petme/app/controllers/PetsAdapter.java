package com.petme.app.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.PetItemBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.PetModel;
import com.squareup.picasso.Picasso;

import java.util.List;

@SuppressLint ( "SetTextI18n" )
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
		return new PetsAdapterHolder ( PetItemBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.pet_item , parent , false ) ) );
	}

	@Override
	public void onBindViewHolder ( @NonNull PetsAdapterHolder holder , int position ) {

		try {
			PetModel task = mList.get ( position );

			holder.bind.petName.setText ( "Pet Name: " + task.getName ( ) );
			holder.bind.petBreed.setText ( "Breed: " + task.getBreed ( ) );
			holder.bind.petAge.setText ( "Age: " + task.getAge ( ) );

			holder.bind.getRoot ( ).setCardBackgroundColor ( Color.parseColor ( task.getColor ( ) ) );

			Picasso.get ( ).load ( task.getImage ( ) ).placeholder ( R.drawable.pet ).error ( R.drawable.pet ).into ( holder.bind.petImg );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
	}

	@Override
	public int getItemCount ( ) {
		return mList.size ( );
	}

	static class PetsAdapterHolder extends RecyclerView.ViewHolder {
		PetItemBinding bind;

		public PetsAdapterHolder ( @NonNull PetItemBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}


}
