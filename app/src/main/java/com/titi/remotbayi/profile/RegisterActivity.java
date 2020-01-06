package com.titi.remotbayi.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.PojoRegister;
import com.titi.remotbayi.sqlite.SqliteHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edt_fullname_up)
    TextInputEditText edtFullnameUp;
    @BindView(R.id.edt_email_up)
    TextInputEditText edtEmailUp;
    @BindView(R.id.edt_password_up)
    TextInputEditText edtPasswordUp;
    @BindView(R.id.edt_password_confirm_up)
    TextInputEditText edtPasswordConfirmUp;
    @BindView(R.id.btn_regist_up)
    Button btnRegistUp;
    @BindView(R.id.txt_login_up)
    TextView txtLoginUp;
    SqliteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        db = new SqliteHandler(getApplicationContext());
    }

    @OnClick(R.id.btn_regist_up)
    public void onBtnRegistUpClicked() {
        if (edtPasswordUp.getText().toString().equals(edtPasswordConfirmUp.getText().toString())) {
            ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
            Call<PojoRegister> call = interfaces.doRegister(edtFullnameUp.getText().toString(), edtEmailUp.getText().toString(), edtPasswordConfirmUp.getText().toString());
            call.enqueue(new Callback<PojoRegister>() {
                @Override
                public void onResponse(Call<PojoRegister> call, Response<PojoRegister> response) {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(RegisterActivity.this, "Registrasi berhasil, silahkan login dulu.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<PojoRegister> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Maaf, Password dan Password Konfimasi tidak cocok", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txt_login_up)
    public void onTxtLoginUpClicked() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
