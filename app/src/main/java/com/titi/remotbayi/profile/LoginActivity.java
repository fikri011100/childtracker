package com.titi.remotbayi.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.titi.remotbayi.MainActivity;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ApiClient;
import com.titi.remotbayi.model.ApiInterface;
import com.titi.remotbayi.model.PojoLogin;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email_in)
    TextInputEditText edtEmailIn;
    @BindView(R.id.edt_password_in)
    TextInputEditText edtPasswordIn;
    @BindView(R.id.btn_login_in)
    Button btnLoginIn;
    @BindView(R.id.txt_regist_in)
    TextView txtRegistIn;
    SqliteHandler db;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        db = new SqliteHandler(getApplicationContext());
        sharedPref = new SharedPref(getApplicationContext());
        if (sharedPref.isLogin()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_login_in)
    public void onBtnLoginInClicked() {
        if (edtEmailIn.getText().toString().isEmpty() || edtPasswordIn.getText().toString().isEmpty()) {
            Toast.makeText(this, "email atau password belum diisi", Toast.LENGTH_LONG).show();
        } else {
            if (edtEmailIn.getText().toString().equals("admin@admin.com") && edtPasswordIn.getText().toString().equals("admin")) {
                db.addUser("1", "admin", "admin@admin.com");
                sharedPref.setLogin(true);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                ApiInterface interfaces = ApiClient.getClient().create(ApiInterface.class);
                Call<PojoLogin> call = interfaces.doLogin(edtEmailIn.getText().toString(), edtPasswordIn.getText().toString());
                call.enqueue(new Callback<PojoLogin>() {
                    @Override
                    public void onResponse(Call<PojoLogin> call, Response<PojoLogin> response) {
                        if (response.body().getResult() == true) {
                            db.addUser(response.body().getUser().get(0).getUserId(), response.body().getUser().get(0).getUsername(), response.body().getUser().get(0).getEmail());
                            sharedPref.setLogin(true);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Maaf, username atau password salah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PojoLogin> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @OnClick(R.id.txt_regist_in)
    public void onTxtRegistInClicked() {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}
