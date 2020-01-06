package com.electreca.tech.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.electreca.tech.R;
import com.electreca.tech.model.products.ProductList;
import com.electreca.tech.utils.Constants;
import com.electreca.tech.utils.HelperMethods;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    LayoutInflater inflater = null;
    private Context mContext;

    public MarkerInfoWindowAdapter(Context mContext) {
        this.mContext = mContext;
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.row_info_window, null);
    }


    private void renderWindowContent(Marker marker, View view) {
        //-- Declaration
        TextView tv_category, tv_location, tv_name;
        //-- Common Text
        tv_category = view.findViewById(R.id.tv_category);
        tv_location = view.findViewById(R.id.tv_location);
        tv_name = view.findViewById(R.id.tv_name);
        //--
        Gson g = new Gson();
        ProductList productData = g.fromJson(marker.getSnippet(), ProductList.class);
        if (productData != null) {
//            tv_category.setBackgroundColor(Color.parseColor(productData.isActive() ? "#6A1589" : "#9D0B0B"));
            tv_category.setText(HelperMethods.checkNullForString(productData.getCategory()));
            tv_location.setText(HelperMethods.checkNullForString(productData.getCity()));
            tv_name.setText(HelperMethods.checkNullForString(productData.getName()));
        }
        int default_width = 250; // -- IF LAYOUT LOOK TO SMALL CHANGE THIS TO 300 AND WIEGHT TO 0.5 AND 1.5
        int new_width = (int) (default_width * Constants.SCREEN_SCALE);
        view.setLayoutParams(new LinearLayout.LayoutParams(new_width, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowContent(marker, mWindow);
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowContent(marker, mWindow);
        return mWindow;
    }
}
