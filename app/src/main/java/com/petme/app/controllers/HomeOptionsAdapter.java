package com.petme.app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petme.R;
import com.example.petme.databinding.HomeItemCardBinding;

import java.util.List;

public class HomeOptionsAdapter extends RecyclerView.Adapter < HomeOptionsAdapter.HomeOptionsAdapterHolder > {

	List < String > mList;
	Context mCtx;

	public HomeOptionsAdapter ( Context mCtx , List < String > mList ) {
		this.mList = mList;
		this.mCtx = mCtx;
	}

	@NonNull
	@Override
	public HomeOptionsAdapterHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new HomeOptionsAdapterHolder ( HomeItemCardBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.home_item_card , parent , false ) ) );
	}

	//( "vet" , "shop" , "tasks" , "lost/Found" , "adopt" , "mating" )

	@Override
	public void onBindViewHolder ( @NonNull HomeOptionsAdapterHolder holder , int position ) {
		String option = mList.get ( position );

		holder.bind.title.setText ( option.toUpperCase () );

		switch ( option ) {
			case "vet":
				holder.bind.icon.setImageResource ( R.drawable.ic_heart_tick );
				break;
			case "shop":
				holder.bind.icon.setImageResource ( R.drawable.ic_shop );
				break;
			case "tasks":
				holder.bind.icon.setImageResource ( R.drawable.ic_task_square );
				break;
			case "lost":
				holder.bind.icon.setImageResource ( R.drawable.ic_note_2 );
				break;
			case "adopt":
				holder.bind.icon.setImageResource ( R.drawable.ic_house );
				break;
			case "mating":
				holder.bind.icon.setImageResource ( R.drawable.ic_lovely );
				break;
		}
	}

	@Override
	public int getItemCount ( ) {
		return 6;
	}

	static class HomeOptionsAdapterHolder extends RecyclerView.ViewHolder {
		HomeItemCardBinding bind;

		public HomeOptionsAdapterHolder ( @NonNull HomeItemCardBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}

}
