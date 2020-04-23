package com.electreca.tech.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.app.ActivityCompat;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.adapter.MarkerInfoWindowAdapter;
import com.electreca.tech.model.products.ProductList;
import com.electreca.tech.model.products.ProductResponseDataModel;
import com.electreca.tech.utils.Constants;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowLocationActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private ImageButton ivBack;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
    }

    @Override
    public void initComponents() {
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Show Location", R.color.colorWhite, false, 0);
        initializeMap();
    }

    private void initializeMap() {
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        int role = getLoggedInUser().getData().getUserData().getUserRole();
        if (role != 2) {
            Gson g = new Gson();
            ProductList productData = g.fromJson(marker.getSnippet(), ProductList.class);
            mBundle = new Bundle();
            mBundle.putParcelable(Constants.KEY_PRODUCT_LIST, productData);
            startDesireIntent(ProductDetailActivity.class, mContext, false, 0, mBundle);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            getGoogleMapSetting(googleMap);
            moveToDefaultLocation(googleMap);
            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnInfoWindowClickListener(this);

            callGetProduct();
        } else {
            HelperMethods.showGeneralSWWToast(mContext);
        }
    }

    private void callGetProduct() {
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            Call<ProductResponseDataModel> call = apiInterface.fetchProduct(getHeaderValue(0));
            call.enqueue(new Callback<ProductResponseDataModel>() {
                @Override
                public void onResponse(Call<ProductResponseDataModel> call, Response<ProductResponseDataModel> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        ProductResponseDataModel responseDataModel = response.body();
                        if (responseDataModel != null) {
                            if (responseDataModel.isIsSuccess()) {
                                logoutFromApp(responseDataModel.getErrorCode());
                                List<ProductList> productLists = new ArrayList<>();
                                productLists = responseDataModel.getData().getProduct();
                                if (productLists != null && !productLists.isEmpty()) {
                                    addMarker(productLists);
                                }
                            }
                        }
                    } else {
                        HelperMethods.showGeneralSWWToast(mContext);
                    }
                }

                @Override
                public void onFailure(Call<ProductResponseDataModel> call, Throwable t) {
                    dismissDialog();
                    HelperMethods.showGeneralSWWToast(getApplicationContext());
                    t.printStackTrace();
                }
            });
        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }

    private void addMarker(List<ProductList> productLists) {
        MarkerOptions markerOptions = new MarkerOptions();
        MarkerInfoWindowAdapter windowAdapter = new MarkerInfoWindowAdapter(mContext);
        for (ProductList data : productLists) {
            markerOptions.position(new LatLng(data.getLatitude(), data.getLongitude()));
//            markerOptions.title(data.getName());
            markerOptions.snippet(jsonString(data));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(getPin(data.getCategory(), data.isActive())));
            Marker d = mMap.addMarker(markerOptions);
            d.setZIndex(1.0f);
            d.setTag(Constants.CATEGORYTYPE.SOLAR);
        }
        mMap.setInfoWindowAdapter(windowAdapter);
    }

    //-- Method for converting json object to string
    private String jsonString(ProductList productList) {
        Gson gson = new Gson();
        return gson.toJson(productList);
    }


    private void moveToDefaultLocation(GoogleMap googleMap) {
        LatLng florida = new LatLng(20.5937, 78.9629);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(florida));
    }

    private void getGoogleMapSetting(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.setMinZoomPreference(1);
//        googleMap.setMaxZoomPreference(18);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.isBuildingsEnabled();
        googleMap.setBuildingsEnabled(false);
        googleMap.isIndoorEnabled();
        googleMap.setIndoorEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    /**
     * function for showing custom marker image
     *
     * @param category  received category
     * @param isOffline received flag for product is offline or online
     * @return function will return resource image for product
     **/
    public static int getPin(String category, boolean isOffline) {
        int resourceImage = 0;
        switch (category) {
            case "Rooftop":
                resourceImage = R.drawable.ic_rooftop;
//                resourceImage = isOffline ? R.drawable.ic_ic_rooftop_offline : R.drawable.ic_ic_rooftop;
                break;
            case "Solar light":
                resourceImage = R.drawable.ic_solarlight;
//                resourceImage = isOffline ? R.drawable.ic_ic_solar_light_offline : R.drawable.ic_ic_solar_light;
                break;
            case "Solar Pump":
                resourceImage = R.drawable.ic_solar_pump;
//                resourceImage = isOffline ? R.drawable.ic_ic_solar_pump_offline : R.drawable.ic_ic_solar_pump;
                break;
            case "Home Light":
                resourceImage = R.drawable.ic_home_light;
//                resourceImage = isOffline ? R.drawable.ic_ic_home_light_offline : R.drawable.ic_ic_home_light;
                break;
            default:
                resourceImage = R.drawable.ic_solarlight;
//                resourceImage = isOffline ? R.drawable.ic_ic_solar_light_offline : R.drawable.ic_ic_solar_light;
                break;
        }
        return resourceImage;

    }


}
