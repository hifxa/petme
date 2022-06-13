package com.petme.app.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.ChatItemBinding;
import com.petme.app.interfaces.ChatSelection;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.ChatModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@SuppressLint ( "NotifyDataSetChanged" )
public class ChatAdapter extends RecyclerView.Adapter < ChatAdapter.ChatItemHolder > {

	//selection logic
	private final HashSet < ChatModel > mSelectedData = new HashSet <> ( );
	Context mCtx;
	String senderId;
	List < ChatModel > chatList;
	RecyclerClicks clicks;
	ChatSelection select;
	private int currentSelectedId = - 1;

	public ChatAdapter (
			Context mCtx ,
			String senderId ,
			List < ChatModel > chatList ,
			RecyclerClicks clicks ,
			ChatSelection select
	) {
		this.mCtx = mCtx;
		this.senderId = senderId;
		this.chatList = chatList;
		this.clicks = clicks;
		this.select = select;
	}

	@NonNull
	@Override
	public ChatItemHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new ChatItemHolder (
				ChatItemBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.chat_item , parent , false ) )
		);
	}

	@Override
	public void onBindViewHolder ( @NonNull ChatItemHolder holder , int position ) {

		ChatModel model = chatList.get ( position );

		if ( model.getSender_id ( ).equals ( senderId ) ) {
			holder.bind.receiver.getRoot ( ).setVisibility ( View.GONE );
			holder.bind.sender.getRoot ( ).setVisibility ( View.VISIBLE );

			holder.bind.sender.message.setText ( model.getMessage ( ) );
			holder.bind.sender.time.setText ( formatTime ( Long.parseLong ( model.getTimestamp ( ) ) ) );

			if ( model.getType ( ).equals ( "image" ) ) {
				holder.bind.sender.card.setVisibility ( View.VISIBLE );
				holder.bind.sender.message.setVisibility ( View.GONE );
				Picasso.get ( ).load ( model.getMessage ( ) ).placeholder ( R.drawable.pet ).error ( R.drawable.pet ).into ( holder.bind.sender.image );
			}
			else {
				holder.bind.sender.card.setVisibility ( View.GONE );
				holder.bind.sender.message.setVisibility ( View.VISIBLE );
			}

			holder.bind.sender.getRoot ( ).setOnLongClickListener ( v -> {
				select.onLongCLick ( position , chatList.get ( position ) , v );
				return true;
			} );

			holder.bind.sender.getRoot ( ).setOnClickListener ( v -> select.onCLick ( position , chatList.get ( position ) , v ) );
		}
		else {
			holder.bind.receiver.getRoot ( ).setVisibility ( View.VISIBLE );
			holder.bind.sender.getRoot ( ).setVisibility ( View.GONE );

			if ( model.getType ( ).equals ( "image" ) ) {
				holder.bind.receiver.card.setVisibility ( View.VISIBLE );
				holder.bind.receiver.message.setVisibility ( View.GONE );
				Picasso.get ( ).load ( model.getMessage ( ) ).placeholder ( R.drawable.pet ).error ( R.drawable.pet ).into ( holder.bind.receiver.image );
			}
			else {
				holder.bind.receiver.card.setVisibility ( View.GONE );
				holder.bind.receiver.message.setVisibility ( View.VISIBLE );
			}

			holder.bind.receiver.message.setText ( model.getMessage ( ) );
			holder.bind.receiver.time.setText ( formatTime ( Long.parseLong ( model.getTimestamp ( ) ) ) );
		}
		setSelectionView ( position , holder );
	}

	@Override
	public int getItemCount ( ) {
		return chatList.size ( );
	}

	private void setSelectionView ( int pos , ChatItemHolder holder ) {
		if ( mSelectedData.contains ( chatList.get ( pos ) ) ) {

			for ( ChatModel mSelectedDatum : mSelectedData ) {
				if ( mSelectedDatum == chatList.get ( pos ) ) {
					if ( mSelectedDatum.getSender_id ( ).equals ( senderId ) ) {
						holder.bind.sender.getRoot ( ).setBackgroundColor ( ContextCompat.getColor ( mCtx , R.color.inversePrimary ) );
					}
					else {
						holder.bind.receiver.getRoot ( ).setBackgroundColor ( ContextCompat.getColor ( mCtx , R.color.inversePrimary ) );
					}
				}
			}
		}
		else {
			holder.bind.sender.getRoot ( ).setBackgroundColor ( ContextCompat.getColor ( mCtx , android.R.color.transparent ) );
			holder.bind.receiver.getRoot ( ).setBackgroundColor ( ContextCompat.getColor ( mCtx , android.R.color.transparent ) );
		}
	}

	private String formatTime ( long parseLong ) {
		try {
			return new SimpleDateFormat ( "hh:mm a" , Locale.getDefault ( ) ).format ( new Date ( parseLong ) );
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
		return "";
	}

	public void toggleSelection ( int pos ) {
		currentSelectedId = pos;

		if ( mSelectedData.contains ( chatList.get ( pos ) ) ) {
			mSelectedData.remove ( chatList.get ( pos ) );
		}
		else {
			mSelectedData.add ( chatList.get ( pos ) );
		}

		notifyDataSetChanged ( );
	}

	public void clearSelections ( ) {
		mSelectedData.clear ( );
		notifyDataSetChanged ( );
	}

	public int getSelectedItemCount ( ) {
		return mSelectedData.size ( );
	}

	public List < ChatModel > getSelectedItems ( ) {
		return new ArrayList <> ( mSelectedData );
	}

	public void removeData ( int position ) {
		chatList.remove ( position );
		resetCurrentIndex ( );
	}

	private void resetCurrentIndex ( ) {
		currentSelectedId = - 1;
	}

	public ChatModel getItem ( int position ) {
		return chatList.get ( position );
	}

	static class ChatItemHolder extends RecyclerView.ViewHolder {
		ChatItemBinding bind;

		public ChatItemHolder ( @NonNull ChatItemBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}
}
