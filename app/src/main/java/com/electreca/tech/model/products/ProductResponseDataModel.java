package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductResponseDataModel extends BaseResponse {
    @SerializedName("Data")
    @Expose
    private Productdata data;

    public Productdata getData() {
        return data;
    }

    public void setData(Productdata data) {
        this.data = data;
    }
}
