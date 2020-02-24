package com.electreca.tech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electreca.tech.R;
import com.electreca.tech.components.CustomTextView;
import com.electreca.tech.model.products.Note;
import com.electreca.tech.utils.HelperMethods;

import java.util.List;

public class RecentNoteAdapter extends RecyclerView.Adapter<RecentNoteAdapter.MyViewHolder> {
    private Context context;
    private List<Note> noteList;

    public RecentNoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public RecentNoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recent_notes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentNoteAdapter.MyViewHolder holder, int position) {
        if (noteList != null && !noteList.isEmpty()) {
            Note note = noteList.get(position);
            holder.tvEmpName.setText(HelperMethods.checkNullForString(note.getEmpName()));
            holder.tvNotes.setText(HelperMethods.checkNullForString(note.getRemark()));
            holder.tvDate.setText(HelperMethods.strToDate(note.getDate(), "dd MMM YYYY"));
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView tvNotes,
                tvEmpName,
                tvDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvEmpName = itemView.findViewById(R.id.tvEmpName);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
