package com.electreca.tech.model.specificuser;

import com.electreca.tech.model.users.UserdataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersResponseModel {
    @SerializedName("users")
    @Expose
    private UserdataModel users;

    public UserdataModel getUsers() {
        return users;
    }

    public void setUsers(UserdataModel users) {
        this.users = users;
    }
}
