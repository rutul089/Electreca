package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.model.UserRoleModel;
import com.electreca.tech.utils.HelperMethods;

import java.util.ArrayList;
import java.util.List;

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
                callAddUser();
                break;
        }
    }

    private void callAddUser() {
        // todo code here for adding new user
        HelperMethods.showToast("Signup btn click", mContext);
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
