package com.titi.remotbayi.tumbuhkembang;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ModelChild;
import com.titi.remotbayi.sqlite.SqliteHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTumbuhKembangActivity extends AppCompatActivity {

    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.edt_child)
    Spinner edtChild;
    @BindView(R.id.ly_child)
    ConstraintLayout lyChild;
    @BindView(R.id.txt_tgl_tumbuhkembang)
    TextView txtTglTumbuhkembang;
    @BindView(R.id.ly_tgl_tumbuhkembang)
    ConstraintLayout lyTglTumbuhkembang;
    @BindView(R.id.edt_bb)
    TextInputEditText edtBb;
    @BindView(R.id.edt_tb)
    TextInputEditText edtTb;
    @BindView(R.id.edt_temp)
    TextInputEditText edtTemp;
    @BindView(R.id.button_add_tumbuhkembang)
    Button buttonAddTumbuhkembang;
    SqliteHandler db;
    Random r;
    String id, tglTumbuhKembang;
    private String babyName = "";
    private int mYear, mMonth, mDay;
    protected List<ModelChild> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tumbuh_kembang);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        r = new Random();
        id = String.valueOf(r.nextInt(9999 - 1111 + 1) - 1111);
        loadSpinnerData();
        edtChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                babyName = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddTumbuhKembangActivity.this, babyName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtTglTumbuhkembang.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {

                        txtTglTumbuhkembang.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tglTumbuhKembang = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }

    private void loadSpinnerData() {
        List<String> lables = db.getAllBaby();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lables);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtChild.setAdapter(dataAdapter);
    }

    @OnClick(R.id.img_back_create)
    public void onImgBackCreateClicked() {
        onBackPressed();
        finish();
    }

    @OnClick(R.id.button_add_tumbuhkembang)
    public void onButtonAddTumbuhkembangClicked() {
        db.addTumbuhKembangData(
                id,
                babyName,
                edtBb.getText().toString().trim(),
                edtTb.getText().toString().trim(),
                edtTemp.getText().toString().trim(),
                tglTumbuhKembang
        );
        Toast.makeText(this, "Berhasil Menambah Data Tumbuh Kembang", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }
}
