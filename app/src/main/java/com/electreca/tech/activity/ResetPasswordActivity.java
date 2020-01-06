package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.utils.HelperMethods;

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
                HelperMethods.showToast("Update Pwd", mContext);
                break;
        }
    }
}
