package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.adapter.UserlistAdapter;
import com.electreca.tech.utils.HelperMethods;

public class UserListActivity extends BaseActivity implements View.OnClickListener, UserlistAdapter.ItemClick {

    private RecyclerView rv_user_list;
    private ImageButton ivBack;
    private UserlistAdapter userlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_activitty);
    }

    @Override
    public void initComponents() {
        rv_user_list = findViewById(R.id.rv_user_list);
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0,true,"Users",R.color.colorWhite,false,0);
        //--
        userlistAdapter = new UserlistAdapter(mContext, this);
        rv_user_list.setHasFixedSize(true);
        rv_user_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_user_list.setAdapter(userlistAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void actionRequest(int position, boolean isPhoneClick, boolean icShareClick, boolean isEdit) {
        if (isEdit) {
            startDesireIntent(ProfileActivity.class, mContext, false, 0, null);
        } else if (isPhoneClick) {
            HelperMethods.showToast("Phone click", mContext);
        } else {
            HelperMethods.showToast("Share click", mContext);
        }
    }
}
