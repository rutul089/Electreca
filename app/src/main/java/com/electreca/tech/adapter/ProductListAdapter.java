package com.electreca.tech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context mContext;
    private ItemClick itemClickListner;


    public ProductListAdapter(Context mContext, ItemClick itemClickListner) {
        this.mContext = mContext;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //--
        CustomTextView tv_product_name,
                tv_installed_date,
                tv_product_id,
                tv_installed_by,
                tv_address,
                tv_product_history,
                tv_status;
        ImageView iv_phone,
                iv_direction;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_installed_date = itemView.findViewById(R.id.tv_installed_date);
            tv_product_id = itemView.findViewById(R.id.tv_product_id);
            tv_installed_by = itemView.findViewById(R.id.tv_installed_by);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_product_history = itemView.findViewById(R.id.tv_product_history);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_phone = itemView.findViewById(R.id.iv_phone);
            iv_direction = itemView.findViewById(R.id.iv_direction);
        }
    }

    public interface ItemClick {
        void actionRequest(int position, boolean isPhoneClick, boolean icLocation, boolean isProductHistoryClick);
    }
}
