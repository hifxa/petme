package com.petme.app.controllers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.xcode.onboarding.OnBoardingPage;

import java.util.List;

public class OnboardAdapter extends RecyclerView.Adapter < OnboardAdapter.OnBoardingViewHolder > {

	List < OnBoardingPage > pages;
	Context mCtx;

	public OnboardAdapter ( @NonNull List < OnBoardingPage > pages ) {
		this.pages = pages;
	}

	@Override
	@NonNull
	public OnBoardingViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		mCtx = parent.getContext ();
		return new OnBoardingViewHolder ( LayoutInflater.from ( mCtx ).inflate ( R.layout.onboarding_page , parent , false ) );
	}

	@Override
	public void onBindViewHolder ( @NonNull OnBoardingViewHolder holder , int position ) {
		holder.imageView.setImageResource ( pages.get ( position ).getImage ( ) );
		holder.heading.setText ( pages.get ( position ).getTitle ( ) );
		holder.description.setText ( pages.get ( position ).getDescription ( ) );

		holder.heading.setTextColor ( ContextCompat.getColor ( mCtx, pages.get ( position ).getTxtColor ( ) ) );
		holder.description.setTextColor ( ContextCompat.getColor ( mCtx, pages.get ( position ).getTxtColor ( ) ) );
		holder.root.setBackgroundColor ( ContextCompat.getColor ( mCtx, pages.get ( position ).getBgColor ( ) ) );
	}

	@Override
	public int getItemCount ( ) {
		return pages.size ( );
	}

	protected static class OnBoardingViewHolder extends RecyclerView.ViewHolder {
		ImageView imageView;
		TextView heading;
		TextView description;
		LinearLayout root;

		public OnBoardingViewHolder ( View view ) {
			super ( view );
			imageView = view.findViewById ( R.id.image );
			heading = view.findViewById ( R.id.heading );
			description = view.findViewById ( R.id.description );
			root = view.findViewById ( R.id.root_view );
		}
	}
}
