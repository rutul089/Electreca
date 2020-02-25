package com.electreca.tech.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePwdRequestModel {
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("userNewPassword")
    @Expose
    private String userNewPassword;

    public ChangePwdRequestModel(int userID, String userPassword, String userNewPassword) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userNewPassword = userNewPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNewPassword() {
        return userNewPassword;
    }

    public void setUserNewPassword(String userNewPassword) {
        this.userNewPassword = userNewPassword;
    }
}
