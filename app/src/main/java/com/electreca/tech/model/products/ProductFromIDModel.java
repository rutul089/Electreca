package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductFromIDModel extends BaseResponse {
    @SerializedName("Data")
    @Expose
    private ProductDate data;

    public ProductDate getData() {
        return data;
    }

    public void setData(ProductDate data) {
        this.data = data;
    }


}

