package com.titi.remotbayi.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.titi.remotbayi.AddChildActivity;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ModelChild;
import com.titi.remotbayi.utils.StringHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterChild extends RecyclerView.Adapter<AdapterChild.ViewHolder> {

    private List<ModelChild> data;
    Context context;

    public AdapterChild(List<ModelChild> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anak, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textNamaAnak.setText(data.get(position).getChildName());
//        holder.textTgl.setText(context.getString(R.string.tgl_kel) + " " + data.get(position).getTglLahir());
        holder.textTgl.setText(context.getString(R.string.tgl_kel) + " " + StringHelper.formatDate(data.get(position).getTglLahir() + " 00:00:00"));
        holder.textAnakke.setText(context.getString(R.string.anak_ke) + " " + data.get(position).getAnakKe());
        holder.textJenisKel.setText(context.getString(R.string.jenis_kel) + " " + data.get(position).getKelaminChild());
        holder.textRs.setText(context.getString(R.string.rs_name) + " " + data.get(position).getRSName());
        holder.textNamaDok.setText(context.getString(R.string.bidan_name) + " " + data.get(position).getBidanName());
        holder.textMetodeLahir.setText(context.getString(R.string.metode_lahir) + " " + data.get(position).getMetodeLahir());
        holder.imgEdit.setOnClickListener(v -> {
            Intent i = new Intent(context, AddChildActivity.class);
            i.putExtra("email", data.get(position).getUserIdChild());
            i.putExtra("anak_ke", data.get(position).getAnakKe());
            i.putExtra("status", "edit");
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_tgl)
        TextView textTgl;
        @BindView(R.id.text_anakke)
        TextView textAnakke;
        @BindView(R.id.text_nama_anak)
        TextView textNamaAnak;
        @BindView(R.id.text_jenis_kel)
        TextView textJenisKel;
        @BindView(R.id.text_rs)
        TextView textRs;
        @BindView(R.id.text_nama_dok)
        TextView textNamaDok;
        @BindView(R.id.text_metode_lahir)
        TextView textMetodeLahir;
        @BindView(R.id.img_edit)
        ImageView imgEdit;
        @BindView(R.id.cons_anak)
        ConstraintLayout consAnak;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
