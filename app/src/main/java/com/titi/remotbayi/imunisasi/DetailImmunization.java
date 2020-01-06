package com.titi.remotbayi.imunisasi;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.titi.remotbayi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailImmunization extends AppCompatActivity {

    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    String title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_immunization);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        txtTitle.setText(title);
        txtDesc.setText(desc);
    }

    @OnClick(R.id.img_back_create)
    public void onViewClicked() {
        onBackPressed();
        finish();
    }
}
