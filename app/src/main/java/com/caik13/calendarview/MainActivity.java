package com.caik13.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caik13.scheduletableview.ScheduleTable;

public class MainActivity extends AppCompatActivity {

    private ScheduleTable scheduleTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        calendarView = findViewById(R.id.calendarview);
//
//        Map<String, DayData> map = new HashMap<>();
//
//        DayData dayData = new DayData();
//        dayData.setDate("2019-03-07");
//        dayData.setTimeType(DayData.TIME_TYPE_MORNING);
//        dayData.setScheme(true);
//        dayData.setSchmeText("专家预约");
//        dayData.setSchemeTextColor(Color.parseColor("#ffffff"));
//        dayData.setSchemeBgColor(Color.BLUE);
//        dayData.setSchemeTextSize(12);
//
//        map.put("2019-3-7", dayData);
//
//        calendarView.setMarkData(map);
//
//
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String, DayData> map = new HashMap<>();
//
//                DayData dayData = new DayData();
//                dayData.setDate("2019-3-8");
//                dayData.setTimeType(DayData.TIME_TYPE_NOON);
//                dayData.setScheme(true);
//                dayData.setSchmeText("专家预约");
//                dayData.setSchemeTextColor(Color.parseColor("#ffffff"));
//                dayData.setSchemeBgColor(Color.BLUE);
//                dayData.setSchemeTextSize(12);
//
//                map.put("2019-3-8", dayData);
//
//                calendarView.setMarkData(map);
//            }
//        });
    }
}
