package com.electreca.tech.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.components.MyProgressDialog;
import com.electreca.tech.model.users.LoginResponseDataModel;
import com.electreca.tech.utils.ApplicationSharedPreferences;
import com.electreca.tech.utils.HelperLog;
import com.electreca.tech.utils.runtimepermissionhelper.ActivityManagePermission;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseActivity extends ActivityManagePermission {
    private MyProgressDialog myProgressDialog;
    public Context mContext;
    public BaseActivity mActivity;
    public Bundle mBundle = new Bundle();
    protected Drawable dividerDrawable;
    public ImageView ivHeaderBg;
    public ImageButton ivBack, ivEdit;
    public CustomTextView tvAppLabel;
    private /*static*/ int mivHeaderBgColor;
    private /*static*/ int mHeaderLabelColor;

    public void showDialog() {
        try {
            if (myProgressDialog != null && !myProgressDialog.isShowing()) {
                myProgressDialog.setCancelable(false);
                myProgressDialog.show();
            }
        } catch (Exception e) {
            HelperLog.printExceptionStack(e);
        }
    }

    public void dismissDialog() {
        try {
            if (myProgressDialog != null && myProgressDialog.isShowing()) {
                myProgressDialog.dismiss();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState/*, int layoutID*/) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        mBundle = getIntent().getExtras();
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        myProgressDialog = new MyProgressDialog(mContext);
        dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.item_decoration_divider);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initComponents();
        setListener();
        intiHeaderComps();
        prepareViews();
    }

    private void intiHeaderComps() {
        ivBack = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        ivHeaderBg = findViewById(R.id.ivHeaderBg);
        tvAppLabel = findViewById(R.id.tvAppLabel);
        mHeaderLabelColor = R.color.colorWhite;
    }

    public void setHeaderView(@Nullable int ivHeaderBgColor, boolean showBackIcon, @Nullable String headerLabel, @Nullable int headerLabelColor, boolean isEdit, int leftIcon) {
        mHeaderLabelColor = headerLabelColor;
        ivBack.setVisibility(showBackIcon ? View.VISIBLE : View.GONE);
        ivEdit.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        if (leftIcon != 0) {
            ivEdit.setImageResource(leftIcon);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvAppLabel.setTextColor(getResources().getColor(mHeaderLabelColor, null));
        } else {
            tvAppLabel.setTextColor(getResources().getColor(mHeaderLabelColor));
        }
        tvAppLabel.setText(headerLabel);
    }

    abstract public void initComponents();

    abstract public void setListener();

    abstract public void prepareViews();



    public void flushPreferences() {
        ApplicationSharedPreferences.saveObject(getString(R.string.PREFS_LOGGED_IN_USER_DETAILS), null, mContext);
    }

    public void logoutFromApp(int responseCode) {
        if(responseCode == 21 || responseCode == 20){
            if (isUserLoggedIn()){
                ApplicationSharedPreferences.set(getResources().getString(R.string.PREFS_LOGGED_IN), false, mContext);
                ApplicationSharedPreferences.saveObject(getString(R.string.PREFS_LOGGED_IN_USER_DETAILS), null, mContext);
                flushPreferences();
                startDesireIntent(LoginActivity.class, mContext, true, 0, null);
                finish();
            }
        }
    }
    /**
     * Function for starting new activity
     *
     * @param activity         activity name / class name which you want to move
     * @param context          application context
     * @param isActivityResult boolean type parameter for checking activity result
     * @param reqCode          unique request code for each activity
     * @param bundle           data which you want to pass threw activity
     **/
    public void startDesireIntent(final Class activity, final Context context, boolean isActivityResult, int reqCode, @Nullable Bundle bundle) {
        Intent desireIntent = new Intent(context, activity);
        if (bundle != null) {
            desireIntent.putExtras(bundle);
        }
        if (isActivityResult) {
            startActivityForResult(desireIntent, reqCode);
        } else {
            startActivity(desireIntent);
        }
    }

    public LoginResponseDataModel getLoggedInUser() {
        return (LoginResponseDataModel) ApplicationSharedPreferences.getSavedObject
                (getResources().getString(R.string.PREFS_LOGGED_IN_USER_DETAILS),
                        null, LoginResponseDataModel.class, mContext);
    }
    public boolean isUserLoggedIn() {
        return ApplicationSharedPreferences.getBooleanValue(getResources().getString(R.string.PREFS_LOGGED_IN), false, mContext);
    }

    public Map<String, String> getHeaderValue(@Nullable int eventName) {
        LoginResponseDataModel loginModelResponse = getLoggedInUser();
        Map<String, String> headers = new HashMap<>();
        if (eventName == 0) {
            if (loginModelResponse != null) {
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + loginModelResponse.getData().getToken());
            } else {
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
            }
        } else {
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");
        }


        return headers;
    }
}
