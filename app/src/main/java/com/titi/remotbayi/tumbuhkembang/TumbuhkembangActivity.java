package com.titi.remotbayi.tumbuhkembang;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.titi.remotbayi.R;
import com.titi.remotbayi.eventbus.BabyNameBus;
import com.titi.remotbayi.model.ModelTumbuhkembang;
import com.titi.remotbayi.sqlite.SqliteHandler;
import com.titi.remotbayi.utils.StringHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbuhkembang);
        ButterKnife.bind(this);
        db = new SqliteHandler(this);
        loadSpinnerData();

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

    private void showChart() {
        List<DataEntry> data = new ArrayList<>();
        List<DataEntry> data1 = new ArrayList<>();
        List<DataEntry> data2 = new ArrayList<>();
        //chart 1
        chartBb = findViewById(R.id.chart_bb);
        APIlib.getInstance().setActiveAnyChartView(chartBb);
        Cartesian cartesian = AnyChart.column();
        for (int i = 0; i < datas.size(); i++) {
            data.add(new ValueDataEntry(StringHelper.formatDate(datas.get(i).getTgl() + " 00:00:00"), Float.parseFloat(datas.get(i).getBb())));
            Log.d("asdas", String.valueOf(Float.parseFloat(datas.get(i).getBb())));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Berat Badan (FW)");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        chartBb.setChart(cartesian);
        //chart 2
        chartTb = findViewById(R.id.chart_tb);
        APIlib.getInstance().setActiveAnyChartView(chartTb);
        Cartesian cartesian1 = AnyChart.column();
        for (int i = 0; i < datas.size(); i++) {
            data1.add(new ValueDataEntry(StringHelper.formatDate(datas.get(i).getTgl() + " 00:00:00"), Float.parseFloat(datas.get(i).getTb())));
            Log.d("asdas", String.valueOf(Float.parseFloat(datas.get(i).getBb())));
        }
        Column column1 = cartesian1.column(data1);

        column1.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian1.animation(true);
        cartesian1.title("Tinggi Badan (FW)");

        cartesian1.yScale().minimum(0d);

        cartesian1.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian1.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian1.interactivity().hoverMode(HoverMode.BY_X);

        chartTb.setChart(cartesian1);

        //chart 3
        chartSuhuTubuh = findViewById(R.id.chart_suhutubuh);
        APIlib.getInstance().setActiveAnyChartView(chartSuhuTubuh);
        Cartesian cartesian2 = AnyChart.column();
        for (int i = 0; i < datas.size(); i++) {
            data2.add(new ValueDataEntry(StringHelper.formatDate(datas.get(i).getTgl() + " 00:00:00"), Float.parseFloat(datas.get(i).getSuhu())));
        }
        Column column2 = cartesian2.column(data2);
        column2.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian2.animation(true);
        cartesian2.title("Suhu Tubuh (FW)");

        cartesian2.yScale().minimum(0d);

        cartesian2.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian2.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian2.interactivity().hoverMode(HoverMode.BY_X);

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
