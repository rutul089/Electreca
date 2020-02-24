package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDate {
    @SerializedName("product")
    @Expose
    private ProductList product;

    public ProductList getProduct() {
        return product;
    }

    public void setProduct(ProductList product) {
        this.product = product;
    }
}

