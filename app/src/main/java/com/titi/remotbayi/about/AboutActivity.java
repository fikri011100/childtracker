package com.titi.remotbayi.about;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.PojoEditSchedule;
import com.titi.remotbayi.sqlite.SqliteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    public String url = "http://fikri.akudeveloper.com/getDetail.php";
    ArrayList<HashMap<String, String>> list_dataa;
    @BindView(R.id.textview_detail)
    TextView textviewDetail;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.edt_title)
    TextInputEditText edtTitle;
    @BindView(R.id.button_add_tumbuhkembang)
    Button buttonAddTumbuhkembang;
    @BindView(R.id.cons_admin)
    ConstraintLayout consAdmin;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    HashMap<String, String> user;
    SqliteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        user = db.getUserDetails();
        consAdmin.setVisibility(View.GONE);
        textviewDetail.setVisibility(View.VISIBLE);
        imgBackCreate.setOnClickListener(view -> onBackPressed());
        if (user.get("name").equals("admin")) {
            imgEdit.setVisibility(View.VISIBLE);
            imgEdit.setOnClickListener(view -> {
                consAdmin.setVisibility(View.VISIBLE);
                textviewDetail.setVisibility(View.GONE);
            });
        } else {
            imgEdit.setVisibility(View.GONE);
        }
        buttonAddTumbuhkembang.setOnClickListener(view -> {
            ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
            Call<PojoEditSchedule> call = interfaces.editDetail(edtTitle.getText().toString());
            call.enqueue(new Callback<PojoEditSchedule>() {
                @Override
                public void onResponse(Call<PojoEditSchedule> call, retrofit2.Response<PojoEditSchedule> response) {
                    if (response.isSuccessful()) {
                        consAdmin.setVisibility(View.GONE);
                        textviewDetail.setVisibility(View.VISIBLE);
                        getResponse();
                    }
                }

                @Override
                public void onFailure(Call<PojoEditSchedule> call, Throwable t) {

                }
            });
        });
        getResponse();
    }

    private void getResponse() {
        final ProgressDialog progres = new ProgressDialog(this);
        progres.setTitle("Mohon tunggu sebentar");
        progres.setMessage("Sedang Ambil Data");
        progres.show();
        requestQueue = Volley.newRequestQueue(AboutActivity.this);
        list_dataa = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progres.dismiss();
                list_dataa.clear();
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("detail");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("detail_tumbuhkembang", json.getString("detail_tumbuhkembang"));
                        map.put("id", json.getString("id"));
                        list_dataa.add(map);
                    }
                    textviewDetail.setText(list_dataa.get(0).get("detail_tumbuhkembang"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
        });
        requestQueue.add(stringRequest);
    }
}
