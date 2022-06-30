package com.petme.app.controllers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.HomeItemCardBinding;
import com.petme.app.interfaces.RecyclerClicks;

import java.util.List;

public class HomeOptionsAdapter extends RecyclerView.Adapter < HomeOptionsAdapter.HomeOptionsAdapterHolder > {

	List < String > mList;
	Context mCtx;
	RecyclerClicks mClicks;

	public HomeOptionsAdapter ( Context mCtx , List < String > mList , RecyclerClicks mClicks ) {
		this.mList = mList;
		this.mCtx = mCtx;
		this.mClicks = mClicks;
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

		holder.bind.title.setText ( option.toUpperCase ( ) );
		holder.bind.click.setOnClickListener ( view -> mClicks.onItemClick ( position , option ) );

		switch ( option ) {
			case "Shops / Vet":
				holder.bind.icon.setImageResource ( R.drawable.shop_onboarding );
				holder.bind.title.setTextColor ( ContextCompat.getColor ( mCtx , R.color.onShopContainer ) );
				holder.bind.icon.setImageTintList ( ColorStateList.valueOf ( ContextCompat.getColor ( mCtx , R.color.onShopContainer ) ) );
				holder.bind.card.setCardBackgroundColor ( ContextCompat.getColor ( mCtx , R.color.shopContainer ) );
				break;
			case "tasks":
				holder.bind.icon.setImageResource ( R.drawable.task_onboading );
				holder.bind.title.setTextColor ( ContextCompat.getColor ( mCtx , R.color.onTask ) );
				holder.bind.icon.setImageTintList ( ColorStateList.valueOf ( ContextCompat.getColor ( mCtx , R.color.onTask ) ) );
				holder.bind.card.setCardBackgroundColor ( ContextCompat.getColor ( mCtx , R.color.taskContainer ) );
				break;
			case "Lost / Found":
				holder.bind.icon.setImageResource (R.drawable.lost_and_found_onboarding);
				holder.bind.title.setTextColor ( ContextCompat.getColor ( mCtx , R.color.onLostFound ) );
				holder.bind.icon.setImageTintList ( ColorStateList.valueOf ( ContextCompat.getColor ( mCtx , R.color.onLostFound ) ) );
				holder.bind.card.setCardBackgroundColor ( ContextCompat.getColor ( mCtx , R.color.lostFoundContainer ) );
				break;
			case "adopt":
				holder.bind.icon.setImageResource ( R.drawable.adoptonboading );
				holder.bind.title.setTextColor ( ContextCompat.getColor ( mCtx , R.color.onAdopt ) );
				holder.bind.icon.setImageTintList ( ColorStateList.valueOf ( ContextCompat.getColor ( mCtx , R.color.onAdopt ) ) );
				holder.bind.card.setCardBackgroundColor ( ContextCompat.getColor ( mCtx , R.color.adoptContainer ) );
				break;
		}
	}

	@Override
	public int getItemCount ( ) {
		return mList.size ( );
	}

	static class HomeOptionsAdapterHolder extends RecyclerView.ViewHolder {
		HomeItemCardBinding bind;

		public HomeOptionsAdapterHolder ( @NonNull HomeItemCardBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}
}