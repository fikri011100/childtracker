package com.titi.remotbayi.tumbuhkembang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.RangeColumn;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipPositionMode;
import com.titi.remotbayi.R;
import com.titi.remotbayi.eventbus.BabyNameBus;
import com.titi.remotbayi.model.ModelChild;
import com.titi.remotbayi.model.ModelStandartTumbuhKembang;
import com.titi.remotbayi.model.ModelTumbuhkembang;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.StringHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TumbuhkembangActivity extends AppCompatActivity {

    @BindView(R.id.img_back_create)
    ImageView imgBackCreate;
    @BindView(R.id.button_add_tumbuhkembang)
    Button buttonAddTumbuhkembang;
    @BindView(R.id.cons_tumbuhkembang)
    ConstraintLayout consTumbuhkembang;
    @BindView(R.id.spinner_child)
    Spinner spinnerChild;
    @BindView(R.id.img_reload)
    ImageView imgReload;
    SqliteHandler db;
    AnyChartView chartBb, chartTb, chartSuhuTubuh;
    Cursor cursor;
    String babyName = "";
    protected List<ModelTumbuhkembang> datas = new ArrayList<>();
    protected List<ModelChild> data = new ArrayList<>();
    protected List<ModelStandartTumbuhKembang> dataStandart = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    public String url = "http://fikri.akudeveloper.com/getImmunization.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbuhkembang);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        loadSpinnerData();
        getResponse();

        spinnerChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                babyName = parent.getItemAtPosition(position).toString();
                EventBus.getDefault().post(new BabyNameBus.EventBus(babyName));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imgReload.setOnClickListener(view -> {
            datas.clear();
            EventBus.getDefault().post(new BabyNameBus.EventBus(babyName));
        });
    }

    private class CustomDataEntry extends DataEntry {
        public CustomDataEntry(String x, Number edinburgHigh, Number edinburgLow, Number londonHigh, Number londonLow) {
            setValue("x", x);
            setValue("edinburgHigh", edinburgHigh);
            setValue("edinburgLow", edinburgLow);
            setValue("londonHigh", londonHigh);
            setValue("londonLow", londonLow);
        }
    }

    private void showChart() {
        List<DataEntry> data = new ArrayList<>();
        List<DataEntry> data1 = new ArrayList<>();
        List<DataEntry> data2 = new ArrayList<>();
        //chart 1
        chartBb = findViewById(R.id.chart_bb);
        APIlib.getInstance().setActiveAnyChartView(chartBb);
        Cartesian cartesian = AnyChart.cartesian();

        for (int i = 0; i < datas.size(); i++) {
            data.add(new CustomDataEntry(StringHelper.formatDate(datas.get(i).getTgl() + " 00:00:00"), Float.parseFloat(datas.get(i).getBb()), 0, Float.parseFloat(dataStandart.get(i).getBb()), 0));
            Log.d("asdas", String.valueOf(Float.parseFloat(datas.get(i).getBb())));
        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping londonData = set.mapAs("{ x: 'x', high: 'londonHigh', low: 'londonLow' }");
        Mapping edinburgData = set.mapAs("{ x: 'x', high: 'edinburgHigh', low: 'edinburgLow' }");

        RangeColumn columnLondon = cartesian.rangeColumn(londonData);
        columnLondon.name("Bayi");

        RangeColumn columnEdinburg = cartesian.rangeColumn(edinburgData);
        columnEdinburg.name("Standar");

        cartesian.animation(true);
        cartesian.title("Berat Badan (FW)");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(true);
        cartesian.yAxis(true);

        cartesian.legend(true);

        cartesian.yGrid(true)
                .yMinorGrid(true);

        cartesian.tooltip().titleFormat("{%SeriesName} ({%x})");

        chartBb.setChart(cartesian);
        //chart 2
        chartTb = findViewById(R.id.chart_tb);
        APIlib.getInstance().setActiveAnyChartView(chartTb);
        Cartesian cartesian1 = AnyChart.cartesian();

        for (int i = 0; i < datas.size(); i++) {
            data1.add(new CustomDataEntry(StringHelper.formatDate(datas.get(i).getTgl() + " 00:00:00"), Float.parseFloat(datas.get(i).getTb()), 0, Float.parseFloat(dataStandart.get(i).getTb()), 0));
            Log.d("asdas", String.valueOf(Float.parseFloat(datas.get(i).getBb())));
        }
        Set set1 = Set.instantiate();
        set1.data(data1);
        Mapping londonData1 = set1.mapAs("{ x: 'x', high: 'londonHigh', low: 'londonLow' }");
        Mapping edinburgData1 = set1.mapAs("{ x: 'x', high: 'edinburgHigh', low: 'edinburgLow' }");

        RangeColumn columnLondon1 = cartesian1.rangeColumn(londonData1);
        columnLondon1.name("Bayi");

        RangeColumn columnEdinburg1 = cartesian1.rangeColumn(edinburgData1);
        columnEdinburg1.name("Standar");

        cartesian1.animation(true);
        cartesian1.title("Tinggi Badan (FW)");

        cartesian1.yScale().minimum(0d);

        cartesian1.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian1.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian1.interactivity().hoverMode(HoverMode.BY_X);
        cartesian1.xAxis(true);
        cartesian1.yAxis(true);

        cartesian1.legend(true);

        cartesian1.yGrid(true)
                .yMinorGrid(true);

        cartesian1.tooltip().titleFormat("{%SeriesName} ({%x})");

        chartTb.setChart(cartesian1);

        //chart 3
        chartSuhuTubuh = findViewById(R.id.chart_suhutubuh);
        APIlib.getInstance().setActiveAnyChartView(chartSuhuTubuh);
        Cartesian cartesian2 = AnyChart.column();
        for (int i = 0; i < datas.size(); i++) {
            data2.add(new CustomDataEntry(StringHelper.formatDate(datas.get(i).getTgl() + " 00:00:00"), Float.parseFloat(datas.get(i).getSuhu()),0,Float.parseFloat(dataStandart.get(i).getSt()),0));
        }
        Set set2 = Set.instantiate();
        set2.data(data2);
        Mapping londonData2 = set2.mapAs("{ x: 'x', high: 'londonHigh', low: 'londonLow' }");
        Mapping edinburgData2 = set2.mapAs("{ x: 'x', high: 'edinburgHigh', low: 'edinburgLow' }");

        RangeColumn columnLondon2 = cartesian2.rangeColumn(londonData2);
        columnLondon2.name("Bayi");

        RangeColumn columnEdinburg2 = cartesian2.rangeColumn(edinburgData2);
        columnEdinburg2.name("Standar");

        cartesian2.animation(true);
        cartesian2.title("Suhu Tubuh (FW)");

        cartesian2.yScale().minimum(0d);

        cartesian2.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian2.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian2.interactivity().hoverMode(HoverMode.BY_X);
        cartesian2.xAxis(true);
        cartesian2.yAxis(true);

        cartesian2.legend(true);

        cartesian2.yGrid(true)
                .yMinorGrid(true);

        cartesian2.tooltip().titleFormat("{%SeriesName} ({%x})");

        chartSuhuTubuh.setChart(cartesian2);
    }

    private void getDataSqlite(String babyName) {
        cursor = db.getTumbuhKembangData(babyName);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ModelTumbuhkembang model = new ModelTumbuhkembang();
                model.setId(cursor.getString(0));
                model.setBaby(cursor.getString(1));
                model.setTgl(cursor.getString(2));
                model.setBb(cursor.getString(3));
                model.setTb(cursor.getString(4));
                model.setSuhu(cursor.getString(5));
                datas.add(model);
            }
        }
        cursor.close();
        db.close();
    }

    @Subscribe
    public void getBabyName(BabyNameBus.EventBus eventBus) {
        babyName = eventBus.getName();
        getDataSqlite(babyName);
        showChart();
    }

    private void loadSpinnerData() {
        List<String> lables = db.getAllBaby();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lables);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChild.setAdapter(dataAdapter);
        db.close();
    }

    private void getResponse() {
        final ProgressDialog progres = new ProgressDialog(this);
        progres.setTitle("Mohon tunggu sebentar");
        progres.setMessage("Sedang Ambil Data");
        progres.show();
        requestQueue = Volley.newRequestQueue(TumbuhkembangActivity.this);
        stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progres.dismiss();
                Log.d("response ", response);
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
                        map.put("bb_standart", json.getString("bb_standart"));
                        map.put("tb_standart", json.getString("tb_standart"));
                        map.put("st_standart", json.getString("st_standart"));
                        ModelStandartTumbuhKembang model = new ModelStandartTumbuhKembang();
                        model.setBb(json.getString("bb_standart"));
                        model.setTb(json.getString("tb_standart"));
                        model.setSt(json.getString("st_standart"));
                        dataStandart.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, error -> {
        });
        requestQueue.add(stringRequest);
    }

    @OnClick(R.id.img_back_create)
    public void onImgBackCreateClicked() {
        onBackPressed();
        finish();
    }

    @OnClick(R.id.button_add_tumbuhkembang)
    public void onButtonAddTumbuhkembangClicked() {
        Intent i = new Intent(getApplicationContext(), AddTumbuhKembangActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
