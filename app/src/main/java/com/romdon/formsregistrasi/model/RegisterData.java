package com.romdon.formsregistrasi.model;

public class RegisterData {
    private int id;
    private String name;
    private String address;
    private String photo;
    private String location;
    private String gender;
    private String phoneNumber;

    public RegisterData() {

    }

    public RegisterData(String name, String address, String photo, String location, String gender, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.location = location;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public RegisterData(int id, String name, String address, String photo, String location, String gender, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photo = photo;
        this.location = location;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
