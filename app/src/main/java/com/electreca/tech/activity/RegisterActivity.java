package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.model.UserRoleModel;
import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.model.products.ProductFromIDModel;
import com.electreca.tech.model.users.RegisterRequestModel;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ImageButton ivBack;
    private CustomEditText et_emp_name,
            et_phone,
            et_email,
            et_pwd;
    private Spinner sp_role;
    private CustomButton btn_signup;
    private int role = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initComponents() {
        ivBack = findViewById(R.id.ivBack);
        et_emp_name = findViewById(R.id.et_emp_name);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        sp_role = findViewById(R.id.sp_role);
        btn_signup = findViewById(R.id.btn_signup);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        //--
        //-- Spinner
        sp_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                role = getUserList().get(position).getUserID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void prepareViews() {
        //-
        setHeaderView(0, true, "Signup", R.color.colorWhite, false, 0);
        //--
        ArrayAdapter<UserRoleModel> adapter = new ArrayAdapter<UserRoleModel>(this, R.layout.row_spinner_item, getUserList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_role.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_signup:
                checkForValidation();
//                callAddUser();
                break;
        }
    }

    private void checkForValidation() {
        String name = et_emp_name.getText().toString();
        String password = et_pwd.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone.getText().toString();

        if (HelperMethods.checkForValidString(name)) {
            if (HelperMethods.checkForValidString(password)) {
                if (HelperMethods.checkForValidString(email)) {
                    if (HelperMethods.checkForValidString(phone)) {
                        callAddUser();
                    } else {
                        HelperMethods.showToast("Please enter phone number", mContext);
                    }
                } else {
                    HelperMethods.showToast("Please enter email", mContext);
                }
            } else {
                HelperMethods.showToast("Please enter password", mContext);
            }
        } else {
            HelperMethods.showToast("Please enter name", mContext);
        }
    }

    private void callAddUser() {
        // todo code here for adding new user


        String name = et_emp_name.getText().toString();
        String password = et_pwd.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone.getText().toString();

        if (role == 0 || role < 0) {
            HelperMethods.showToast("Please select user role ", mContext);
            return;
        }


        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            RegisterRequestModel requestModel = new RegisterRequestModel(name, email, password, role, phone);
            Call<BaseResponse> call = apiInterface.registerCall(getHeaderValue(1), requestModel);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        if (baseResponse != null) {
                            logoutFromApp(baseResponse.getErrorCode());
                            if (baseResponse.isIsSuccess()) {
                                HelperMethods.showToast("User added successful", mContext);
                            } else {
                                HelperMethods.showToast(baseResponse.getMessage(), mContext);
                            }
                        }
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

    /**
     * function for adding user role
     **/
    public List<UserRoleModel> getUserList() {
        ArrayList<UserRoleModel> roleList = new ArrayList<>();
        roleList.add(new UserRoleModel(0, "Please select user role"));
        roleList.add(new UserRoleModel(1, "Admin"));
        roleList.add(new UserRoleModel(2, "Installer"));
        roleList.add(new UserRoleModel(3, "Technician"));
        return roleList;
    }
}
