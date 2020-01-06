package com.electreca.tech.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.electreca.tech.R;
import com.electreca.tech.model.UserRoleModel;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AddProductActivity.class.getSimpleName();
    private ImageButton ivBack;
    private Spinner sp_category;
    private String category = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    @Override
    public void initComponents() {
        //--
        ivBack = findViewById(R.id.ivBack);
        sp_category = findViewById(R.id.sp_category);
    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        //-- Spinner
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    category = "";
                    return;
                }
                category = getCategory().get(position).getUserName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void prepareViews() {
        //--
        setHeaderView(0, true, "Add Location", R.color.colorWhite, false, 0);
        //-- Set spinner
        ArrayAdapter<UserRoleModel> adapter = new ArrayAdapter<UserRoleModel>(this, R.layout.row_spinner_item, getCategory());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    public List<UserRoleModel> getCategory() {
        ArrayList<UserRoleModel> roleList = new ArrayList<>();
        roleList.add(new UserRoleModel(0, "Please select category"));
        roleList.add(new UserRoleModel(1, "Rooftop"));
        roleList.add(new UserRoleModel(2, "Solar light"));
        roleList.add(new UserRoleModel(3, "Solar Pump"));
        roleList.add(new UserRoleModel(4, "Home Light"));
        return roleList;
    }
}
