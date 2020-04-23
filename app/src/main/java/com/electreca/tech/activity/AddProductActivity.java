package com.electreca.tech.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.model.UserRoleModel;
import com.electreca.tech.model.products.AddProductRequestModel;
import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AddProductActivity.class.getSimpleName();
    private ImageButton ivBack;
    private Spinner sp_category;
    private String category = "";
    private CustomEditText et_project_name,
            et_location_name,
            et_product_id,
            et_city,
            et_state,
            et_lat,
            et_lng,
            et_notes;
    private CustomButton btn_add_device;
    public FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    @Override
    public void initComponents() {
        //--
        ivBack = findViewById(R.id.ivBack);
        et_project_name = findViewById(R.id.et_project_name);
        et_location_name = findViewById(R.id.et_location_name);
        et_product_id = findViewById(R.id.et_product_id);
        et_city = findViewById(R.id.et_city);
        et_state = findViewById(R.id.et_state);
        et_lat = findViewById(R.id.et_lat);
        et_lng = findViewById(R.id.et_lng);
        et_notes = findViewById(R.id.et_notes);
        sp_category = findViewById(R.id.sp_category);
        btn_add_device = findViewById(R.id.btn_add_device);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLastLocation();
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        btn_add_device.setOnClickListener(this);
        //-- Spinner
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    category = "";
                    return;
                }
                category = getCategory().get(position).getUserName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Add Location", R.color.colorWhite, false, 0);
        //-- Set spinner
        ArrayAdapter<UserRoleModel> adapter = new ArrayAdapter<UserRoleModel>(this, R.layout.row_spinner_item, getCategory());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(adapter);
        //--
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        getLastLocation();
        /*
        Handler handler = new Handler();
        int i = 0;
        for (AddProductRequestModel data : addLocation()) {
            i++;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callAddLocationLoop(data.getName(), data.getCategory(), data.getCity(), data.getProjectName(), data.getState(), data.getLatitude(), data.getLongitude(), data.getEmpName(), "", 0, true, "Lorem ipsum dolor sit amet.", data.getInstallDate());
                    }
                }, 1000*i);
            }

        }
*/
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
                                    // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    setLocation(latitude,longitude);


                                    Log.d(TAG, location.toString());
                                }
                            }
                        }
                );
            }
        }
    }

    private void setLocation(double lat, double lng) {
        et_lat.setEnabled(false);
        et_lng.setEnabled(false);
        et_lat.setText(lat+"");
        et_lng.setText(lng+"");
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            et_city.setText(addresses.get(0).getLocality());
            et_state.setText(addresses.get(0).getAdminArea());
        } catch (IOException e) {
            e.printStackTrace();
            et_lat.setEnabled(true);
            et_lng.setEnabled(true);
        }
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

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
                setLocation(latitude,longitude);
                Log.d(TAG, mLastLocation.toString());
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_add_device:
                callAddProduct();
                break;
        }
    }

    //-- APi call for adding produict

    private void callAddProduct() {

        String projectName = et_project_name.getText().toString();
        String locationName = et_location_name.getText().toString();
        String productId = et_product_id.getText().toString();
        String note = et_notes.getText().toString();
        String empName = getLoggedInUser().getData().getUserData().getUserName();
        String city = et_city.getText().toString();
        String state = et_state.getText().toString();
        double lat = Double.parseDouble(et_lat.getText().toString());
        double lng = Double.parseDouble(et_lng.getText().toString());

        if (!HelperMethods.checkForValidString(category)) {
            HelperMethods.showToast("Please select category", mContext);
            return;
        }

        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            AddProductRequestModel addProductRequestModel = new AddProductRequestModel(projectName, category, city, "", state, "",
                    locationName, 0, lng, lat, true, empName, empName, note, HelperMethods.getCurrentDate());

            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            Call<BaseResponse> call = apiInterface.addNewProduct(getHeaderValue(0), addProductRequestModel);
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
                            } else {
                                HelperMethods.showToast(baseResponse.getMessage(), mContext);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    dismissDialog();
                    t.printStackTrace();
                    HelperMethods.showGeneralSWWToast(mContext);
                }
            });


        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }


    }

    private void callAddLocationLoop(String locationName, String category, String city, String projectName, String state,
                                     double lat, double lng, String empName, String servicedate, int count, boolean isActive, String note, String currentDate) {
        AddProductRequestModel addProductRequestModel = new AddProductRequestModel(projectName, category, city, "", state, "",
                locationName, 0, lng, lat, true, empName, empName, note, currentDate);
        ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
        ApiInterface apiInterface = locationApplication.getApiInterface();
        Call<BaseResponse> call = apiInterface.addNewProduct(getHeaderValue(0), addProductRequestModel);
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    dismissDialog();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    dismissDialog();
                }
            });
        } else {

        }


    }

    public List<UserRoleModel> getCategory() {
        ArrayList<UserRoleModel> roleList = new ArrayList<>();
        roleList.add(new UserRoleModel(0, "Please select category"));
        roleList.add(new UserRoleModel(1, "Rooftop"));
        roleList.add(new UserRoleModel(2, "Solar light"));
        roleList.add(new UserRoleModel(3, "Solar Pump"));
        roleList.add(new UserRoleModel(4, "Home Light"));
        return roleList;
    }


    public List<AddProductRequestModel> addLocation() {
        ArrayList<AddProductRequestModel> roleList = new ArrayList<>();

        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospital", 0, 77.88460634, 21.80879623, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-23T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospital", 0, 78.26812323, 22.00954507, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-31T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospital", 0, 77.9097846, 22.19492203, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-10T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospital", 0, 78.124486, 21.9247444, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-23T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospitalan", 0, 78.26812323, 21.64826267, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-23T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospitl", 0, 77.92075589, 21.62323438, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-3T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Betul", "", "MP", "", "CHC Hospital", 0, 77.5447454, 21.9109278, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-2T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "dhar ", "", "MP", "", "SDM MANWAR", 0, 75.0870236, 22.2326428, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-1T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "indore ", "", "MP", "", "SDM DEPALPUR", 0, 75.5457668, 22.8502695, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-13T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "dhar ", "", "MP", "", "SDM KUKCHHI", 0, 74.7562466, 22.2023475, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-1T21:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "indore ", "", "MP", "", "SDM BADNAWAR", 0, 75.24049, 23.0215609, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-23T20:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "indore ", "", "MP", "", "SDM SANWER", 0, 75.8464445, 22.7541031, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-22T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "indore ", "", "MP", "", "CEO SANWER ", 0, 75.8464445, 22.7541031, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-23T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "indore ", "", "MP", "", "CEO DEPALPUR", 0, 75.5457668, 22.8502695, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-9T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "JHABUA ", "", "MP", "", "SDM THANDLA", 0, 74.5768, 23.007956, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-10T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "JHABUA ", "", "MP", "", "SDM BHABRA", 0, 74.797448, 23.0098769, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-23T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "ALIRAJPUR ", "", "MP", "", "SDM JABAT ", 0, 74.566695, 22.416705, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-1T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "JHABUA ", "", "MP", "", "SDM MEGHNAGAR", 0, 74.545503, 22.908052, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-18T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "JHABUA ", "", "MP", "", "SDM RANAPUR", 0, 74.5214197, 22.648625, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-11T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "JHABUA ", "", "MP", "", "SDM PETLAWAD", 0, 74.797448, 23.0098769, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-02-20T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "ALIRAJPUR ", "", "MP", "", "CEO BHABHARA ", 0, 74.797448, 23.0098769, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-31T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "ALIRAJPUR ", "", "MP", "", "CEO JOBAT", 0, 74.566695, 22.416705, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-20T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "khargone ", "", "MP", "", "sdm rajpur", 0, 75.16195, 21.9041, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-30T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "badwani", "", "MP", "", "sdm pansemal", 0, 74.7571, 21.6914, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-03T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "khargone ", "", "MP", "", "sdm sendhawa", 0, 75.1142249, 21.6906179, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-20T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "khargone ", "", "MP", "", "sdm mandleswar", 0, 75.6807689, 22.1605112, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-31T13:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "badwah ", "", "MP", "", "sdm badwah", 0, 76.040429, 22.252609, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-31T12:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "khandwa", "", "MP", "", "ceo khandwa", 0, 76.3369479, 21.857174, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-11T11:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Burhanpur", "", "MP", "", "SDM Office", 0, 76.56332, 21.36574, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2020-01-07T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Burhanpur", "", "MP", "", "Subhash School", 0, 76.22187, 21.31111, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-31T22:43:59"));
        roleList.add(new AddProductRequestModel("Solar Projecct", "Rooftop", "Burhanpur", "", "MP", "", "GHSS", 0, 76.23021, 21.30802, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.", "2019-12-01T22:43:59"));

//        roleList.add(new AddProductRequestModel("Jigen Vora , Paldi", "Rooftop", "AHD", "", "Gujarat", "", "Shrinand Nagar Zone", 0, 72.5615, 23.0149, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Milan R Purohit 2kw", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.147, 22.3341, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("vinodiniben Pandya 8KW", "Solar Pump", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.2018, 22.315, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Rajendrabhai Ratilal Patel", "Home Light", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.52, 23.06, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Sudhir V Godbole 2 KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.2297, 22.2957, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Dewanandbhai S Solanki", "Solar Pump", "Ahmedabad", "", "Gujarat", "", "Gandhinagar Zone", 0, 72.64, 23.19, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Patel Nitinkumar Ratilal", "Home Light", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.31, 23.03, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Patel Mahesh Ramnikbhai", "Solar light", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.51, 23.06, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Upendrabhai Shah 10KW", "Solar Pump", "AHD", "", "Gujarat", "", "Shrinand Nagar Zone", 0, 72.5437, 23.0109, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Patel Vijaykumar Ramnikbhai", "Rooftop", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.51, 23.06, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Vinitaben A Patel 3KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1799, 22.265, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Prafulbhai Shantilal Gandhi", "Solar Pump", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.52, 23.04, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Bansibhai Mahasukhbhai Patel", "Home Light", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.52, 23.03, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Sharmila Rajiv Mehta", "Solar light", "Ahmedabad", "", "Gujarat", "", "Gandhinagar Zone", 0, 72.64, 23.19, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Ramanbhai Punjabhai Parmar", "Solar Pump", "Chandkheda (M)", "", "Gujarat", "", "GANDHINAGAR O&M DIVISION", 0, 72.85, 23.11, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Ashish Tamboli 3KW", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.182, 22.2821, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Rajesh Tamboli 4 KW", "Home Light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.182, 22.2821, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Kaminiben D Sheth ", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.16, 22.285, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Sumit Bhogilal Kansara", "Rooftop", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.54, 23.017, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Pankaj Govindlal Patel", "Rooftop", "Ahmedabad", "", "Gujarat", "", "City Zone", 0, 72.59, 23.05, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Ankita V Patel -3.25KW", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.2492, 22.2946, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Pravin P Patel 3KW", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.2345, 22.3288, true, "Rutul Mehta", "Rutul Mehta", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Baldev Patel 3Kw", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.195, 22.2726, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Siddharth Gupte 3KW", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1979, 22.2474, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Shantilal Tamboli 3KW", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1833, 22.2841, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Birva Ketan Vora", "Rooftop", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.55, 23.06, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Dashrathbhai Mangabhai Patel", "Rooftop", "Chandlodiya (M+OG) (Part)", "", "Gujarat", "", "GANDHINAGAR O&M DIVISION", 0, 72.51, 23.11, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Rehanaben chashmawala 3kw", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1531, 22.2912, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Nandkishor Jaynarayan Pathak", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.19, 22.31, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("NARENDRA R SHAH 5KW N", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1742, 22.295, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("shaileshkumar K Patel", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1599, 22.2843, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("santosh garg 15kw", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1414, 22.3115, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Kailash agarwal 10KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1423, 22.3139, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Shabbirbhai 10KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1913, 22.327, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("MOHAMMADI N CHASMAWALA 5 KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1531, 22.2912, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Viral Kothari 4KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1535, 22.3053, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Devinder Kaur 8KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1974, 22.2504, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Sharadchandra Dave 3KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.2051, 22.3218, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Mukund Patel 3 Kw", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1981, 22.2754, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Manjulaben K Vyas 4KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.206, 22.3211, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("NAIRROOTI J BADHEKA 2KW N", "Solar light", "Bil", "", "Gujarat", "", "JAMBUVA O&M DIVISION", 0, 73.1487, 22.2587, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Hemang Patel Bill 4kw", "Solar light", "Bil", "", "Gujarat", "", "JAMBUVA O&M DIVISION", 0, 73.1491, 22.2575, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Manish Rathod 4KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1294, 22.2912, true, "Dhaval Shah", "Dhaval Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("UDAYKUMAR SINHA 3 KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1995, 22.2443, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Pravinbhai Jivatlal Kothari", "Solar light", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.5605, 23.0177, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("BHASKAR ARANDE 3KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.18, 22.3341, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Dinanath Sehgal 3KW N", "Home Light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1976, 22.2473, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("KANTIBHAI H AMIN 3 KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1784, 22.2667, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("UDAY PUNEKAR 3KW N", "Home Light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1774, 22.277, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("AJAY PATEL 4KW N", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1877, 22.2758, true, "Upendra Shah", "Upendra Shah", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("VASANTBHAI MISTRY 4KW N", "Solar Pump", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.1879, 22.2776, true, "Prahar Seth", "Prahar Seth", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("Mohanbhai M Kachiya 5KW N", "Home Light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.184, 22.2743, true, "Prahar Seth", "Prahar Seth", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("BHOGILAL J GANDHI 5KW", "Solar light", "Vadodara (M Corp+OG)", "", "Gujarat", "", "LALBAUG DIVISION", 0, 73.196, 22.277, true, "Prahar Seth", "Prahar Seth", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempus."));
//        roleList.add(new AddProductRequestModel("Milan Shah , Paldi", "Rooftop", "Ahmedabad", "", "Gujarat", "", "Naranpura Zone", 0, 72.5527, 23.0066, true, "Prahar Seth", "Prahar Seth", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("Narendra Shah 3KW N", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI EAST DIVISION", 0, 73.2309, 22.3022, true, "Prahar Seth", "Prahar Seth", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));
//        roleList.add(new AddProductRequestModel("ABDUL RASHID 3KW N", "Rooftop", "Vadodara (M Corp+OG)", "", "Gujarat", "", "VISHVAMITRI(W) DIVISION", 0, 73.1606, 22.2936, true, "Prahar Seth", "Prahar Seth", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus convallis."));


        return roleList;
    }

}
