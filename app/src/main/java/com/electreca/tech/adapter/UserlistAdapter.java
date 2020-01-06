package com.electreca.tech.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;

public class UserlistAdapter extends RecyclerView.Adapter<UserlistAdapter.ViewHolder> {
    private Context mContext;
    private ItemClick itemClickListner;


    public UserlistAdapter(Context mContext, ItemClick itemClickListner) {
        this.mContext = mContext;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public UserlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserlistAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserlistAdapter.ViewHolder holder, int position) {
            holder.ib_edit.setOnClickListener(v -> itemClickListner.actionRequest(position,false,false,true));
            holder.iv_phone.setOnClickListener(v -> itemClickListner.actionRequest(position,true,false,false));
            holder.iv_share.setOnClickListener(v -> itemClickListner.actionRequest(position,false,true,false));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //--
        ImageButton ib_edit;
        ImageView iv_phone,
                iv_share;
        CustomTextView tv_emp_name,
                tv_phone,
                tv_designation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ib_edit = itemView.findViewById(R.id.ib_edit);
            iv_phone = itemView.findViewById(R.id.iv_phone);
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_emp_name = itemView.findViewById(R.id.tv_emp_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_designation = itemView.findViewById(R.id.tv_designation);
        }
    }

    public interface ItemClick {
        void actionRequest(int position, boolean isPhoneClick, boolean icShareClick, boolean isEdit);
    }
}
