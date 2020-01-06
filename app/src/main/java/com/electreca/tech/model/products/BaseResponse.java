package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("isSuccess")
    @Expose
    private boolean isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error_code")
    @Expose
    private int errorCode;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
