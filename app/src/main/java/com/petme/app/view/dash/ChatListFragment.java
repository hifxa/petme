package com.petme.app.view.dash;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.ChatListAdapter;
import com.petme.app.databinding.FragmentChatListBinding;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.ChatModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;
import com.petme.app.view.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends BaseFragment < FragmentChatListBinding > {

	List < ChatModel > mList = new ArrayList <> ( );
	ChatListAdapter mAdapter;

	@Override
	public FragmentChatListBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentChatListBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		mAdapter = new ChatListAdapter ( mCtx , mList , ( pos , type ) -> {
			startActivity ( new Intent( mCtx , ChatActivity.class ).putExtra("receiverId", type) );

		} );

		bind.chatsRecycler.setAdapter ( mAdapter );

		bind.chat.setOnClickListener ( v -> startActivity ( new Intent ( mCtx , ChatActivity.class ) ) );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );

		getChats ( );
	}

	private void getChats ( ) {

		FireRef.chatListRef.child ( new Prefs ( mCtx ).getUserId ( ) ).addValueEventListener ( new ValueEventListener ( ) {
			@Override
			public void onDataChange ( @NonNull DataSnapshot snap ) {
				try {
					mList.clear ( );
					for ( DataSnapshot mData : snap.getChildren ( ) ) {

						Alerts.log ( TAG,"DATA: "+mData.getValue () );

						mList.add ( mData.getValue ( ChatModel.class ) );
					}

					if ( mList.isEmpty ( ) ) {
						bind.noData.getRoot ( ).setVisibility ( View.VISIBLE );
						bind.chatsRecycler.setVisibility ( View.GONE );
					}
					else {
						bind.noData.getRoot ( ).setVisibility ( View.GONE );
						bind.chatsRecycler.setVisibility ( View.VISIBLE );
					}

					mAdapter.notifyDataSetChanged ( );
				}
				catch ( Exception e ) {
					e.printStackTrace ( );
					bind.noData.getRoot ( ).setVisibility ( View.VISIBLE );
					bind.chatsRecycler.setVisibility ( View.GONE );
				}
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

}