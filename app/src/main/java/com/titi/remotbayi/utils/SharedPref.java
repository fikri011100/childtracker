package com.titi.remotbayi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.SharedPreferences.*;

public class SharedPref {
    private static final String ISLOGGEDIN = "isloggedin";

    private static SharedPreferences sharedPreferences;
    private static String TAG = SharedPref.class.getSimpleName();
    Editor editor;
    int PRIVATE_MODE = 0;
    Context _context;

    public SharedPref(Context context) {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(ISLOGGEDIN, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(ISLOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(ISLOGGEDIN, false);
    }
}
