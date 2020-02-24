package com.electreca.tech.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.adapter.DashboardAdapter;
import com.electreca.tech.model.DashboardReponseModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseActivity implements DashboardAdapter.ItemClick {
    private RecyclerView rv_dashboard;
    private RelativeLayout rl_main;
    private float height, width;
    private GridLayoutManager lLayout;
    private DashboardAdapter dashboardAdapter;
    private ImageButton ivEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initComponents() {
        rv_dashboard = findViewById(R.id.rv_dashboard);
        rl_main = findViewById(R.id.rl_main);
        ivEdit = findViewById(R.id.ivEdit);
    }

    @Override
    public void setListener() {
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("You are about to exit your account.Are you sure?");
                builder.setPositiveButton("Yes", (dialog, which) -> logoutFromApp(21));
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void prepareViews() {
        setHeaderView(0, false, "Dashboard", R.color.colorWhite, true, R.drawable.ic_logout);
        //--
        dashboardAdapter = new DashboardAdapter(mContext, getDashBoard(), this);
        rv_dashboard.setAdapter(dashboardAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        rv_dashboard.setLayoutManager(gridLayoutManager);

    }

    private List<DashboardReponseModel> getDashBoard() {
        List<DashboardReponseModel> dashboardReponseModel = new ArrayList<>();
        dashboardReponseModel.add(new DashboardReponseModel(0, R.drawable.ic_add_produts, "Add\nLocation"));
        dashboardReponseModel.add(new DashboardReponseModel(1, R.drawable.ic_show_location, "Show\nProducts"));
        dashboardReponseModel.add(new DashboardReponseModel(2, R.drawable.ic_add_user, "Add\nUser"));
        dashboardReponseModel.add(new DashboardReponseModel(3, R.drawable.ic_list_user, "User\nList"));
        dashboardReponseModel.add(new DashboardReponseModel(4, R.drawable.ic_aboutus, "About US"));
        dashboardReponseModel.add(new DashboardReponseModel(5, R.drawable.ic_statastics, "Statistics"));
        dashboardReponseModel.add(new DashboardReponseModel(6, R.drawable.ic_profile, "Profile"));
        dashboardReponseModel.add(new DashboardReponseModel(7, R.drawable.ic_show_location, "Show\nProducList"));
        return dashboardReponseModel;
    }


    @Override
    public void actionRequest(DashboardReponseModel dashboardReponseModel) {
        switch (dashboardReponseModel.getIndex()) {
            case 0:
                startDesireIntent(AddProductActivity.class, mContext, false, 0, null);
                break;
            case 1:
//                startDesireIntent(ShowProductActivity.class, mContext, false, 0, null);
                startDesireIntent(ShowLocationActivity.class, mContext, false, 0, null);

                break;
            case 2:
                startDesireIntent(RegisterActivity.class, mContext, false, 0, null);
                break;
            case 3:
                startDesireIntent(UserListActivity.class, mContext, false, 0, null);
                break;
            case 4:
                startDesireIntent(AboutUsActivity.class, mContext, false, 0, null);
                break;
            case 5:
                startDesireIntent(StatisticsActivity.class, mContext, false, 0, null);
                break;

            case 6:
                startDesireIntent(ProfileActivity.class, mContext, false, 0, null);
                break;
            case 7:
                startDesireIntent(ShowProductActivity.class, mContext, false, 0, null);
                break;
        }
    }
}
