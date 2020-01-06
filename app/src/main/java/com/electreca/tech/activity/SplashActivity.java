package com.electreca.tech.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.electreca.tech.BuildConfig;
import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.utils.ApplicationSharedPreferences;
import com.electreca.tech.utils.Constants;
import com.electreca.tech.utils.runtimepermissionhelper.PermissionResult;
import com.electreca.tech.utils.runtimepermissionhelper.PermissionUtils;

public class SplashActivity extends BaseActivity {
    private CustomTextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.SCREEN_SCALE = metrics.density;
    }

    @Override
    public void initComponents() {
        tvVersionName = findViewById(R.id.tv_versionName);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void prepareViews() {
        tvVersionName.setText("Version Name :" + BuildConfig.VERSION_NAME);

        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            if (ApplicationSharedPreferences.getBooleanValue(Constants.APPLICATION_OPENED_ONCE, false, mContext)) {
                proceedToTheNextActivity();
            } else {
                askMultiplePermission();
            }
        }, 2500);
    }

    private void askMultiplePermission() {
        ApplicationSharedPreferences.set(Constants.APPLICATION_OPENED_ONCE, true, mContext);
        final String permissionAsk[] = {PermissionUtils.MANIFEST_ACCESS_COARSE_LOCATION, PermissionUtils.MANIFEST_ACCESS_FINE_LOCATION, PermissionUtils.MANIFEST_GROUP_LOCATION};
        askCompactPermissions(permissionAsk, new PermissionResult() {
            @Override
            public void permissionGranted() {
                proceedToTheNextActivity();
            }

            @Override
            public void permissionDenied() {
                proceedToTheNextActivity();
            }

            @Override
            public void permissionForeverDenied() {
                proceedToTheNextActivity();
            }
        });
    }

    private void proceedToTheNextActivity() {
        if (isUserLoggedIn()) {
            startDesireIntent(DashboardActivity.class, mContext, false, 0, null);
            finish();
        } else {
            startDesireIntent(LoginActivity.class, mContext, false, 0, null);
            finish();
        }
    }

}
