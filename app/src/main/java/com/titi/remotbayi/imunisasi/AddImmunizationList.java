package com.titi.remotbayi.imunisasi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.PojoEditSchedule;
import com.titi.remotbayi.model.PojoRegister;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.GeneralDoubleButtonDialog;

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
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    SqliteHandler db;
    Random r;
    String id, idTemp, status, title, desc, time;
    @BindView(R.id.button_add_immunization)
    Button buttonAddImmunization;
    @BindView(R.id.txt_atas)
    TextView txtAtas;
    GeneralDoubleButtonDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_immunization_list);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        r = new Random();
        id = String.valueOf(r.nextInt(9999 - 1111 + 1) - 1111);
        status = getIntent().getStringExtra("status");
        idTemp = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        time = getIntent().getStringExtra("time");
        if (status.equals("edit")) {
            txtAtas.setText("Edit List Imunisasi");
            imgDelete.setVisibility(View.VISIBLE);
            edtTitle.setText(title);
            edtDesc.setText(desc);
            edtTime.setText(time);
            buttonAddImmunization.setText("Edit Data Imunisasi");
        }
        imgBackCreate.setOnClickListener(view -> onBackPressed());
        imgDelete.setOnClickListener(view -> {
            deleteImmunization();
        });
    }

    @OnClick(R.id.button_add_immunization)
    public void onViewClicked() {
        if (status.equals("add")) {
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
        } else if (status.equals("edit")) {
            ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
            Call<PojoEditSchedule> call = interfaces.editSchedule(idTemp, edtTitle.getText().toString(), edtDesc.getText().toString(), edtTime.getText().toString());
            call.enqueue(new Callback<PojoEditSchedule>() {
                @Override
                public void onResponse(Call<PojoEditSchedule> call, Response<PojoEditSchedule> response) {
                    if (response.isSuccessful()) {
                        onBackPressed();
                        Toast.makeText(AddImmunizationList.this, "Berhasil edit jadwal imunisasi", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<PojoEditSchedule> call, Throwable t) {
                    Toast.makeText(AddImmunizationList.this, "Gagal edit jadwal imunisasi", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deleteImmunization() {
        dialog = new GeneralDoubleButtonDialog.GeneralDoubleDialogBuilder(AddImmunizationList.this)
                .setCaption("Apakah anda yakin ingin menghapus jadwal ini?")
                .setYesButton("Hapus")
                .setCancelButton("Batal")
                .setOnActionDialog(new GeneralDoubleButtonDialog.OnActionDialog() {
                    @Override
                    public void onYesClickListener() {
                        ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
                        Call<PojoEditSchedule> call = interfaces.deleteSchedule(idTemp);
                        call.enqueue(new Callback<PojoEditSchedule>() {
                            @Override
                            public void onResponse(Call<PojoEditSchedule> call, Response<PojoEditSchedule> response) {
                                onBackPressed();
                                finish();
                            }
                            @Override
                            public void onFailure(Call<PojoEditSchedule> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onCancelListener() {

                    }
                })
                .build();
    }
}
