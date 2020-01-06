package com.electreca.tech.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.R;
import com.electreca.tech.adapter.DashboardAdapter;
import com.electreca.tech.adapter.StatisticsAdapter;

public class StatisticsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = StatisticsActivity.class.getSimpleName();
    private ImageButton ivBack;
    private RecyclerView rv_statistics;
    private StatisticsAdapter statisticsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    @Override
    public void initComponents() {
        ivBack = findViewById(R.id.ivBack);
        rv_statistics = findViewById(R.id.rv_statistics);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Statisctics", R.color.colorWhite, false, 0);
        //--
        statisticsAdapter = new StatisticsAdapter(mContext);
        rv_statistics.setAdapter(statisticsAdapter);
        //--
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        rv_statistics.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}
