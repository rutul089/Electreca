package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.model.specificuser.SpecificUserResponseModel;
import com.electreca.tech.model.users.LoginResponseDataModel;
import com.electreca.tech.model.users.UserdataModel;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton ivBack, ivEdit;
    private CustomButton btn_save;
    private CustomEditText et_name,
            et_email,
            et_phone,
            et_designation;
    private CustomTextView tvResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void initComponents() {
        ivBack = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        btn_save = findViewById(R.id.btn_save);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_designation = findViewById(R.id.et_designation);
        tvResetPassword = findViewById(R.id.tvResetPassword);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "My Profile", R.color.colorWhite, true, R.drawable.ic_edit);
        setEnabled(false);
        callSpecificUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivEdit:
                setEnabled(true);
                break;
            case R.id.btn_save:
                HelperMethods.showToast("test", mContext);
                break;
            case R.id.tvResetPassword:
                startDesireIntent(ResetPasswordActivity.class, mContext, false, 0, null);
                break;
        }
    }


    private void setEnabled(boolean isEnable) {
        btn_save.setEnabled(isEnable);
        btn_save.setBackgroundResource(isEnable ? R.color.colorPrimary : R.color.colorInActive);
        et_phone.setEnabled(isEnable);
        et_email.setEnabled(isEnable);
    }

    //-- API call for fetching specific user
    private void callSpecificUser() {
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            int id = getLoggedInUser().getData().getUserData().getUserID();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            Call<SpecificUserResponseModel> call = apiInterface.fetchUserFromID(getHeaderValue(0), id);
            call.enqueue(new Callback<SpecificUserResponseModel>() {
                @Override
                public void onResponse(Call<SpecificUserResponseModel> call, Response<SpecificUserResponseModel> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        SpecificUserResponseModel responseModel = response.body();
                        if (responseModel != null) {
                            logoutFromApp(responseModel.getErrorCode());
                            if (responseModel.getData().getUsers() != null) {
                                UserdataModel userdataModel = responseModel.getData().getUsers();
                                updateUI(userdataModel);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SpecificUserResponseModel> call, Throwable t) {
                    t.printStackTrace();
                    dismissDialog();
                    HelperMethods.showGeneralSWWToast(mContext);
                }
            });

        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }

    private void updateUI(UserdataModel userdataModel) {
        et_name.setText(HelperMethods.checkNullForString(userdataModel.getUserName()));
        et_email.setText(HelperMethods.checkNullForString(userdataModel.getUserEmail()));
        et_phone.setText(HelperMethods.checkNullForString(userdataModel.getPhoneNumber()));
        int userRole = userdataModel.getUserRole();
        et_designation.setText(HelperMethods.getUserRole(mContext, userRole));
    }
}
