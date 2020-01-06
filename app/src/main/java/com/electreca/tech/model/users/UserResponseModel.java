package com.electreca.tech.model.users;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponseModel {
    @SerializedName("userData")
    @Expose
    private UserdataModel userData;
    @SerializedName("Token")
    @Expose
    private String token;

    public UserdataModel getUserData() {
        return userData;
    }

    public void setUserData(UserdataModel userData) {
        this.userData = userData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
