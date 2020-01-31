package com.titi.remotbayi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.titi.remotbayi.sqlite.SqliteHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChildActivity extends AppCompatActivity {

    @BindView(R.id.edt_baby_name)
    TextInputEditText edtBabyName;
    @BindView(R.id.edt_anak_ke)
    TextInputEditText edtAnakKe;
    @BindView(R.id.edt_rs_name)
    TextInputEditText edtRsName;
    @BindView(R.id.edt_doctor_name)
    TextInputEditText edtDoctorName;
    @BindView(R.id.button_add_child)
    Button buttonAddChild;
    @BindView(R.id.edt_tgl_lahir)
    TextView edtTglLahir;
    @BindView(R.id.edt_kelamin_name)
    Spinner edtKelaminName;
    @BindView(R.id.edt_metode_lahir)
    Spinner edtMetodeLahir;
    @BindView(R.id.ly_kelamin_name)
    ConstraintLayout lyKelaminName;
    @BindView(R.id.ly_metode_lahir)
    ConstraintLayout lyMetodeLahir;
    SqliteHandler db;
    int id;
    HashMap<String, String> user;
    HashMap<String, String> baby;
    Random r;
    String email, tglLahir, kelamin, metodeLahir, status, anakKe;
    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        user = db.getUserDetails();
        r = new Random();

        email = getIntent().getStringExtra("email");
        status = getIntent().getStringExtra("status");
        if (status.equals("edit")) {
            anakKe = getIntent().getStringExtra("anak_ke");
            baby = db.getBabyDetails(anakKe);
            edtBabyName.setText(baby.get("nama_bayi"));
            edtAnakKe.setText(baby.get("anak_ke"));
            edtRsName.setText(baby.get("rs_bayi"));
            edtDoctorName.setText(baby.get("nama_bidan"));
            edtTglLahir.setText(baby.get("tanggal_lahir_bayi"));
            buttonAddChild.setText("Ubah Data Anak");
        }
        id = r.nextInt(9999 - 1111 + 1) - 1111;
        edtTglLahir.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String day = dayOfMonth + "";
                        String month = (monthOfYear + 1) + "";
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        }

                        if ((monthOfYear + 1) < 10) {
                            month = "0" + (monthOfYear + 1);
                        }

                        edtTglLahir.setText(day + "-" + month + "-" + year);
                        tglLahir = year + "-" + month + "-" + day;
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        edtKelaminName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    kelamin = "Perempuan";
                } else {
                    kelamin = "Laki-laki";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtMetodeLahir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    metodeLahir = "Caesar";
                } else {
                    metodeLahir = "Normal";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonAddChild.setOnClickListener(view -> {
            if (status.equals("add")) {
                db.addBayi(
                        id,
                        edtBabyName.getText().toString().trim(),
                        Integer.parseInt(edtAnakKe.getText().toString().trim()),
                        edtRsName.getText().toString().trim(),
                        edtDoctorName.getText().toString().trim(),
                        kelamin,
                        email,
                        metodeLahir,
                        tglLahir
                );
            } else if (status.equals("edit")) {
                db.updateBayi(
                        Integer.parseInt(baby.get("id_bayi")),
                        edtBabyName.getText().toString().trim(),
                        Integer.parseInt(edtAnakKe.getText().toString().trim()),
                        edtRsName.getText().toString().trim(),
                        edtDoctorName.getText().toString().trim(),
                        kelamin,
                        email,
                        metodeLahir,
                        tglLahir
                );
            }
            onBackPressed();
            Toast.makeText(getApplicationContext(), "Berhasil Menambah Anak", Toast.LENGTH_SHORT).show();
        });
    }

    @OnClick(R.id.img_back_create)
    public void onViewClicked() {
        onBackPressed();
        finish();
    }
}
