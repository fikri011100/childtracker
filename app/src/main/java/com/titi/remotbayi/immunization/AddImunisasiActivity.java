package com.titi.remotbayi.immunization;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titi.remotbayi.R;
import com.titi.remotbayi.sqlite.SqliteHandler;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddImunisasiActivity extends AppCompatActivity {

    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.ly_tgl_imunisasi)
    ConstraintLayout lyTglImunisasi;
    @BindView(R.id.edt_harga)
    TextInputEditText edtHarga;
    @BindView(R.id.ly_harga)
    TextInputLayout lyHarga;
    @BindView(R.id.edt_lokasi)
    TextInputEditText edtLokasi;
    @BindView(R.id.ly_lokasi)
    TextInputLayout lyLokasi;
    @BindView(R.id.txt_addimage_capt)
    TextView txtAddimageCapt;
    @BindView(R.id.image_add)
    ImageView imageAdd;
    @BindView(R.id.ly_image)
    ConstraintLayout lyImage;
    @BindView(R.id.button_add_child)
    Button buttonAddChild;
    @BindView(R.id.txt_tgl_imunisasi)
    TextView txtTglImunisasi;
    SqliteHandler db;
    Random r;
    String title, status, id, tglImunisasi;
    private int mYear, mMonth, mDay;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imunisasi);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        r = new Random();
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        id = String.valueOf(r.nextInt(9999 - 1111 + 1) - 1111);
        txtTglImunisasi.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {

                        txtTglImunisasi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tglImunisasi = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }

    @OnClick(R.id.img_back_create)
    public void onImgBackCreateClicked() {
        onBackPressed();
        finish();
    }

    @OnClick(R.id.button_add_child)
    public void onButtonAddChildClicked() {
        db.addImmunization(
                id,
                tglImunisasi,
                title,
                edtHarga.getText().toString().trim(),
                edtLokasi.getText().toString().trim(),
                ""
                );
        onBackPressed();
        finish();
    }
}
