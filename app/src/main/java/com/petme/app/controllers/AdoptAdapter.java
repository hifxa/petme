package com.petme.app.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.AdoptItemViewBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.AdoptModel;
import com.petme.app.utils.Alerts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint ( "SetTextI18n" )
public class AdoptAdapter extends RecyclerView.Adapter < AdoptAdapter.AdoptAdapterHolder > {

	private final List < AdoptModel > mList;
	private final RecyclerClicks mClicks;
	private final Context mCtx;

	public AdoptAdapter ( Context mCtx , List < AdoptModel > mList , RecyclerClicks mClicks ) {
		this.mList = mList;
		this.mCtx = mCtx;
		this.mClicks = mClicks;
	}

	@NonNull
	@Override
	public AdoptAdapterHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new AdoptAdapter.AdoptAdapterHolder ( AdoptItemViewBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.adopt_item_view , parent , false ) ) );
	}

    @Override
	public void onBindViewHolder ( @NonNull AdoptAdapterHolder holder , int position ) {
		try {
			AdoptModel adopt = mList.get ( position );

			Alerts.log ( "TAGS" , "INSIDE RECYCLER " + adopt.getTimestamp ( ) );

			holder.bind.petAge.setText ( adopt.getAge ( ) );
			holder.bind.adoptBreed.setText ( adopt.getBreed ( ) );
			holder.bind.adoptDetails.setText ( adopt.getDetails ( ) );
			holder.bind.adoptContact.setText ( adopt.getContact ( ) );
			holder.bind.petName.setText ( "Pet: " + adopt.getName ( ) );
			holder.bind.adoptDate.setText ( formatTime ( Long.parseLong ( adopt.getTimestamp ( ) ) ) );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
	}

	private String formatTime ( long parseLong ) {
		try {
			return new SimpleDateFormat ( "dd MM,yyyy\nhh:mm a" , Locale.getDefault ( ) ).format ( new Date ( parseLong ) );
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

	static class AdoptAdapterHolder extends RecyclerView.ViewHolder {
		AdoptItemViewBinding bind;

		public AdoptAdapterHolder ( @NonNull AdoptItemViewBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}

}
