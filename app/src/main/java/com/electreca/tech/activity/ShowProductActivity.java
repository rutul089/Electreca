package com.electreca.tech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.adapter.ProductListAdapter;

public class ShowProductActivity extends BaseActivity implements View.OnClickListener, ProductListAdapter.ItemClick {
    private RecyclerView rv_showProduct;
    private ProductListAdapter productListAdapter;
    private ImageButton ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
    }

    @Override
    public void initComponents() {
        rv_showProduct = findViewById(R.id.rv_productList);
        ivBack = findViewById(R.id.ivBack);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Show Product", R.color.colorWhite, false, 0);
        //--
        productListAdapter = new ProductListAdapter(mContext, this);
        rv_showProduct.setHasFixedSize(true);
        rv_showProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_showProduct.setAdapter(productListAdapter);
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
    public void actionRequest(int position, boolean isPhoneClick, boolean icLocation, boolean isProductHistoryClick) {

    }
}
