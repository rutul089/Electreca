package com.electreca.tech.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.adapter.RecentNoteAdapter;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.components.DividerItemDecoration;
import com.electreca.tech.model.products.AddProductRequestModel;
import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.model.products.ProductFromIDModel;
import com.electreca.tech.model.products.ProductList;
import com.electreca.tech.model.products.UpdateProductModel;
import com.electreca.tech.utils.Constants;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.utils.runtimepermissionhelper.PermissionResult;
import com.electreca.tech.utils.runtimepermissionhelper.PermissionUtils;
import com.electreca.tech.webservice.ApiInterface;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ProductDetailActivity.class.getSimpleName();
    private ImageButton ivBack, ivEdit;
    private RecyclerView rv_notes;
    private RecentNoteAdapter recentNoteAdapter;
    private ProductList productList;
    private CustomTextView tvInstalledBy, tvCategory, tvInstalledDate, tv_totalCount;
    private int serviceCount = 0;
    private CustomButton btn_update;
    private boolean buttonEnable = false;
    String currentDate;
    private int id;
    public FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    double lat = 0.0, lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }

    @Override
    public void initComponents() {
        //-- Getting value form previous screens
        productList = mBundle.containsKey(Constants.KEY_PRODUCT_LIST) ? mBundle.getParcelable(Constants.KEY_PRODUCT_LIST) : null;
        ivBack = findViewById(R.id.ivBack);
        rv_notes = findViewById(R.id.rv_notes);
        tvInstalledBy = findViewById(R.id.tvInstalledBy);
        tvCategory = findViewById(R.id.tvCategory);
        tvInstalledDate = findViewById(R.id.tvInstalledDate);
        tv_totalCount = findViewById(R.id.tv_totalCount);
        btn_update = findViewById(R.id.btn_update);
        //--


    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Product Detail", R.color.colorWhite, false, R.drawable.ic_edit);
        //--
        callGetProductDetailFromId(productList.getProductID());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
//        float result[] = new float[10];
//        Location.distanceBetween(10.850516, 76.27108, 37.4219846, -122.084044, result);
//        Log.d(TAG + "distance", calculateDistance(10.850516, 76.27108, 37.4219846, -122.084044) + "");
//        Log.d(TAG + "distance", result[0]/1000 + "");
    }


    private void updateUI(ProductList productList) {
        if (productList != null) {

            recentNoteAdapter = new RecentNoteAdapter(mContext, productList.getNotes());
            rv_notes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv_notes.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            rv_notes.setAdapter(recentNoteAdapter);


            tvInstalledBy.setText(HelperMethods.checkNullForString(productList.getEmpName()));
            tvCategory.setText(HelperMethods.checkNullForString(productList.getCategory()));
            serviceCount = Integer.parseInt(HelperMethods.checkNullForString(productList.getServiceCount()));
            tv_totalCount.setText("" + serviceCount);
            currentDate = HelperMethods.getCurrentDate();
//            buttonEnable = HelperMethods.dayDifference(currentDate, productList.getServiceDate()) > 1;
            String lastDate = HelperMethods.strToDate(productList.getServiceDate(), "yyyy-MM-dd");
            String _currentDate = HelperMethods.strToDate(currentDate, "yyyy-MM-dd");
            buttonEnable = HelperMethods.dayDifference(_currentDate, lastDate) >= 1;
//            if (serviceCount == 0) {
//                btn_update.setEnabled(true);
//                btn_update.setBackgroundResource(R.color.colorPrimary);
//            } else {
//                btn_update.setBackgroundResource(buttonEnable ? R.color.colorPrimary : R.color.colorInActive);
//                btn_update.setEnabled(buttonEnable);
//            }
            Log.i(TAG, buttonEnable + "");
            id = productList.getProductID();
            tvInstalledDate.setText(HelperMethods.strToDate(productList.getInstallDate(), "dd MMM YYYY"));
            float result[] = new float[10];
            Location.distanceBetween(lat, lng, productList.getLatitude(), productList.getLongitude(), result);
            if (lat != 0.0 && lng != 0.0) {
                Log.d(TAG, "distance" + result[0]);
                if (result[0] <= 200) {
                    if (serviceCount == 0) {
                        btn_update.setEnabled(true);
                        btn_update.setBackgroundResource(R.color.colorPrimary);
                    } else {
                        btn_update.setBackgroundResource(buttonEnable ? R.color.colorPrimary : R.color.colorInActive);
                        btn_update.setEnabled(buttonEnable);
                    }
                } else {
                    btn_update.setBackgroundResource(R.color.colorInActive);
                    btn_update.setEnabled(false);
                }
            }
        }
    }

    //Here getting distance in kilometers (km)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_update:
                callUpdateProduct(id);
                break;
        }
    }

    //-- Api
    private void callGetProductDetailFromId(int productID) {
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            Call<ProductFromIDModel> call = apiInterface.fetchProductFromID(getHeaderValue(0), productID);
            call.enqueue(new Callback<ProductFromIDModel>() {
                @Override
                public void onResponse(Call<ProductFromIDModel> call, Response<ProductFromIDModel> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        ProductFromIDModel data = response.body();
                        if (data != null) {
                            logoutFromApp(data.getErrorCode());
                            if (data.isIsSuccess()) {
                                updateUI(data.getData().getProduct());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductFromIDModel> call, Throwable t) {
                    dismissDialog();
                    HelperMethods.showGeneralSWWToast(getApplicationContext());
                    t.printStackTrace();
                }
            });
        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }

    //-- Api call for updating
    private void callUpdateProduct(int productId) {
        String empname = getLoggedInUser().getData().getUserData().getUserName();
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            UpdateProductModel updateProductModel = new UpdateProductModel(currentDate, serviceCount + 1, productList.isActive(), empname, "");
            Call<BaseResponse> call = apiInterface.callProductUpdate(getHeaderValue(0), productId, updateProductModel);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        if (baseResponse != null) {
                            logoutFromApp(baseResponse.getErrorCode());
                            if (baseResponse.isIsSuccess()) {
                                HelperMethods.showToast(baseResponse.getMessage(), mContext);
                                callGetProductDetailFromId(id);
                            }
                        }
                    } else {
                        HelperMethods.showGeneralSWWToast(getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    dismissDialog();
                    HelperMethods.showGeneralSWWToast(getApplicationContext());
                    t.printStackTrace();
                }
            });
        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                    Log.d(TAG, location.getLatitude() + " " + location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLastLocation();
    }

    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lng = mLastLocation.getLongitude();
            }
        }
    };


}
