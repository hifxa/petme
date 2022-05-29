package com.petme.app.model;

public class AdoptModel {

	String age;
	String breed;
	String contact;
	String details;
	String image;
	String name;
	String timestamp;

	public String getImage ( ) {
		return image;
	}

	public void setImage ( String image ) {
		this.image = image;
	}

	public String getName ( ) {
		return name;
	}

	public void setName ( String name ) {
		this.name = name;
	}

	public String getDetails ( ) {
		return details;
	}

	public void setDetails ( String details ) {
		this.details = details;
	}

	public String getContact ( ) {
		return contact;
	}

	public void setContact ( String contact ) {
		this.contact = contact;
	}

	public String getAge ( ) {
		return age;
	}

	public void setAge ( String age ) {
		this.age = age;
	}

	public String getBreed ( ) {
		return breed;
	}

	public void setBreed ( String breed ) {
		this.breed = breed;
	}

	public String getTimestamp ( ) {
		return timestamp;
	}

	public void setTimestamp ( String timestamp ) {
		this.timestamp = timestamp;
	}
}
