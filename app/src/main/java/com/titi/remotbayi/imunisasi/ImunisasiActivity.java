package com.titi.remotbayi.imunisasi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ModelChild;
import com.titi.remotbayi.sqlite.SqliteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    ArrayList<HashMap<String, String>> list_dataa;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    public String url = "http://fikri.akudeveloper.com/getImmunization.php";

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
        final ProgressDialog progres = new ProgressDialog(this);
        progres.setTitle("Mohon tunggu sebentar");
        progres.setMessage("Sedang Ambil Data");
        progres.show();
        requestQueue = Volley.newRequestQueue(ImunisasiActivity.this);
        list_dataa = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progres.dismiss();
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("schedule_id", json.getString("schedule_id"));
                        map.put("schedule_title", json.getString("schedule_title"));
                        map.put("schedule_desc", json.getString("schedule_desc"));
                        map.put("schedule_time", json.getString("schedule_time"));
                        map.put("bb_standart", json.getString("bb_standart"));
                        map.put("tb_standart", json.getString("tb_standart"));
                        map.put("st_standart", json.getString("st_standart"));
                        list_dataa.add(map);
                        if (data.size() == 0) {
                            adapter = new AdapterImunisasi(getApplicationContext(), list_dataa, "", "", user.get("name"));
                        } else {
                            String tglLahir = data.get(0).getTglLahir();
                            String rsName = data.get(0).getRSName();
                            adapter = new AdapterImunisasi(getApplicationContext(), list_dataa, rsName, tglLahir, user.get("name"));
                        }
                        recImunisasi.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recImunisasi.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, error -> {
            });
        requestQueue.add(stringRequest);
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
