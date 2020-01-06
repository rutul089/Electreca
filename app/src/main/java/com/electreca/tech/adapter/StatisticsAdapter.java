package com.electreca.tech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private Context mContext;

    public StatisticsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StatisticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatisticsAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_statisctics, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //--
        CustomTextView tv_categoryName, tv_total_activeProduct,
                tv_total_deactiveProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            tv_categoryName = itemView.findViewById(R.id.tv_categoryName);
//            tv_total_activeProduct = itemView.findViewById(R.id.tv_total_activeProduct);
//            tv_total_deactiveProduct = itemView.findViewById(R.id.tv_total_deactiveProduct);
        }
    }
}
