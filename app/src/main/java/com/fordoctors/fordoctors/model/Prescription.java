package com.fordoctors.fordoctors.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Prescription implements Serializable {

    String key, user_id, email, city, country, timestamp, state, age, gender, name, details, profile_image;
    ArrayList<Med> medicine;

    public Prescription() {
    }

    public Prescription(String key, String user_id, String email, String city, String country, String timestamp, String state, String age, String gender, String name, String details, String profile_image, ArrayList<Med> medicine) {
        this.key = key;
        this.user_id = user_id;
        this.email = email;
        this.city = city;
        this.country = country;
        this.timestamp = timestamp;
        this.state = state;
        this.age = age;
        this.gender = gender;
        this.name = name;
        this.details = details;
        this.profile_image = profile_image;
        this.medicine = medicine;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public ArrayList<Med> getMedicine() {
        return medicine;
    }

    public void setMedicine(ArrayList<Med> medicine) {
        this.medicine = medicine;
    }
}
