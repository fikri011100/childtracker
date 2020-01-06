package com.titi.remotbayi;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.titi.remotbayi.home.HomeFragment;
import com.titi.remotbayi.profile.ProfileFragment;
import com.titi.remotbayi.utils.AppController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.frame_menu)
    FrameLayout frameLayout;
    @BindView(R.id.nav_bottom_menu)
    BottomNavigationView navBottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        navBottomMenu.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.home_menu:
                frameLayout.setVisibility(View.VISIBLE);
                fragment = new HomeFragment();
                break;
            case R.id.profile_menu:
                fragment = new ProfileFragment();
                frameLayout.setVisibility(View.VISIBLE);
                break;
        }
        loadFragment(fragment);
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.replace(R.id.frame_menu, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}
