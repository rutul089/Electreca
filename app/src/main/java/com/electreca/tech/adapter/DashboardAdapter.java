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
import com.electreca.tech.model.DashboardReponseModel;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private Context mContext;
    private ItemClick itemClickListner;
    private List<DashboardReponseModel> dashboardReponseModels;

    public DashboardAdapter(Context mContext, List<DashboardReponseModel> dashboardReponseModels, ItemClick itemClickListner) {
        this.mContext = mContext;
        this.itemClickListner = itemClickListner;
        this.dashboardReponseModels = dashboardReponseModels;
    }

    @NonNull
    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DashboardAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.ViewHolder holder, int position) {
        DashboardReponseModel dashboardReponseModel;
        if (dashboardReponseModels != null) {
            dashboardReponseModel = dashboardReponseModels.get(position);
            holder.tv_label.setText(dashboardReponseModel.getTvTotal());
            holder.iv_icon.setImageResource(dashboardReponseModel.getIcon());
            holder.itemView.setOnClickListener(v -> itemClickListner.actionRequest(dashboardReponseModel))
            ;
        }
    }

    @Override
    public int getItemCount() {
        return dashboardReponseModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //--
        CustomTextView tv_label;
        ImageView iv_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_label = itemView.findViewById(R.id.tv_label);
            iv_icon = itemView.findViewById(R.id.iv_icon);

        }
    }

    public interface ItemClick {
        void actionRequest(DashboardReponseModel dashboardReponseModel);
    }
}
