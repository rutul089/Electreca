package com.electreca.tech.model.users;

import com.electreca.tech.model.products.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResponseDataModel extends BaseResponse {
    @SerializedName("Data")
    @Expose
    private UserResponseModel data;

    public UserResponseModel getData() {
        return data;
    }

    public void setData(UserResponseModel data) {
        this.data = data;
    }
}
