package com.petme.app.model;

import androidx.annotation.NonNull;

public class ChatModel {

	String sender_id;
	String type;
	String message;
	String timestamp;

	public String getSender_id ( ) {
		return sender_id;
	}

	public void setSender_id ( String sender_id ) {
		this.sender_id = sender_id;
	}

	public String getType ( ) {
		return type;
	}

	public void setType ( String type ) {
		this.type = type;
	}

	public String getMessage ( ) {
		return message;
	}

	public void setMessage ( String message ) {
		this.message = message;
	}

	public String getTimestamp ( ) {
		return timestamp;
	}

	public void setTimestamp ( String timestamp ) {
		this.timestamp = timestamp;
	}


	@NonNull
	@Override
	public String toString ( ) {
		return sender_id + "\n" + type + "\n" + message + "\n" + timestamp;
	}
}
