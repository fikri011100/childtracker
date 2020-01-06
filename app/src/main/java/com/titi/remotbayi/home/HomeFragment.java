package com.titi.remotbayi.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.titi.remotbayi.R;
import com.titi.remotbayi.about.AboutActivity;
import com.titi.remotbayi.imunisasi.ImunisasiActivity;
import com.titi.remotbayi.tumbuhkembang.TumbuhkembangActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.bg_tumbuhkembang)
    View bgTumbuhkembang;
    @BindView(R.id.bg_imunisasi)
    View bgImunisasi;
    @BindView(R.id.bg_about)
    View bgAbout;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        bgTumbuhkembang.setOnClickListener(view1 -> startActivity(new Intent(getContext(), TumbuhkembangActivity.class)));
        bgImunisasi.setOnClickListener(view1 -> startActivity(new Intent(getContext(), ImunisasiActivity.class)));
        bgAbout.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AboutActivity.class)));
    }
}
