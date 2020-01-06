package com.electreca.tech.model.users;

public class UserRoleModel {

    private int userID;
    private String userName;

    public UserRoleModel(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return this.userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
