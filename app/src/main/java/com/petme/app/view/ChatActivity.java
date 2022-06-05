package com.petme.app.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.petme.app.databinding.ActivityChatBinding;
import com.petme.app.utils.Prefs;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

	ActivityChatBinding bind;

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		bind = ActivityChatBinding.inflate ( getLayoutInflater ( ) );
		setContentView ( bind.getRoot ( ) );

	}

	private void sendMessage ( String message , String type ) {

		Map < String, String > chatMap = new HashMap <> ( );

		chatMap.put ( "type" , type );
		chatMap.put ( "message" , message );
		chatMap.put ( "sender_id" , new Prefs ( this ).getUserId ( ) );
		chatMap.put ( "timestamp" , String.valueOf ( System.currentTimeMillis ( ) ) );


	}

}