package com.titi.remotbayi.imunisasi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.PojoRegister;
import com.titi.remotbayi.sqlite.SqliteHandler;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImmunizationList extends AppCompatActivity {

    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.edt_title)
    TextInputEditText edtTitle;
    @BindView(R.id.edt_desc)
    TextInputEditText edtDesc;
    @BindView(R.id.edt_time)
    TextInputEditText edtTime;
    @BindView(R.id.ly_time)
    TextInputLayout lyTime;
    SqliteHandler db;
    Random r;
    String id;
    @BindView(R.id.button_add_immunization)
    Button buttonAddImmunization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_immunization_list);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        r = new Random();
        id = String.valueOf(r.nextInt(9999 - 1111 + 1) - 1111);
    }

    @OnClick(R.id.button_add_immunization)
    public void onViewClicked() {
        ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
        Call<PojoRegister> call = interfaces.addSchedule(edtTitle.getText().toString(), edtDesc.getText().toString(), edtTime.getText().toString());
        call.enqueue(new Callback<PojoRegister>() {
            @Override
            public void onResponse(Call<PojoRegister> call, Response<PojoRegister> response) {
                if (response.isSuccessful()) {
                    onBackPressed();
                    Toast.makeText(AddImmunizationList.this, "Berhasil menambah jadwal imunisasi", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PojoRegister> call, Throwable t) {
                Toast.makeText(AddImmunizationList.this, "Gagal menambah jadwal imunisasi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
