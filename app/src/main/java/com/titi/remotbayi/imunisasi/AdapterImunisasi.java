package com.titi.remotbayi.imunisasi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.titi.remotbayi.R;
import com.titi.remotbayi.immunization.AddImunisasiActivity;
import com.titi.remotbayi.model.Schedule;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AdapterImunisasi extends RecyclerView.Adapter<AdapterImunisasi.ViewHolder> {

    Context context;
    List<Schedule> schedule;

    public AdapterImunisasi(Context context, List<Schedule> schedule) {
        this.context = context;
        this.schedule = schedule;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imunisasi, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTitle.setText(schedule.get(position).getScheduleTitle());
        holder.textTime.setText(schedule.get(position).getScheduleTime());
        holder.viewCatatImunisasi.setOnClickListener(v -> {
            Intent i = new Intent(context, AddImunisasiActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("title", schedule.get(position).getScheduleTitle());
            i.putExtra("status", "add");
            context.startActivity(i);
        });
        holder.bgMain.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailImmunization.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("title", schedule.get(position).getScheduleTitle());
            i.putExtra("desc", schedule.get(position).getScheduleDesc());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_time)
        TextView textTime;
        @BindView(R.id.view_catat_imunisasi)
        ConstraintLayout viewCatatImunisasi;
        @BindView(R.id.view_remind_me)
        ConstraintLayout viewRemindMe;
        @BindView(R.id.bg_main)
        ConstraintLayout bgMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
