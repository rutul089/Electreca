package com.electreca.tech.model;

public class DashboardReponseModel {
    private int index;
    private int icon;
    private String tvTotal;

    public DashboardReponseModel(int index, int icon, String tvTotal) {
        this.index = index;
        this.icon = icon;
        this.tvTotal = tvTotal;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTvTotal() {
        return tvTotal;
    }

    public void setTvTotal(String tvTotal) {
        this.tvTotal = tvTotal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
