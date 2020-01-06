package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.utils.HelperMethods;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton ivBack;
    private CustomTextView tv_website,
            tv_feedback,
            tv_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    @Override
    public void initComponents() {
        ivBack = findViewById(R.id.ivBack);
        tv_website = findViewById(R.id.tv_website);
        tv_feedback = findViewById(R.id.tv_feedback);
        tv_call = findViewById(R.id.tv_call);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        tv_website.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        tv_call.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        setHeaderView(0, true, "Contact Us", R.color.colorWhite, false, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tv_website:
                HelperMethods.showToast("Website Click",mContext);
                break;
            case R.id.tv_feedback:
                HelperMethods.showToast("Feedback Click",mContext);
                break;
            case R.id.tv_call:
                HelperMethods.showToast("Phone Click",mContext);
                break;
        }
    }
}
