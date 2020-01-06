package com.electreca.tech.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductRequestModel {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("serviceDate")
    @Expose
    private String serviceDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serviceCount")
    @Expose
    private Integer serviceCount;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("installedBy")
    @Expose
    private String installedBy;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("notedata")
    @Expose
    private String notedata;
    @SerializedName("projectName")
    @Expose
    private String projectName;

    public AddProductRequestModel(String projectName, String category, String city, String village, String state, String serviceDate, String name, Integer serviceCount, Double longitude, Double latitude, Boolean isActive, String installedBy, String empName, String notedata) {
        this.projectName = projectName;
        this.category = category;
        this.city = city;
        this.village = village;
        this.state = state;
        this.serviceDate = serviceDate;
        this.name = name;
        this.serviceCount = serviceCount;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isActive = isActive;
        this.installedBy = installedBy;
        this.empName = empName;
        this.notedata = notedata;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
}
