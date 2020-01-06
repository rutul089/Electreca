package com.electreca.tech.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList implements Parcelable {
    @SerializedName("productID")
    @Expose
    private int productID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("projectName")
    @Expose
    private String projectName;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("installDate")
    @Expose
    private String installDate;
    @SerializedName("serviceDate")
    @Expose
    private String serviceDate;
    @SerializedName("serviceCount")
    @Expose
    private int serviceCount;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("installedBy")
    @Expose
    private String empName;
    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;


    protected ProductList(Parcel in) {
        productID = in.readInt();
        name = in.readString();
        category = in.readString();
        city = in.readString();
        state = in.readString();
        village = in.readString();
        projectName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        installDate = in.readString();
        serviceDate = in.readString();
        serviceCount = in.readInt();
        isActive = in.readByte() != 0;
        empName = in.readString();
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel in) {
            return new ProductList(in);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(productID);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(village);
        parcel.writeString(projectName);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(installDate);
        parcel.writeString(serviceDate);
        parcel.writeInt(serviceCount);
        parcel.writeByte((byte) (isActive ? 1 : 0));
        parcel.writeString(empName);
        parcel.writeTypedList(notes);
    }
}
