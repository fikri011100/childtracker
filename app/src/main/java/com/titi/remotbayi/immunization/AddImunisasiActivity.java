package com.titi.remotbayi.immunization;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titi.remotbayi.R;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.CalculateDateImmunization;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

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
    String title, status, id, tglImunisasi, desc, rsname;
    private int mYear, mMonth, mDay;
    final int callbackId = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imunisasi);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        r = new Random();
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        desc = getIntent().getStringExtra("desc");
        rsname = getIntent().getStringExtra("rsname");
        id = String.valueOf(r.nextInt(9999 - 1111 + 1) - 1111);

        txtTglImunisasi.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {

                        txtTglImunisasi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        String month = String.valueOf(monthOfYear + 1);
                        String day = String.valueOf(dayOfMonth);
                        if ((monthOfYear + 1) < 10) {
                            month = "0" + (monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        }
                        tglImunisasi = year + "-" + month + "-" + day;
                        Log.d("startDate", tglImunisasi);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        checkPermission(callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
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
        addCalendar(
                title,
                desc,
                rsname,
                tglImunisasi + " 07:00:00",
                tglImunisasi + " 15:00:00"
        );
        Log.d("startDate", tglImunisasi + " || " + desc + " || " + title + " || " + rsname);
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

    private void addCalendar(String title, String description, String eventLocation, String dateStart, String dateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date startDate = sdf.parse(dateStart);
            Date endDate = sdf.parse(dateEnd);
            ContentValues event = new ContentValues();
            event.put("calendar_id", 3);
            event.put("title", title);
            event.put("description", description);
            event.put("eventLocation", eventLocation);
            event.put("eventTimezone", TimeZone.getDefault().getID());
            event.put("dtstart", startDate.getTime());
            event.put("dtend", endDate.getTime());

            event.put("allDay", 0); // 0 for false, 1 for true
            event.put("eventStatus", 1);
            event.put("hasAlarm", 1); // 0 for false, 1 for true

            String eventUriString = "content://com.android.calendar/events";
            Uri eventUri = getApplicationContext().getContentResolver()
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
            Log.d("finish", "success add calendar");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("deit", e.getMessage());
        }
    }
}
