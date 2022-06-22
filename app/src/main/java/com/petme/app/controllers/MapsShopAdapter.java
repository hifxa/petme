package com.petme.app.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.MapItemBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.PlacesResponse;

import java.util.List;

@SuppressLint ( "SetTextI18n" )
public class MapsShopAdapter extends RecyclerView.Adapter < MapsShopAdapter.MapsAdapterHolder > {

	private final List < PlacesResponse.Results > mList;
	private final RecyclerClicks mClicks;
	private final Context mCtx;

	public MapsShopAdapter ( Context mCtx , List < PlacesResponse.Results > mList , RecyclerClicks mClicks ) {
		this.mList = mList;
		this.mCtx = mCtx;
		this.mClicks = mClicks;
	}

	@NonNull
	@Override
	public MapsAdapterHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new MapsShopAdapter.MapsAdapterHolder ( MapItemBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.map_item , parent , false ) ) );
	}

	@Override
	public void onBindViewHolder ( @NonNull MapsAdapterHolder holder , int position ) {
		try {
			PlacesResponse.Results adopt = mList.get ( position );

			holder.bind.petBreed.setText ( adopt.name );
			holder.bind.petName.setText ( "Status: " + adopt.businessStatus );
			holder.bind.petAge.setText ( "Rating: " + adopt.rating );
			holder.bind.petDetails.setText ( adopt.vicinity );

			String type = "";
			for ( String s : adopt.types ) {
				type = s + "|";
			}
			holder.bind.petContact.setText ( type );

//			Picasso.get ( ).load ( adopt.photos.get ( 0 ).photoReference ).placeholder ( R.drawable.pet ).error ( R.drawable.pet ).into ( holder.bind.petImg );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
	}

	@Override
	public int getItemCount ( ) {
		return mList.size ( );
	}

	static class MapsAdapterHolder extends RecyclerView.ViewHolder {
		MapItemBinding bind;

		public MapsAdapterHolder ( @NonNull MapItemBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}

}
