package com.petme.app.model;

public class TaskModel {

	String task;
	String name;
	String desc;
	String timestamp;
	String status;

	public String getTask ( ) {
		return task;
	}

	public void setTask ( String task ) {
		this.task = task;
	}

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getDesc ( ) {
		return desc;
	}

	public void setDesc ( String desc ) {
		this.desc = desc;
	}

	public String getTimestamp ( ) {
		return timestamp;
	}

	public void setTimestamp ( String timestamp ) {
		this.timestamp = timestamp;
	}

	public String getStatus ( ) {
		return status;
	}

	public void setStatus ( String status ) {
		this.status = status;
	}
}
