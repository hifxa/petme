package com.petme.app.view.dash;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petme.app.base.BaseFragment;
import com.petme.app.databinding.FragmentChatListBinding;
import com.petme.app.view.ChatActivity;

public class ChatListFragment extends BaseFragment < FragmentChatListBinding > {

	@Override
	public FragmentChatListBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentChatListBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.chat.setOnClickListener ( v -> startActivity ( new Intent ( mCtx , ChatActivity.class ) ) );
	}
}