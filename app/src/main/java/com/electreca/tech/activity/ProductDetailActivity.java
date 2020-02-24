package com.electreca.tech.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;
import com.electreca.tech.adapter.RecentNoteAdapter;
import com.electreca.tech.components.CustomButton;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.components.DividerItemDecoration;
import com.electreca.tech.model.products.AddProductRequestModel;
import com.electreca.tech.model.products.BaseResponse;
import com.electreca.tech.model.products.ProductFromIDModel;
import com.electreca.tech.model.products.ProductList;
import com.electreca.tech.model.products.UpdateProductModel;
import com.electreca.tech.utils.Constants;
import com.electreca.tech.utils.HelperMethods;
import com.electreca.tech.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ProductDetailActivity.class.getSimpleName();
    private ImageButton ivBack, ivEdit;
    private RecyclerView rv_notes;
    private RecentNoteAdapter recentNoteAdapter;
    private ProductList productList;
    private CustomTextView tvInstalledBy, tvCategory, tvInstalledDate, tv_totalCount;
    private int serviceCount = 0;
    private CustomButton btn_update;
    private boolean buttonEnable = false;
    String currentDate;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }

    @Override
    public void initComponents() {
        //-- Getting value form previous screens
        productList = mBundle.containsKey(Constants.KEY_PRODUCT_LIST) ? mBundle.getParcelable(Constants.KEY_PRODUCT_LIST) : null;
        ivBack = findViewById(R.id.ivBack);
        rv_notes = findViewById(R.id.rv_notes);
        tvInstalledBy = findViewById(R.id.tvInstalledBy);
        tvCategory = findViewById(R.id.tvCategory);
        tvInstalledDate = findViewById(R.id.tvInstalledDate);
        tv_totalCount = findViewById(R.id.tv_totalCount);
        btn_update = findViewById(R.id.btn_update);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Product Detail", R.color.colorWhite, false, R.drawable.ic_edit);
        //--
        callGetProductDetailFromId(productList.getProductID());


    }


    private void updateUI(ProductList productList) {
        if (productList != null) {

            recentNoteAdapter = new RecentNoteAdapter(mContext, productList.getNotes());
            rv_notes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv_notes.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            rv_notes.setAdapter(recentNoteAdapter);


            tvInstalledBy.setText(HelperMethods.checkNullForString(productList.getEmpName()));
            tvCategory.setText(HelperMethods.checkNullForString(productList.getCategory()));
            serviceCount = Integer.parseInt(HelperMethods.checkNullForString(productList.getServiceCount()));
            tv_totalCount.setText("" + serviceCount);
            currentDate = HelperMethods.getCurrentDate();
//            buttonEnable = HelperMethods.dayDifference(currentDate, productList.getServiceDate()) > 1;
            String lastDate = HelperMethods.strToDate(productList.getServiceDate(), "yyyy-MM-dd");
            String _currentDate = HelperMethods.strToDate(currentDate, "yyyy-MM-dd");
            buttonEnable = HelperMethods.dayDifference(_currentDate, lastDate) >= 1;
            if (serviceCount == 0) {
                btn_update.setEnabled(true);
                btn_update.setBackgroundResource(R.color.colorPrimary);
            } else {
                btn_update.setBackgroundResource(buttonEnable ? R.color.colorPrimary : R.color.colorInActive);
                btn_update.setEnabled(buttonEnable);
            }
            Log.i(TAG, buttonEnable + "");
            id = productList.getProductID();
            tvInstalledDate.setText(HelperMethods.strToDate(productList.getInstallDate(), "dd MMM YYYY"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_update:
                callUpdateProduct(id);
                break;
        }
    }

    //-- Api
    private void callGetProductDetailFromId(int productID) {
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            Call<ProductFromIDModel> call = apiInterface.fetchProductFromID(getHeaderValue(0), productID);
            call.enqueue(new Callback<ProductFromIDModel>() {
                @Override
                public void onResponse(Call<ProductFromIDModel> call, Response<ProductFromIDModel> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        ProductFromIDModel data = response.body();
                        if (data != null) {
                            logoutFromApp(data.getErrorCode());
                            if (data.isIsSuccess()) {
                                updateUI(data.getData().getProduct());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductFromIDModel> call, Throwable t) {
                    dismissDialog();
                    HelperMethods.showGeneralSWWToast(getApplicationContext());
                    t.printStackTrace();
                }
            });
        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }

    //-- Api call for updating
    private void callUpdateProduct(int productId) {
        String empname = getLoggedInUser().getData().getUserData().getUserName();
        if (HelperMethods.checkNetwork(mContext)) {
            showDialog();
            ElectrecaApplication locationApplication = HelperMethods.getAppClassInstance(mContext);
            ApiInterface apiInterface = locationApplication.getApiInterface();
            UpdateProductModel updateProductModel = new UpdateProductModel(currentDate, serviceCount + 1, productList.isActive(), empname, "");
            Call<BaseResponse> call = apiInterface.callProductUpdate(getHeaderValue(0), productId, updateProductModel);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    dismissDialog();
                    if (response.isSuccessful()) {
                        BaseResponse baseResponse = response.body();
                        if (baseResponse != null) {
                            logoutFromApp(baseResponse.getErrorCode());
                            if (baseResponse.isIsSuccess()) {
                                HelperMethods.showToast(baseResponse.getMessage(), mContext);
                                callGetProductDetailFromId(id);
                            }
                        }
                    } else {
                        HelperMethods.showGeneralSWWToast(getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    dismissDialog();
                    HelperMethods.showGeneralSWWToast(getApplicationContext());
                    t.printStackTrace();
                }
            });
        } else {
            HelperMethods.showGeneralNICToast(mContext);
        }
    }


}
