package com.fordoctors.fordoctors;

public class Users {

    String email_id, name, address,city,state,current_state, country, age,
            phone_number, user_id, profile_image, device_token, gender, unique_key,verified;
    public Users() {
    }

    public Users(String email_id, String name, String address, String city, String state, String current_state, String country, String age, String phone_number, String user_id, String profile_image, String device_token, String gender, String unique_key, String verified) {
        this.email_id = email_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.current_state = current_state;
        this.country = country;
        this.age = age;
        this.phone_number = phone_number;
        this.user_id = user_id;
        this.profile_image = profile_image;
        this.device_token = device_token;
        this.gender = gender;
        this.unique_key = unique_key;
        this.verified = verified;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUnique_key() {
        return unique_key;
    }

    public void setUnique_key(String unique_key) {
        this.unique_key = unique_key;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}
