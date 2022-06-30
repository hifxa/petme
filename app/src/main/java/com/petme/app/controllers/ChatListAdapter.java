package com.petme.app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.ChatListItemBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.ChatModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatListAdapter extends RecyclerView.Adapter < ChatListAdapter.TaskAdapterHolder > {

	private final List < ChatModel > mList;
	private final RecyclerClicks mClicks;
	private final Context mCtx;

	public ChatListAdapter ( Context mCtx , List < ChatModel > mList , RecyclerClicks mClicks) {
		this.mList = mList;
		this.mCtx = mCtx;
		this.mClicks = mClicks;
	}

	@NonNull
	@Override
	public TaskAdapterHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new TaskAdapterHolder ( ChatListItemBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.chat_list_item , parent , false ) ) );
	}

	@Override
	public void onBindViewHolder ( @NonNull TaskAdapterHolder holder , int position ) {

		try {
			ChatModel model = mList.get ( position );

			holder.bind.senderName.setText ( model.getSender_id ( ) );
			holder.bind.date.setText ( formatTime ( Long.parseLong ( model.getTimestamp ( ) ) ) );


			if ( model.getType ( ).equals ( "image" ) ) {
				holder.bind.message.setText ( "Sent an Image" );
			}
			else {
				holder.bind.message.setText ( model.getMessage ( ) );
			}

			//holder.bind.taskCheckBox.setOnCheckedChangeListener(chat.isCheck());
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
	}

	public String formatTime ( long timestamp ) {
		try {
			return new SimpleDateFormat ( "EEEE dd MMM,yyyy hh:mm a" , Locale.getDefault ( ) ).format ( new Date ( timestamp ) );
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

	static class TaskAdapterHolder extends RecyclerView.ViewHolder {
		ChatListItemBinding bind;

		public TaskAdapterHolder ( @NonNull ChatListItemBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}


}
