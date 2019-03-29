/*
 * Copyright (C) 2016 huanghaibin_dev <huanghaibin_dev@163.com>
 * WebSite https://github.com/MiracleTimes-Dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.caik13.scheduletableview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthView extends View {

    /**
     * 日历的行数
     */
    protected int mLineCount;
    private List<DayData> mItems;

    private int dayWith = getScreenWidth(getContext()) / 7;

    private int dayOfWeek;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initData();
    }

    private void initData() {
        mItems = new ArrayList<>();
        java.util.Calendar date = java.util.Calendar.getInstance();
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1);

        dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;

        int mPreDiff = Utils.getMonthViewStartDiff(year, month, ScheduleTableConfigDelegate.WEEK_START_WITH_MON); // 月开始偏移量
        int monthDayCount = Utils.getMonthDaysCount(year, month);//获取月份真实天数

        for (int i = 0; i < mLineCount * 7; i++) {
//            int day = date.get(Calendar.DAY_OF_MONTH);

            DayData dayData = new DayData();
            if(i < mPreDiff) {
                // 月开始偏移量
                dayData.setPreMonth(true);
            } else if(i > (mPreDiff + monthDayCount)) {
                // 月结束偏移量
                dayData.setNextMonth(true);
            } else {
                // 本月内容
                dayData.setYear(year);
                dayData.setMonth(month);
                int day = i - mPreDiff + 1;
                dayData.setDay(day);
                dayData.setDate(year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day));
            }
            mItems.add(dayData);
            date.add(Calendar.DAY_OF_MONTH, 1);
        }

    }
    Paint.FontMetrics fontMetrics;
    private void init() {
        java.util.Calendar date = java.util.Calendar.getInstance();
        mLineCount = Utils.getMonthViewStartDiff(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, ScheduleTableConfigDelegate.WEEK_START_WITH_MON)
                + Utils.getMonthEndDiff(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, ScheduleTableConfigDelegate.WEEK_START_WITH_MON)
                + Utils.getMonthDaysCount(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1);
        System.out.println(mLineCount);

        mLineCount = mLineCount / 7;

        paint.setTextSize(Utils.dipToPx(getContext(), 12));
        fontMetrics = paint.getFontMetrics();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(dayWith * mLineCount, MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(dayWith * 7, MeasureSpec.EXACTLY);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextAlign(Paint.Align.CENTER);
        int d = 0;
        for (int i = 0; i < mLineCount; i++) {
            for (int j = 0; j < 7; j++) {
                DayData dayData = mItems.get(d);
                if(dayData.isPreMonth()) {

                } else if(dayData.isNextMonth()) {

                } else {
                    canvas.drawText(String.valueOf(dayData.getDay()), j * dayWith + dayWith / 2, i * dayWith + dayWith / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, paint);
                }
                ++d;
            }
        }

    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
