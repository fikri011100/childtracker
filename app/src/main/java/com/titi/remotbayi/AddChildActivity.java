package com.titi.remotbayi;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.CalculateDateImmunization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class AddChildActivity extends AppCompatActivity {

    @BindView(R.id.edt_baby_name)
    TextInputEditText edtBabyName;
    @BindView(R.id.edt_anak_ke)
    TextInputEditText edtAnakKe;
    @BindView(R.id.edt_doctor_name)
    TextInputEditText edtDoctorName;
    @BindView(R.id.button_add_child)
    Button buttonAddChild;
    @BindView(R.id.edt_tgl_lahir)
    TextView edtTglLahir;
    @BindView(R.id.edt_kelamin_name)
    Spinner edtKelaminName;
    @BindView(R.id.edt_metode_lahir)
    Spinner edtMetodeLahir;
    @BindView(R.id.ly_kelamin_name)
    ConstraintLayout lyKelaminName;
    @BindView(R.id.ly_metode_lahir)
    ConstraintLayout lyMetodeLahir;
    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.switch_calendar)
    Switch switchCalendar;
    SqliteHandler db;
    int id;
    HashMap<String, String> user;
    HashMap<String, String> baby;
    Random r;
    String email, tglLahir, kelamin, metodeLahir, status, anakKe, tglLahirDet;
    private int mYear, mMonth, mDay;
    ArrayList<HashMap<String, String>> list_dataa;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private boolean suitch;
    public String url = "http://fikri.akudeveloper.com/getImmunization.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        user = db.getUserDetails();
        r = new Random();
        getResponse();
        checkPermission(42, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
        email = getIntent().getStringExtra("email");
        status = getIntent().getStringExtra("status");
        if (status.equals("edit")) {
            anakKe = getIntent().getStringExtra("anak_ke");
            baby = db.getBabyDetails(anakKe);
            edtBabyName.setText(baby.get("nama_bayi"));
            edtAnakKe.setText(baby.get("anak_ke"));
            edtDoctorName.setText(baby.get("nama_bidan"));
            edtTglLahir.setText(baby.get("tanggal_lahir_bayi"));
            buttonAddChild.setText("Ubah Data Anak");
        }
        id = r.nextInt(9999 - 1111 + 1) - 1111;
        edtTglLahir.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String day, dayDet;
                        String month = String.valueOf((monthOfYear + 1));
                        if (dayOfMonth <= 15) {
                            dayDet = "07";
                        } else {
                            dayDet = "23";
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = String.valueOf(dayOfMonth);
                        }

                        if ((monthOfYear + 1) < 10) {
                            month = "0" + (monthOfYear + 1);
                        }

                        edtTglLahir.setText(day + "-" + month + "-" + year);
                        tglLahir = year + "-" + month + "-" + day;
                        tglLahirDet = year + "-" + month + "-" + dayDet;
                        Log.d("tes", tglLahir);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        edtKelaminName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    kelamin = "Perempuan";
                } else {
                    kelamin = "Laki-laki";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtMetodeLahir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    metodeLahir = "Caesar";
                } else {
                    metodeLahir = "Normal";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        switchCalendar.setChecked(false);
        suitch = false;
        switchCalendar.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b == true) {
                suitch = true;
            } else  {
                suitch = false;
            }
        });
        buttonAddChild.setOnClickListener(view -> {
            if (status.equals("add")) {
                db.addBayi(
                        id,
                        edtBabyName.getText().toString().trim(),
                        Integer.parseInt(edtAnakKe.getText().toString().trim()),
                        "",
                        edtDoctorName.getText().toString().trim(),
                        kelamin,
                        email,
                        metodeLahir,
                        tglLahir
                );
            } else if (status.equals("edit")) {
                db.updateBayi(
                        Integer.parseInt(baby.get("id_bayi")),
                        edtBabyName.getText().toString().trim(),
                        Integer.parseInt(edtAnakKe.getText().toString().trim()),
                        "",
                        edtDoctorName.getText().toString().trim(),
                        kelamin,
                        email,
                        metodeLahir,
                        tglLahir
                );
            }
            if (suitch == true) {
                addCalendarChild();
            }
            onBackPressed();
            Toast.makeText(getApplicationContext(), "Berhasil Menambah Anak", Toast.LENGTH_SHORT).show();
        });
    }

    private void getResponse() {
        requestQueue = Volley.newRequestQueue(AddChildActivity.this);
        list_dataa = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("schedule_id", json.getString("schedule_id"));
                        map.put("schedule_title", json.getString("schedule_title"));
                        map.put("schedule_desc", json.getString("schedule_desc"));
                        map.put("schedule_time", json.getString("schedule_time"));
                        list_dataa.add(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void addCalendarChild() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final ProgressDialog progres = new ProgressDialog(this);
        progres.setTitle("Mohon tunggu sebentar");
        progres.setMessage("Sedang Mengirim data");
        progres.show();
        for (int i = 0; i < list_dataa.size(); i++) {
            try {
                Date startDate = sdf.parse(CalculateDateImmunization.startDate(Integer.parseInt(list_dataa.get(i).get("schedule_time")), tglLahirDet));
                Date endDate = sdf.parse(CalculateDateImmunization.endDate(Integer.parseInt(list_dataa.get(i).get("schedule_time")), tglLahirDet));
                ContentValues event = new ContentValues();
                event.put("calendar_id", 1);
                event.put("title", list_dataa.get(i).get("schedule_title"));
                event.put("description", list_dataa.get(i).get("schedule_desc"));
                event.put("eventLocation", "");
                event.put("eventTimezone", TimeZone.getDefault().getID());
                event.put("dtstart", startDate.getTime());
                event.put("dtend", endDate.getTime());

                event.put("allDay", 0); // 0 for false, 1 for true
                event.put("eventStatus", 1);
                event.put("hasAlarm", 1); // 0 for false, 1 for true

                String eventUriString = "content://com.android.calendar/events";
                Uri eventUri = getApplicationContext()
                        .getContentResolver()
                        .insert(Uri.parse(eventUriString), event);
                long eventID = Long.parseLong(eventUri.getLastPathSegment());

                // if reminder need to set


                int minutes = 120;


                // add reminder for the event
                ContentValues reminders = new ContentValues();
                reminders.put("event_id", eventID);
                reminders.put("method", "1");
                reminders.put("minutes", minutes);

                String reminderUriString = "content://com.android.calendar/reminders";
                getApplicationContext().getContentResolver()
                        .insert(Uri.parse(reminderUriString), reminders);
                Log.d("tes", "success adding calendar" + startDate.getTime() + list_dataa.get(i).get("schedule_title"));
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("date", e.getMessage());
            }
        }
        progres.dismiss();
    }

    @OnClick(R.id.img_back_create)
    public void onViewClicked() {
        onBackPressed();
        finish();
    }

    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }
}
