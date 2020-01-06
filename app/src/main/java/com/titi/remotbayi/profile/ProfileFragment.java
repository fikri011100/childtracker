package com.titi.remotbayi.profile;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.titi.remotbayi.AddChildActivity;
import com.titi.remotbayi.R;
import com.titi.remotbayi.model.ModelChild;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @BindView(R.id.username)
    TextView username;
    HashMap<String, String> user;
    SqliteHandler db;
    @BindView(R.id.rec_anak)
    RecyclerView recAnak;
    AdapterChild adapter;
    Cursor cursor;
    SlideInBottomAnimationAdapter animAdapter;
    protected List<ModelChild> data = new ArrayList<>();
    @BindView(R.id.button_add_child)
    Button buttonAddChild;
    @BindView(R.id.image_logout)
    ImageView imageLogout;
    SharedPref sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new SqliteHandler(getContext());
        user = db.getUserDetails();
        sessionManager = new SharedPref(getActivity());
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        username.setText("Hai " + user.get("name"));
        if (!user.get("name").equals("admin")) {
            setRec();
        } else {
            buttonAddChild.setVisibility(View.GONE);
        }
        imageLogout.setOnClickListener(v -> {
            logout();
            sessionManager.setLogin(false);

        });
    }

    private void logout() {
        db.close();
        db.deleteUsers();
        db.deleteAllBaby();
        db.deleteImunisasi();
        db.deleteTumbuhKembang();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    private void setRec() {
        recAnak.setItemAnimator(new SlideInDownAnimator());
        recAnak.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterChild(data, getContext());
        animAdapter = new SlideInBottomAnimationAdapter(adapter);
        animAdapter.setFirstOnly(true);
        animAdapter.setDuration(500);
        animAdapter.setInterpolator(new OvershootInterpolator(.5f));
        recAnak.setAdapter(animAdapter);
        getDataSQLite();
    }

    @Override
    public void onResume() {
        super.onResume();

        data.clear();
        getDataSQLite();
        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataSQLite() {
        cursor = db.getBaby();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ModelChild model = new ModelChild();
                model.setId(cursor.getString(0));
                model.setChildName(cursor.getString(1));
                model.setAnakKe(cursor.getString(2));
                model.setRSName(cursor.getString(3));
                model.setBidanName(cursor.getString(4));
                model.setKelaminChild(cursor.getString(5));
                model.setUserIdChild(cursor.getString(6));
                model.setMetodeLahir(cursor.getString(7));
                model.setTglLahir(cursor.getString(8));
                data.add(model);
            }
        }
        cursor.close();
        db.close();
    }

    @OnClick(R.id.button_add_child)
    public void onViewClicked() {
        Intent i = new Intent(getActivity(), AddChildActivity.class);
        i.putExtra("email", user.get("email"));
        i.putExtra("status", "add");
        startActivity(i);
    }
}
