package com.titi.remotbayi.imunisasi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.titi.remotbayi.R;
import com.titi.remotbayi.immunization.AddImunisasiActivity;
import com.titi.remotbayi.utils.CalculateDateImmunization;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

class AdapterImunisasi extends RecyclerView.Adapter<AdapterImunisasi.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> list_dataa;
    String name, rsName, tglLahir;

    public AdapterImunisasi(Context context, ArrayList<HashMap<String, String>> list_dataa, String rsName, String tglLahir, String name) {
        this.context = context;
        this.list_dataa = list_dataa;
        this.name = name;
        this.rsName = rsName;
        this.tglLahir = tglLahir;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imunisasi, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTitle.setText(list_dataa.get(position).get("schedule_title"));
        holder.textTime.setText(CalculateDateImmunization.formatDate(Integer.parseInt(list_dataa.get(position).get("schedule_time"))));
        holder.viewCatatImunisasi.setOnClickListener(v -> {
            Intent i = new Intent(context, AddImunisasiActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("title", list_dataa.get(position).get("schedule_title"));
            i.putExtra("status", "add");
            i.putExtra("desc", list_dataa.get(position).get("schedule_desc"));
            i.putExtra("rsname", rsName);
            context.startActivity(i);
        });
        holder.bgMain.setOnClickListener(view -> {
            if (name.equals("admin")) {
                Intent i = new Intent(context, AddImmunizationList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", list_dataa.get(position).get("schedule_id"));
                i.putExtra("title", list_dataa.get(position).get("schedule_title"));
                i.putExtra("desc", list_dataa.get(position).get("schedule_desc"));
                i.putExtra("time", list_dataa.get(position).get("schedule_time"));
                i.putExtra("status", "edit");
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, DetailImmunization.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("title", list_dataa.get(position).get("schedule_title"));
                i.putExtra("desc", list_dataa.get(position).get("schedule_desc"));
                context.startActivity(i);
            }
        });

//        holder.viewRemindMe.setOnClickListener(view -> {
//            if (!name.equals("admin")) {
//                if (!rsName.equals("")) {
//                    holder.addCalendar(
//                            list_dataa.get(position).get("schedule_title"),
//                            list_dataa.get(position).get("schedule_desc"),
//                            rsName,
//                            CalculateDateImmunization.startDate(Integer.parseInt(list_dataa.get(position).get("schedule_time")), tglLahir),
//                            CalculateDateImmunization.endDate(Integer.parseInt(list_dataa.get(position).get("schedule_time")), tglLahir)
//                    );
//                    Log.d("startDate", CalculateDateImmunization.startDate(Integer.parseInt(list_dataa.get(position).get("schedule_time")), tglLahir));
//                    Log.d("endDate", CalculateDateImmunization.endDate(Integer.parseInt(list_dataa.get(position).get("schedule_time")), tglLahir));
//                    Toast.makeText(context, "Jadwal Sudah Ditambah, silahkan cek di Google Calendar", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(context, "Maaf, data anak belum di penuhiii", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(context, "Maaf, data anak belum di penuhi", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list_dataa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_time)
        TextView textTime;
        @BindView(R.id.view_catat_imunisasi)
        ConstraintLayout viewCatatImunisasi;
        @BindView(R.id.view_remind_me)
        ConstraintLayout viewRemindMe;
        @BindView(R.id.bg_main)
        ConstraintLayout bgMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        private void addCalendar(String title, String description, String eventLocation, String dateStart, String dateEnd) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date startDate = sdf.parse(dateStart);
                Date endDate = sdf.parse(dateEnd);
                ContentValues event = new ContentValues();
                event.put("calendar_id", 1);
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
                Uri eventUri = context
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
                context.getContentResolver()
                        .insert(Uri.parse(reminderUriString), reminders);
                Log.d("tes", String.valueOf(startDate.getTime()) + title);
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("date", e.getMessage());
            }
        }
    }
}
