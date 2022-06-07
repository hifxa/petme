package com.petme.app.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.petme.app.R;
import com.petme.app.base.BaseActivity;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.ChatAdapter;
import com.petme.app.databinding.ActivityChatBinding;
import com.petme.app.interfaces.ChatSelection;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.ChatModel;
import com.petme.app.utils.Alerts;
import com.petme.app.utils.Prefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint ( "NotifyDataSetChanged" )
public class ChatActivity extends BaseActivity {

	private final List < ChatModel > chatList = new ArrayList <> ( );
	private final RecyclerClicks chatCLicks = ( pos , type ) -> {

	};
	private final String receiverId = "123456";
	private String receiverChatKey = "";
	private String senderChatKey = "";
	private ActivityChatBinding bind;
	private ActionModeCall mCallback;
	private ActionMode actionMode;
	private String senderId = "";
	private ChatAdapter adapter;
	private final ChatSelection chatSelect = new ChatSelection ( ) {
		@Override
		public void onCLick ( int position , ChatModel model , View view ) {

		}

		@Override
		public void onLongCLick ( int position , ChatModel model , View view ) {
			enableActionMode ( position );
		}
	};

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		bind = ActivityChatBinding.inflate ( getLayoutInflater ( ) );
		setContentView ( bind.getRoot ( ) );

		senderId = new Prefs ( this ).getUserId ( );
//		receiverId = getIntent ( ).getStringExtra ( "receiverId" );

		mCallback = new ActionModeCall ( );

		senderChatKey = senderId + "_chat_" + receiverId;
		receiverChatKey = receiverId + "_chat_" + senderId;

		adapter = new ChatAdapter ( this , senderId , chatList , chatCLicks , chatSelect );
		bind.chatsRecycler.setAdapter ( adapter );

		bind.send.setOnClickListener ( v -> {
			if ( ! bind.chatText.getText ( ).toString ( ).trim ( ).isEmpty ( ) ) {
				sendMessage ( bind.chatText.getText ( ).toString ( ).trim ( ) , "text" );
				bind.chatText.setText ( "" );
			}
		} );

		bind.chatBox.setEndIconOnClickListener ( v -> Alerts.success ( this , "Coming soon!" ) );
	}

	@Override
	protected void onStart ( ) {
		super.onStart ( );
		getChats ( );
	}

	@Override
	protected void onStop ( ) {
		super.onStop ( );
//		chatQuery.removeEventListener ( chatListener );
	}

	@Override
	public boolean onCreateOptionsMenu ( Menu menu ) {
		getMenuInflater ( ).inflate ( R.menu.empty_menu , menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected ( @NonNull MenuItem item ) {
		if ( item.getItemId ( ) == android.R.id.home ) {
			finish ( );
		}
		return true;
	}

	private void getChats ( ) {
		BaseFragment.FireRef.chatRef.child ( senderChatKey ).addValueEventListener ( new ValueEventListener ( ) {

			@Override
			public void onDataChange ( @NonNull DataSnapshot snapshot ) {
				chatList.clear ( );
				for ( DataSnapshot snap : snapshot.getChildren ( ) ) {
					chatList.add ( snap.getValue ( ChatModel.class ) );
					Alerts.log ( TAG , "CHAT SIZE: " + chatList.get ( 0 ) );
				}
				adapter.notifyDataSetChanged ( );
			}

			@Override
			public void onCancelled ( @NonNull DatabaseError error ) {

			}
		} );
	}

	//this will act as a single method to upload any type of message i.e, images, text, and videos
	private void sendMessage ( String message , String type ) {
		Map < String, String > chatMap = new HashMap <> ( );
		chatMap.put ( "type" , type );
		chatMap.put ( "message" , message );
		chatMap.put ( "sender_id" , new Prefs ( this ).getUserId ( ) );
		chatMap.put ( "timestamp" , String.valueOf ( System.currentTimeMillis ( ) ) );

		String pushKey = BaseFragment.FireRef.chatRef.push ( ).getKey ( );

		BaseFragment.FireRef.chatRef.child ( senderChatKey ).child ( pushKey ).setValue ( chatMap ).addOnCompleteListener ( task -> {
					if ( task.isSuccessful ( ) ) {
						addToChatList ( chatMap , senderId );
					}
				} )
				.addOnFailureListener ( e -> e.printStackTrace ( ) );

		BaseFragment.FireRef.chatRef.child ( receiverChatKey ).child ( pushKey ).setValue ( chatMap ).addOnCompleteListener ( task -> {
					if ( task.isSuccessful ( ) ) {
						addToChatList ( chatMap , receiverId );
					}
				} )
				.addOnFailureListener ( e -> e.printStackTrace ( ) );
	}

	// this will add the chats to the user's chat list, which they can access from the bottom menu
	private void addToChatList ( Map < String, String > data , String id ) {
		BaseFragment.FireRef.chatListRef.child ( id ).setValue ( data ).addOnCompleteListener ( task -> {
		} ).addOnFailureListener ( Throwable :: printStackTrace );
	}

	private void enableActionMode ( int position ) {
		if ( actionMode == null ) {
			actionMode = startSupportActionMode ( mCallback );
		}
		toggleSelection ( position );
	}

	private void toggleSelection ( int position ) {
		adapter.toggleSelection ( position );
		int count = adapter.getSelectedItemCount ( );

		if ( count == 0 ) {
			actionMode.finish ( );
		}
		else {
			actionMode.setTitle ( "$count Selected" );
			actionMode.invalidate ( );
		}
	}

	private void deleteInboxes ( ) {
		List < ChatModel > selectedItemPositions = adapter.getSelectedItems ( );
		Collections.reverse ( selectedItemPositions );

		for ( int i = 0 ; i < selectedItemPositions.size ( ) ; i++ ) {
			adapter.removeData ( i );
		}
		adapter.notifyDataSetChanged ( );
	}

	class ActionModeCall implements ActionMode.Callback {
		@Override
		public boolean onCreateActionMode ( ActionMode mode , Menu menu ) {
			mode.getMenuInflater ( ).inflate ( R.menu.contextual_action_bar , menu );
			return true;
		}

		@Override
		public boolean onPrepareActionMode ( ActionMode mode , Menu menu ) {
			return false;
		}

		@Override
		public boolean onActionItemClicked ( ActionMode mode , MenuItem item ) {
			int id = item.getItemId ( );
			if ( id == R.id.delete ) {
				deleteInboxes ( );
				mode.finish ( );
				return true;
			}
			else if ( id == android.R.id.home ) {
				onBackPressed ( );
				return true;
			}
			return false;
		}

		@Override
		public void onDestroyActionMode ( ActionMode mode ) {
			adapter.clearSelections ( );
			actionMode = null;
		}
	}

}