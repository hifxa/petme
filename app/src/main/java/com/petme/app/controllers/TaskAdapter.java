package com.petme.app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petme.app.R;
import com.petme.app.databinding.TaskItemViewBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.TaskModel;
import com.petme.app.utils.Alerts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter < TaskAdapter.TaskAdapterHolder > {

	private final List < TaskModel > mList;
	private final Context mCtx;
	private final RecyclerClicks mClicks;

	public TaskAdapter ( Context mCtx , List < TaskModel > mList , RecyclerClicks mClicks ) {
		this.mList = mList;
		this.mCtx = mCtx;
		this.mClicks = mClicks;
	}

	@NonNull
	@Override
	public TaskAdapterHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
		return new TaskAdapterHolder ( TaskItemViewBinding.bind ( LayoutInflater.from ( mCtx ).inflate ( R.layout.task_item_view , parent , false ) ) );
	}

	@Override
	public void onBindViewHolder ( @NonNull TaskAdapterHolder holder , int position ) {

		try {
			TaskModel task = mList.get ( position );

			Alerts.log ( "TAGS" , "INSIDE RECYCLER " + task.getTimestamp ( ) );

			holder.bind.taskTitle.setText ( task.getTask ( ) );
			holder.bind.tasskDesc.setText ( task.getDesc ( ) );
			holder.bind.petName.setText ( "Pet: " + task.getName ( ) );
			holder.bind.taskTime.setText ( formatTime ( Long.parseLong ( task.getTimestamp ( ) ) ) );
			holder.bind.taskCheckBox.setOnCheckedChangeListener(task.isCheck());
		}
		catch ( Exception e ) {
			e.printStackTrace ( );
		}
	}


	public void onCheckChanged (CompoundButton buttonView, boolean isChecked) {
		TaskModel task = mList.get(getItemCount());
		mList.add(mList.remove(getItemCount()));
		notifyDataSetChanged();

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
		TaskItemViewBinding bind;

		public TaskAdapterHolder ( @NonNull TaskItemViewBinding bind ) {
			super ( bind.getRoot ( ) );
			this.bind = bind;
		}
	}


}
