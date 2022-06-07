package com.petme.app.interfaces;

import android.view.View;

import com.petme.app.model.ChatModel;

public interface ChatSelection {
	void onCLick ( int position , ChatModel model , View view );

	void onLongCLick ( int position , ChatModel model , View view );
}
