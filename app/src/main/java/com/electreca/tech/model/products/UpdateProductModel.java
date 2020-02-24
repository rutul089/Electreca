package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProductModel {
    @SerializedName("serviceDate")
    @Expose
    private String serviceDate;
    @SerializedName("serviceCount")
    @Expose
    private int serviceCount;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("notedata")
    @Expose
    private String notedata;

    public UpdateProductModel(String serviceDate, int serviceCount, boolean isActive, String empName, String notedata) {
        this.serviceDate = serviceDate;
        this.serviceCount = serviceCount;
        this.isActive = isActive;
        this.empName = empName;
        this.notedata = notedata;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNotedata() {
        return notedata;
    }

    public void setNotedata(String notedata) {
        this.notedata = notedata;
    }
}
