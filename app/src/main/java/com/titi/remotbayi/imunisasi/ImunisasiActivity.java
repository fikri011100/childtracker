package com.titi.remotbayi.imunisasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.PojoSchedule;
import com.titi.remotbayi.sqlite.SqliteHandler;

import java.util.HashMap;

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
        fabAdd.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddImmunizationList.class)));
    }

    private void getResponse() {
        ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
        Call<PojoSchedule> call = interfaces.getSchedule();
        call.enqueue(new Callback<PojoSchedule>() {
            @Override
            public void onResponse(Call<PojoSchedule> call, Response<PojoSchedule> response) {
                adapter = new AdapterImunisasi(getApplicationContext(), response.body().getSchedule());
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
        getResponse();
    }

    @OnClick(R.id.img_back_create)
    public void onViewClicked() {
        onBackPressed();
        finish();
    }
}
