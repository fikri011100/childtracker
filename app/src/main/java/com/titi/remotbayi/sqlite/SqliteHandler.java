package com.titi.remotbayi.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.titi.remotbayi.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqliteHandler extends SQLiteOpenHelper {

    private static final String TAG = SqliteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "bayi";

    //USER
    public static final String TABLE_USER = "user";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    //BAYI
    public static final String TABLE_BAYI = "bayi";
    public static final String KEY_ID_BAYI = "id_bayi";
    public static final String KEY_NAMA_BAYI = "nama_bayi";
    public static final String KEY_ANAK_KE = "anak_ke";
    public static final String KEY_RS = "rs_bayi";
    public static final String KEY_NAMA_BIDAN = "nama_bidan";
    public static final String KEY_KELAMIN = "kelamin_bayi";
    public static final String KEY_USER = "user_bayi";
    public static final String KEY_METODE_LAHIR = "metode_lahir_bayi";
    public static final String KEY_TGL_LAHIR = "tanggal_lahir_bayi";

    //IMUNISASI BAYI
    public static final String TABLE_IMUNISASI = "imunisasi";
    public static final String KEY_ID_IMUNISASI = "id_imunisasi";
    public static final String KEY_TGL_IMUNISASI = "tgl_imunisasi";
    public static final String KEY_TYPE_IMUNISASI = "type_imunisasi";
    public static final String KEY_PRICE_IMUNISASI = "price_imunisasi";
    public static final String KEY_LOCATION_IMUNISASI = "location_imunisasi";
    public static final String KEY_IMAGE_IMUNISASI = "image_imunisasi";

    //TUMBUH KEMBANG
    public static final String TABLE_TUMBUHKEMBANG = "tumbuhkembang";
    public static final String KEY_ID_TUMBUHKEMBANG = "id_tumbuhkembang";
    public static final String KEY_BABY_TUMBUHKEMBANG = "baby_tumbuhkembang";
    public static final String KEY_TGL_TUMBUHKEMBANG = "tgl_tumbuhkembang";
    public static final String KEY_BB_TUMBUHKEMBANG = "bb_tumbuhkembang";
    public static final String KEY_TB_TUMBUHKEMBANG = "tb_tumbuhkembang";
    public static final String KEY_SUHU_TUBUH_TUMBUHKEMBANG = "suhutubuh_tumbuhkembang";

    //STANDART CHART IMUNISASI
    public static final String TABLE_STANDARTCHART = "standartchart";
    public static final String KEY_ID_STANDARTCHART = "id_standartchart";
    public static final String KEY_TGL_STANDARTCHART = "tgl_standartchart";
    public static final String KEY_BB_STANDARTCHART = "bb_standartchart";
    public static final String KEY_TB_STANDARTCHART = "tb_standartchart";
    public static final String KEY_ST_STANDARTCHART = "st_standartchart";

    public SqliteHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY ,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE"
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_BAYI_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BAYI + "("
                + KEY_ID_BAYI + " INTEGER PRIMARY KEY ,"
                + KEY_NAMA_BAYI + " TEXT,"
                + KEY_ANAK_KE + " INTEGER, "
                + KEY_RS + " TEXT,"
                + KEY_NAMA_BIDAN + " TEXT,"
                + KEY_KELAMIN + " TEXT,"
                + KEY_USER + " TEXT,"
                + KEY_METODE_LAHIR + " TEXT,"
                + KEY_TGL_LAHIR + " TEXT"
                + ")";
        db.execSQL(CREATE_BAYI_TABLE);

        String CREATE_IMUNISASI_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IMUNISASI + "("
                + KEY_ID_IMUNISASI + " INTEGER PRIMARY KEY ,"
                + KEY_TGL_IMUNISASI + " TEXT,"
                + KEY_TYPE_IMUNISASI + " TEXT,"
                + KEY_PRICE_IMUNISASI + " TEXT,"
                + KEY_LOCATION_IMUNISASI + " TEXT,"
                + KEY_IMAGE_IMUNISASI + " TEXT"
                + ")";
        db.execSQL(CREATE_IMUNISASI_TABLE);

        String CREATE_TUMBUHKEMBANG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TUMBUHKEMBANG + "("
                + KEY_ID_TUMBUHKEMBANG + " INTEGER PRIMARY KEY ,"
                + KEY_BABY_TUMBUHKEMBANG + " TEXT,"
                + KEY_TGL_TUMBUHKEMBANG + " TEXT,"
                + KEY_BB_TUMBUHKEMBANG + " TEXT,"
                + KEY_TB_TUMBUHKEMBANG + " TEXT,"
                + KEY_SUHU_TUBUH_TUMBUHKEMBANG + " TEXT"
                + ")";
        db.execSQL(CREATE_TUMBUHKEMBANG_TABLE);

        String CREATE_STANDARTCHART_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STANDARTCHART + "("
                + KEY_ID_STANDARTCHART + " INTEGER PRIMARY KEY ,"
                + KEY_TGL_STANDARTCHART + " TEXT,"
                + KEY_BB_STANDARTCHART + " TEXT,"
                + KEY_TB_STANDARTCHART + " TEXT,"
                + KEY_ST_STANDARTCHART + " TEXT"
                + ")";
        db.execSQL(CREATE_STANDARTCHART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAYI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMUNISASI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUMBUHKEMBANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STANDARTCHART);
        onCreate(db);
    }

    public void addStandartChart(String id_s, String tgl, String bb, String tb, String st) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_STANDARTCHART, id_s);
        values.put(KEY_TGL_STANDARTCHART, tgl);
        values.put(KEY_BB_STANDARTCHART, bb);
        values.put(KEY_TB_STANDARTCHART, tb);
        values.put(KEY_ST_STANDARTCHART, st);

        long id = db.insert(TABLE_STANDARTCHART, null, values);
        db.close();
        Log.d("tes", "New standart chart inserted into sqlite: " + id);
    }

    public HashMap<String, String> getStandartChart() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_STANDARTCHART;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("tgl", cursor.getString(1));
            user.put("bb", cursor.getString(2));
            user.put("tb", cursor.getString(2));
            user.put("st", cursor.getString(2));
        }
        cursor.close();
        db.close();

        return user;
    }

    public void addUser(String id_u,String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id_u);
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);

        long id = db.insert(TABLE_USER, null, values);
        db.close();
        Log.d("tes", "New user inserted into sqlite: " + id);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
        }
        cursor.close();
        db.close();

        return user;
    }

    public void addImmunization(String id_u,String tgl, String type, String price, String location, String image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_IMUNISASI, id_u);
        values.put(KEY_TGL_IMUNISASI, tgl);
        values.put(KEY_TYPE_IMUNISASI, type);
        values.put(KEY_PRICE_IMUNISASI, price);
        values.put(KEY_LOCATION_IMUNISASI, location);
        values.put(KEY_IMAGE_IMUNISASI, image);

        long id = db.insert(TABLE_IMUNISASI, null, values);
        db.close();
        Log.d("immunization", "New Immunization data has been inserted into sqlite: " + id);
    }

    public void addBayi(int id_b, String name, int anakKe, String rs, String namaBidan, String kelamin, String userId, String metodeLahir, String tglLahir) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_BAYI, id_b);
        values.put(KEY_NAMA_BAYI, name);
        values.put(KEY_ANAK_KE, anakKe);
        values.put(KEY_RS, rs);
        values.put(KEY_NAMA_BIDAN, namaBidan);
        values.put(KEY_KELAMIN, kelamin);
        values.put(KEY_USER, userId);
        values.put(KEY_METODE_LAHIR, metodeLahir);
        values.put(KEY_TGL_LAHIR, tglLahir);

        long id = db.insert(TABLE_BAYI, null, values);
        db.close();
        Log.d("user", "New bayi inserted into sqlite: " + id);
    }

    public HashMap<String, String> getBabyDetails(String users) {
        HashMap<String, String> baby = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_BAYI + " WHERE " + KEY_ANAK_KE + " = '" + users + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            baby.put("id_bayi", cursor.getString(0));
            baby.put("nama_bayi", cursor.getString(1));
            baby.put("anak_ke", cursor.getString(2));
            baby.put("rs_bayi", cursor.getString(3));
            baby.put("nama_bidan", cursor.getString(4));
            baby.put("kelamin_bayi", cursor.getString(5));
            baby.put("user_bayi", cursor.getString(6));
            baby.put("metode_lahir_bayi", cursor.getString(7));
            baby.put("tanggal_lahir_bayi", cursor.getString(8));
        }
        cursor.close();
        db.close();

        cursor.moveToFirst();
        cursor.close();
        db.close();

        return baby;
    }

    public void updateBayi(int id_b, String name, int anakKe, String rs, String namaBidan, String kelamin, String userId, String metodeLahir, String tglLahir) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_BAYI, id_b);
        values.put(KEY_NAMA_BAYI, name);
        values.put(KEY_ANAK_KE, anakKe);
        values.put(KEY_RS, rs);
        values.put(KEY_NAMA_BIDAN, namaBidan);
        values.put(KEY_KELAMIN, kelamin);
        values.put(KEY_USER, userId);
        values.put(KEY_METODE_LAHIR, metodeLahir);
        values.put(KEY_TGL_LAHIR, tglLahir);

        db.update(TABLE_BAYI, values, KEY_ID_BAYI + "=\"" + id_b + "\"", null);
        db.close();
    }

    public Cursor getBaby() {
        String selectQuery = Utils
                .dbGetdata(TABLE_BAYI, null, null, new String[]{KEY_ANAK_KE, "ASC"});
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public List<String> getAllBaby(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BAYI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d("user","Deleted all user info from sqlite");
    }

    public void deleteAllBaby() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BAYI, null, null);
        db.close();

        Log.d("baby","Deleted all baby info from sqlite");
    }

    public void deleteImunisasi() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMUNISASI, null, null);
        db.close();

        Log.d("imunisasi","Deleted all imunisasi info from sqlite");
    }

    public void deleteTumbuhKembang() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TUMBUHKEMBANG, null, null);
        db.close();

        Log.d("user","Deleted all user info from sqlite");
    }

    public void addTumbuhKembangData(String id_u,String baby, String bb, String tb, String suhubadan, String tgl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TUMBUHKEMBANG, id_u);
        values.put(KEY_BABY_TUMBUHKEMBANG, baby);
        values.put(KEY_TGL_TUMBUHKEMBANG, tgl);
        values.put(KEY_BB_TUMBUHKEMBANG, bb);
        values.put(KEY_TB_TUMBUHKEMBANG, tb);
        values.put(KEY_SUHU_TUBUH_TUMBUHKEMBANG, suhubadan);

        long id = db.insert(TABLE_TUMBUHKEMBANG, null, values);
        db.close();
        Log.d("tumbuh kembang", "New data tumbuh kembang inserted into sqlite: " + id);
    }

    public Cursor getTumbuhKembangData(String baby) {
        String selectQuery = "SELECT  * FROM " + TABLE_TUMBUHKEMBANG + " WHERE " + KEY_BABY_TUMBUHKEMBANG + " = '" + baby + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("tumbuhkembang", selectQuery);
        return cursor;
    }
}
