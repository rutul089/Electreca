package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.model.specificuser.SpecificUserResponseModel;
import com.electreca.tech.model.users.ChangePwdRequestModel;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private CustomEditText et_pwd,
            et_new_pwd,
            et_conform_pwd;
    private CustomButton btn_upadatePwd;
    private ImageButton ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    public void initComponents() {
        et_pwd = findViewById(R.id.et_pwd);
        et_new_pwd = findViewById(R.id.et_new_pwd);
        et_conform_pwd = findViewById(R.id.et_conform_pwd);
        btn_upadatePwd = findViewById(R.id.btn_upadatePwd);
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    public void setListener() {
        btn_upadatePwd.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Change Password", R.color.colorWhite, false, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_upadatePwd:
                if (checkForValidation()) {
                    callChangePassword();
                }
                break;
        }
    }

    //-- Validation
    private boolean checkForValidation() {
        String pwd = et_pwd.getText().toString();
        String newPwd = et_new_pwd.getText().toString();
        String confPwd = et_conform_pwd.getText().toString();
        boolean isValid = false;
        if (HelperMethods.checkForValidString(pwd)) {
            if (HelperMethods.checkForValidString(newPwd)) {
                if (HelperMethods.checkForValidString(confPwd)) {
                    if (newPwd.equals(confPwd)) {
                        isValid = true;
                    } else {
                        isValid = false;
                        HelperMethods.showToast("New password and confirm password must be same", mContext);
                    }
                } else {
                    isValid = false;
                    HelperMethods.showToast("Enter confirm password", mContext);
                }
            } else {
                isValid = false;
                HelperMethods.showToast("Enter New password", mContext);
            }
        } else {
            isValid = false;
            HelperMethods.showToast("Enter password", mContext);
        }
        return isValid;
    }

    //-- Call change password API
    private void callChangePassword() {
        String pwd = et_pwd.getText().toString();
        String newPwd = et_new_pwd.getText().toString();
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();

            int userID = getLoggedInUser().getData().getUserData().getUserID();
            ChangePwdRequestModel changePwdRequestModel = new ChangePwdRequestModel(userID, pwd, newPwd);

            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            Call<BaseResponse> call = apiInterface.callChangePwd(getHeaderValue(0), changePwdRequestModel);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        if (baseResponse != null) {
                            logoutFromApp(baseResponse.getErrorCode());
                            HelperMethods.showToast(baseResponse.getMessage(), mContext);

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
}
