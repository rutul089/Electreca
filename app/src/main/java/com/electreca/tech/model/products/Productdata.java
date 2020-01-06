package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Productdata {
    @SerializedName("product")
    @Expose
    private List<ProductList> product = null;

    public List<ProductList> getProduct() {
        return product;
    }

    public void setProduct(List<ProductList> product) {
        this.product = product;
    }
}
