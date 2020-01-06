package com.electreca.tech;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.electreca.tech.webservice.ApiInterface;
import com.electreca.tech.webservice.BaseApiClient;

public class ElectrecaApplication extends Application {
    private static final ElectrecaApplication ourInstance = new ElectrecaApplication();
    private ApiInterface apiInterface;

    public static ElectrecaApplication getOurInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiInterface = BaseApiClient.createService(ApiInterface.class);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
