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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weiyugang 2019年3月6日
 */
public class ScheduleView extends View implements View.OnClickListener {
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime;


    private static final long ONE_DAY = 1000 * 3600 * 24;
    private String[] mWeekBarStr = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    private ScheduleTableConfigDelegate mDelegate;

    private int mDayCount;
    private List<DayData> mDayDates;
    private Map<String, List<DayData>> mItems;
    private float mWeekBarBaseline;// weekbar text baseline
    private float mWeekBarTextOffset;

    public ScheduleView(Context context) {
        this(context, null);
    }

    public ScheduleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(ScheduleTableConfigDelegate mDelegate) {
        this.mDelegate = mDelegate;
        init();
    }

    private void init() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        mDayCount = mDelegate.getWeekCount() * 7 + (7 - (dayOfWeek - 1) + 1);
        initDate();
        initPaint();
        setOnClickListener(this);
    }

    private void initDate() {
        mDayDates = new ArrayList<>();
        mItems = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        long curDateMills = calendar.getTimeInMillis();//生成选择的日期时间戳

        for (int i = 0; i < mDayCount; i++) {
            calendar.setTimeInMillis(curDateMills + i * ONE_DAY);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            DayData weekDayDate = new DayData();
            weekDayDate.setDate(year + "-" + month + "-" + day);
            weekDayDate.setTime(month + "-" + day);
            weekDayDate.setDayOfWeek(dayOfWeek);

            mDayDates.add(weekDayDate);


            for (int j = 0; j < 3; j++) {
                DayData dayData = new DayData();
                dayData.setDate(year + "-" + (month >= 10 ? month : "0" + month) + "-" + (day >= 10 ? day : "0" + day));
                dayData.setTime((month >= 10 ? month : "0" + month) + "-" + (day >= 10 ? day : "0" + day));
                dayData.setDayOfWeek(dayOfWeek);
                dayData.setTimeType(j);
                List<DayData> list = mItems.get(mDayDates.get(i).getDate());
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(dayData);
                mItems.put(mDayDates.get(i).getDate(), list);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mDelegate.getWeekBarItemHeight() * 4, MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((mDelegate.getWeekBarItemWith()) * mDayCount, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private TextPaint mWeekBarTextPaint;
    private Paint mWeekBarTextPaint2;

    private Paint mWeekBarBorderPaint;
    private Paint mWeekBarBgPaint;

    private Paint mDayBgPint;
    private Paint mDayBorderPaint;
    private Paint mDaySchmeTextPaint;
    private Paint mDaySchmeBgPaint;


    private void initPaint() {
        mWeekBarTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mWeekBarTextPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

        mWeekBarBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWeekBarBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDayBgPint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDayBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDaySchmeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDaySchmeBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDayBgPint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mWeekBarTextPaint.setTextSize(mDelegate.getWeekBarTextSize());
        mWeekBarTextPaint.setColor(mDelegate.getWeekBarTextColor());
        mWeekBarTextPaint.setTextAlign(Paint.Align.CENTER);
        mWeekBarTextPaint2.setTextSize(mDelegate.getWeekBarTextSize());
        mWeekBarTextPaint2.setColor(mDelegate.getWeekBarTextColor());
        mWeekBarTextPaint2.setTextAlign(Paint.Align.CENTER);

        mWeekBarBgPaint.setStyle(Paint.Style.FILL);
        mWeekBarBgPaint.setColor(mDelegate.getWeekBarBgColor());

        mWeekBarBorderPaint.setStyle(Paint.Style.STROKE);
        mWeekBarBorderPaint.setStrokeWidth(mDelegate.getWeekBarBoderWith());
        mWeekBarBorderPaint.setColor(mDelegate.getWeekBarBoderColor());

        mDayBorderPaint.setStyle(Paint.Style.STROKE);
        mDayBorderPaint.setStrokeWidth(mDelegate.getWeekDayBoderWith());
        mDayBorderPaint.setColor(mDelegate.getWeekDayBoderColor());

        mDayBgPint.setColor(mDelegate.getWeekDayBgColor());
        mDayBgPint.setStyle(Paint.Style.FILL);


        // weekbar
        Paint.FontMetrics mWeekBarText2FontMetrics = mWeekBarTextPaint2.getFontMetrics();
        // weekbar text baseline
        mWeekBarBaseline = (mDelegate.getWeekBarItemHeight() / 2) + (mWeekBarText2FontMetrics.bottom - mWeekBarText2FontMetrics.top) / 2 - mWeekBarText2FontMetrics.bottom;
        // weekbar文字高度
        mWeekBarTextOffset = (mWeekBarText2FontMetrics.descent - mWeekBarText2FontMetrics.ascent) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw weekbar
//            canvas.clipRect();

        // 画背景
        canvas.drawRect(0,
                0,
                (mDayCount + 1) * mDelegate.getWeekBarItemWith(),
                mDelegate.getWeekBarItemHeight(),
                mWeekBarBgPaint);

        canvas.drawLine(0,
                0,
                (mDelegate.getWeekBarItemWith()) * mDayCount,
                0, mWeekBarBorderPaint);

        canvas.drawLine(
                0,
                mDelegate.getWeekBarItemHeight(),
                (mDelegate.getWeekBarItemWith()) * mDayCount,
                mDelegate.getWeekBarItemHeight(),
                mWeekBarBorderPaint);

        // region 画 weekbar
        for (int i = 0; i < mDayCount; i++) {
            // 画背景
//            canvas.drawRect(i * mDelegate.getWeekBarItemWith(),
//                    0,
//                    (i + 1) * mDelegate.getWeekBarItemWith(),
//                    mDelegate.getWeekBarItemHeight(),
//                    mWeekBarBgPaint);
            // 画边框
//            canvas.drawRect(i * mDelegate.getWeekBarItemWith(),
//                    0,
//                    (i + 1) * mDelegate.getWeekBarItemWith(),
//                    mDelegate.getWeekBarItemHeight(),
//                    mWeekBarBorderPaint);

            canvas.drawText(mItems.get(mDayDates.get(i).getDate()).get(0).getTime(),
                    i * mDelegate.getWeekBarItemWith() + mDelegate.getWeekBarItemWith() / 2,
                    mWeekBarBaseline - mWeekBarTextOffset, mWeekBarTextPaint);

            if (i == 0) {
                canvas.drawText("今日",
                        i * mDelegate.getWeekBarItemWith() + mDelegate.getWeekBarItemWith() / 2,
                        mWeekBarBaseline + mWeekBarTextOffset, mWeekBarTextPaint2);
            } else {
                canvas.drawText(mWeekBarStr[mItems.get(mDayDates.get(i).getDate()).get(0).getDayOfWeek() - 1],
                        i * mDelegate.getWeekBarItemWith() + mDelegate.getWeekBarItemWith() / 2,
                        mWeekBarBaseline + mWeekBarTextOffset, mWeekBarTextPaint2);
            }
        }
        // endregion

        // draw day
        // region 画day
        for (int j = 0; j < mDayCount; j++) {
            for (int m = 0; m < 3; m++) {
                // 画背景
                canvas.drawRect(j * mDelegate.getWeekBarItemWith(),
                        (m + 1) * mDelegate.getWeekBarItemHeight(),
                        (j + 1) * mDelegate.getWeekBarItemWith(),
                        (m + 2) * mDelegate.getWeekBarItemHeight(),
                        mDayBgPint);

                // 画边框
                canvas.drawRect(j * mDelegate.getWeekBarItemWith(),
                        (m + 1) * mDelegate.getWeekBarItemHeight(),
                        (j + 1) * mDelegate.getWeekBarItemWith(),
                        (m + 2) * mDelegate.getWeekBarItemHeight(),
                        mDayBorderPaint);

                // 画标记
                List<DayData> dayDataList = mItems.get(mDayDates.get(j).getDate());
                if (dayDataList != null) {
                    DayData dayData = dayDataList.get(m);

                    if (dayData.isScheme() && m == dayData.getTimeType()) {
                        mDaySchmeBgPaint.setStyle(Paint.Style.FILL);
                        mDaySchmeBgPaint.setColor(dayData.getSchemeBgColor());

                        mDaySchmeTextPaint.setTextSize(dipToPx(getContext(), dayData.getSchemeTextSize()));
                        mDaySchmeTextPaint.setColor(dayData.getSchemeTextColor());
                        mDaySchmeTextPaint.setTextAlign(Paint.Align.CENTER);

                        Paint.FontMetrics fontMetrics = mDaySchmeTextPaint.getFontMetrics();

                        // 画标记背景
                        RectF rectF = new RectF(j * mDelegate.getWeekBarItemWith() + dipToPx(getContext(), 10),
                                (m + 1) * mDelegate.getWeekBarItemHeight() + dipToPx(getContext(), 11),
                                (j + 1) * mDelegate.getWeekBarItemWith() - dipToPx(getContext(), 10),
                                (m + 2) * mDelegate.getWeekBarItemHeight() - dipToPx(getContext(), 11));
                        canvas.drawRoundRect(rectF, 14, 14, mDaySchmeBgPaint);

                        // 计算baseline
                        float baseline = (m * mDelegate.getWeekDayItemHeight() + mDelegate.getWeekBarItemHeight() + mDelegate.getWeekDayItemHeight() / 2) + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

                        // 画标记文字
                        canvas.drawText(dayData.getSchmeText(), j * mDelegate.getWeekDayItemWith() + mDelegate.getWeekDayItemWith() / 2,
                                baseline, mDaySchmeTextPaint);
                    }
                }
            }
        }
        // endregion
    }

    public void notifyMarkData() {
        Map<String, List<DayData>> marDatas = mDelegate.getMarkDatas();

        for (int i = 0; i < mDayDates.size(); i++) {
            if (marDatas.containsKey(mItems.get(mDayDates.get(i).getDate()).get(0).getDate())) {
                for (int j = 0; j < 3; j++) {

                    DayData dayData = mItems.get(mDayDates.get(i).getDate()).get(j);
                    DayData d = marDatas.get(dayData.getDate()).get(j);

                    if (d.getTimeType() == dayData.getTimeType()) {
                        dayData.setTimeType(d.getTimeType());
                        dayData.setSchemeBgColor(d.getSchemeBgColor());
                        dayData.setScheme(d.isScheme());
                        dayData.setSchemeTextColor(d.getSchemeTextColor());
                        dayData.setSchemeTextSize(d.getSchemeTextSize());
                        dayData.setSchmeText(d.getSchmeText());
                    } else {
                        dayData.setSchemeBgColor(0);
                        dayData.setScheme(false);
                        dayData.setSchemeTextColor(0);
                        dayData.setSchemeTextSize(0);
                        dayData.setSchmeText("");
                    }
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    DayData dayData = mItems.get(mDayDates.get(i).getDate()).get(j);
//                    DayData d = marDatas.get(dayData.getDate()).get(j);
                    dayData.setSchemeBgColor(0);
                    dayData.setScheme(false);
                    dayData.setSchemeTextColor(0);
                    dayData.setSchemeTextSize(0);
                    dayData.setSchmeText("");
                }
            }
        }
        invalidate();
    }

    private float mX, mY;
    private boolean isClick;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mDelegate.isClickAble()) {
            return false;
        }
        if (event.getPointerCount() > 1)
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float mDY;
                if (isClick) {
                    mDY = event.getY() - mY;
                    isClick = Math.abs(mDY) <= 50;
                }
                break;
            case MotionEvent.ACTION_UP:
                mX = event.getX();
                mY = event.getY();
                break;
        }
        return super.onTouchEvent(event);
    }

    private int mHorizontalScrollDistance;

    public void updateHorizontalScrollDistance(int h) {
        this.mHorizontalScrollDistance = h;
    }

    /**
     * 获取点击选中的日期
     *
     * @return return
     */
    protected DayData getIndex() {
        int indexX2 = (int) (mX / mDelegate.getWeekBarItemWith());

        List<DayData> dayDatas = mItems.get(mDayDates.get(indexX2).getDate());

        int indexY2 = (int) ((mY - mDelegate.getWeekBarItemHeight()) / mDelegate.getWeekDayItemHeight());
        return dayDatas.get(indexY2);
    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        if (!mDelegate.isClickAble()) {
            return;
        }
        if (!isClick) {
            return;
        }

        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = curClickTime;
            DayData dayData = getIndex();
            if (dayData == null) {
                return;
            }
            if (mDelegate.getOnCalendarInterceptListener() != null) {
                mDelegate.getOnCalendarInterceptListener().onCalendarInterceptClick(dayData, isClick);
            }
        }
    }
}
