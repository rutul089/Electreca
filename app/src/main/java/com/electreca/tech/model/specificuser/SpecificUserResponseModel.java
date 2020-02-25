package com.electreca.tech.model.specificuser;

import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.model.users.UserResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificUserResponseModel extends BaseResponse {
    @SerializedName("Data")
    @Expose
    private UsersResponseModel data;

    public UsersResponseModel getData() {
        return data;
    }

    public void setData(UsersResponseModel data) {
        this.data = data;
    }
}
