package com.petme.app.model;

public class FoundModel {

    String petAnimal;
    String breed;
    String foundLocation;
    String currentlyAt;
    String contact;
    String image;
    String timestamp;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPetAnimal() {
        return petAnimal;
    }

    public void setPetAnimal(String petAnimal) {
        this.petAnimal = petAnimal;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        this.foundLocation = foundLocation;
    }

    public String getCurrentlyAt() {
        return currentlyAt;
    }

    public void setCurrentlyAt(String currentlyAt) {
        this.currentlyAt = currentlyAt;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
