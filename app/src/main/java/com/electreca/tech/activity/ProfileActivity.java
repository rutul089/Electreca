package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomEditText;
import com.electreca.tech.components.CustomTextView;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivEdit:
                break;
            case R.id.btn_save:
                break;
            case R.id.tvResetPassword:
                startDesireIntent(ResetPasswordActivity.class, mContext, false, 0, null);
                break;
        }
    }
}
