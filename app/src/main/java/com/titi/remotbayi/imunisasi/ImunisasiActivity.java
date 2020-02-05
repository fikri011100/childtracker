package com.titi.remotbayi.imunisasi;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.ModelChild;
import com.titi.remotbayi.model.PojoSchedule;
import com.titi.remotbayi.sqlite.SqliteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImunisasiActivity extends AppCompatActivity {

    @BindView(R.id.rec_imunisasi)
    RecyclerView recImunisasi;
    AdapterImunisasi adapter;
    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    SqliteHandler db;
    HashMap<String, String> user;
    @BindView(R.id.img_refresh_imunisasi)
    ImageView imgRefreshImunisasi;
    @BindView(R.id.cons_bwh)
    ConstraintLayout consBwh;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    Cursor cursor;
    protected List<ModelChild> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imunisasi);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        user = db.getUserDetails();
        if (user.get("name").equals("admin")) {
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            fabAdd.setVisibility(View.GONE);
        }
        getResponse();
        fabAdd.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddImmunizationList.class);
            i.putExtra("id", "none");
            i.putExtra("title", "none");
            i.putExtra("desc", "none");
            i.putExtra("time", "none");
            i.putExtra("status", "add");
            startActivity(i);
        });
        imgRefreshImunisasi.setOnClickListener(view -> getResponse());
        swipe.setOnRefreshListener(() -> {
            getResponse();
            swipe.setRefreshing(false);
            adapter.notifyDataSetChanged();
        });
    }



    private void getDataSQLite() {
        cursor = db.getBaby();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ModelChild model = new ModelChild();
                model.setId(cursor.getString(0));
                model.setChildName(cursor.getString(1));
                model.setAnakKe(cursor.getString(2));
                model.setRSName(cursor.getString(3));
                model.setBidanName(cursor.getString(4));
                model.setKelaminChild(cursor.getString(5));
                model.setUserIdChild(cursor.getString(6));
                model.setMetodeLahir(cursor.getString(7));
                model.setTglLahir(cursor.getString(8));
                data.add(model);
            }
        }
        cursor.close();
        db.close();
    }

    private void getResponse() {
        ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
        Call<PojoSchedule> call = interfaces.getSchedule();
        call.enqueue(new Callback<PojoSchedule>() {
            @Override
            public void onResponse(Call<PojoSchedule> call, Response<PojoSchedule> response) {
                if (data.size() == 0) {
                    adapter = new AdapterImunisasi(getApplicationContext(), response.body().getSchedule(), "", "", user.get("name"));
                } else {
                    String tglLahir = data.get(0).getTglLahir();
                    String rsName = data.get(0).getRSName();
                    adapter = new AdapterImunisasi(getApplicationContext(), response.body().getSchedule(), rsName, tglLahir, user.get("name"));
                }
                recImunisasi.setHasFixedSize(true);
                recImunisasi.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recImunisasi.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PojoSchedule> call, Throwable t) {
                Toast.makeText(ImunisasiActivity.this, "Maaf tidak ada data jadwal imunisasi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        getDataSQLite();
        getResponse();
    }

    @OnClick(R.id.img_back_create)
    public void onViewClicked() {
        onBackPressed();
        finish();
    }
}
