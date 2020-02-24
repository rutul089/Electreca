package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.model.users.LoginRequestModel;
import com.electreca.tech.model.users.LoginResponseDataModel;
import com.electreca.tech.utils.ApplicationSharedPreferences;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = LoginActivity.class.getSimpleName();
    private CustomEditText et_email,
            et_pwd;
    private CustomTextView tv_forgot_pwd, tv_terms;
    private CustomButton btn_login;
    private String mPassword, mEmail;
    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initComponents() {
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);
        tv_terms = findViewById(R.id.tv_terms);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public void setListener() {
        btn_login.setOnClickListener(this);
        tv_forgot_pwd.setOnClickListener(this);
        tv_terms.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {

    }

    private void callLogin(String mEmail, String mPassword) {
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            LoginRequestModel loginRequestModel = new LoginRequestModel(mEmail, mPassword);
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();

            Call<LoginResponseDataModel> call = apiInterface.loginCall(getHeaderValue(1), loginRequestModel);
            call.enqueue(new Callback<LoginResponseDataModel>() {
                @Override
                public void onResponse(Call<LoginResponseDataModel> call, Response<LoginResponseDataModel> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        LoginResponseDataModel loginResponseDataModel = response.body();
                        if (loginResponseDataModel != null) {
                            if (loginResponseDataModel.isIsSuccess()) {
//                                logoutFromApp(loginResponseDataModel.getErrorCode());
                                ApplicationSharedPreferences.set(getResources().getString(R.string.PREFS_LOGGED_IN), true, mContext);
                                ApplicationSharedPreferences.saveObject(getString(R.string.PREFS_LOGGED_IN_USER_DETAILS), loginResponseDataModel, mContext);
                                startDesireIntent(DashboardActivity.class, mContext, false, 0, null);
                                finish();
                            }else {
                                HelperMethods.showToast(loginResponseDataModel.getMessage(),mContext);
                            }
                        }
                    }else {
                        HelperMethods.showGeneralSWWToast(mContext);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseDataModel> call, Throwable t) {
                    dismissDialog();
                    HelperMethods.showGeneralNICToast(mContext);
                    t.printStackTrace();
                }
            });

        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (checkForValidation()) {
                    callLogin(mEmail, mPassword);
                }
//                startDesireIntent(DashboardActivity.class, mContext, false, 0, null);
//                finish();
                break;
            case R.id.tv_forgot_pwd:
                HelperMethods.showToast("Forgot Pwd", mContext);
                break;
            case R.id.tv_terms:
                HelperMethods.showToast("Terms and Condition", mContext);
                break;
            default:
                break;
        }
    }

    private boolean checkForValidation() {
        mPassword = et_pwd.getText().toString().trim();
        mEmail = et_email.getText().toString().trim();
        if (!HelperMethods.isValidEmail(mEmail)) {
            et_email.setError("Enter valid email.");
        } else if (!HelperMethods.checkForValidString(mPassword)) {
            et_pwd.setError("Enter valid Password");
        } else {
            validate = true;
        }
        return validate;
    }
}
