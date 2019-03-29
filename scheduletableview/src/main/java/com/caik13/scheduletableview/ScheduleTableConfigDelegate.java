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
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.List;
import java.util.Map;

/**
 * @author weiyugang 2019年3月6日
 */
public final class ScheduleTableConfigDelegate {
    public static final int WEEK_START_WITH_SUN = 1;
    public static final int WEEK_START_WITH_MON = 2;
    public static final int WEEK_START_WITH_SAT = 6;
    private boolean isStop;
    private boolean isClickAble;

    private int mWeekCount;

    private int mWeekBarBgColor;
    private int mWeekBarTextColor;
    private int mWeekBarTextSize;
    private int mWeekBarBoderWith;
    private int mWeekBarBoderColor;

    private int mWeekBarItemWith;
    private int mWeekBarItemHeight;

    private int mLeftBarItemWith;
    private int mLeftBarItemHeight;
    private int mLeftBarItemTitleHeight;

    private int mWeekDayItemWith;
    private int mWeekDayItemHeight;

    private int mLeftBarTextColor;
    private int mLeftBarTextSize;
    private int mLeftBarBoderWith;
    private int mLeftBarBoderColor;
    private int mLeftBarBgColor;

    private int mWeekDayBgColor;
    private int mWeekDayTextColor;
    private int mWeekDayTextSize;
    private int mWeekDayBoderWith;
    private int mWeekDayBoderColor;

    private int mSchemeBgColor;
    private int mSchemeTextColor;
    private int mSchemeTextSize;
    private int mSchemeBoderWith;
    private int mSchemeBoderColor;

    private Map<String, List<DayData>> mMarkDatas;

    public ScheduleTableConfigDelegate(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScheduleTable);
        mWeekCount = array.getInt(R.styleable.ScheduleTable_week_count, 1);
        mWeekBarBgColor = array.getColor(R.styleable.ScheduleTable_weekbar_background_color, Color.WHITE);
        mWeekBarTextColor = array.getColor(R.styleable.ScheduleTable_weekbar_text_color, Color.parseColor("#333333"));
        mWeekBarTextSize = array.getDimensionPixelSize(R.styleable.ScheduleTable_weekbar_text_size, Utils.dipToPx(context, 12));
        mWeekBarBoderWith = (int) array.getDimension(R.styleable.ScheduleTable_weekbar_border_with, 1);
        mWeekBarBoderColor = array.getColor(R.styleable.ScheduleTable_weekbar_border_color, Color.parseColor("#eeeeee"));
        mWeekBarItemWith = (int) array.getDimension(R.styleable.ScheduleTable_weekbar_item_with, dipToPx(context, 78));
        mWeekBarItemHeight = (int) array.getDimension(R.styleable.ScheduleTable_weekbar_item_height, dipToPx(context, 50));

        mLeftBarTextColor = array.getColor(R.styleable.ScheduleTable_leftbar_text_color, Color.parseColor("#333333"));
        mLeftBarTextSize = array.getDimensionPixelSize(R.styleable.ScheduleTable_leftbar_text_size, Utils.dipToPx(context, 12));
        mLeftBarBoderWith = (int) array.getDimension(R.styleable.ScheduleTable_leftbar_border_with, 1);
        mLeftBarBoderColor = array.getColor(R.styleable.ScheduleTable_leftbar_border_color, Color.parseColor("#00333333"));
        mLeftBarBgColor = array.getColor(R.styleable.ScheduleTable_leftbar_background_color, Color.parseColor("#eeeeee"));
        mLeftBarItemWith = (int) array.getDimension(R.styleable.ScheduleTable_leftbar_item_with, dipToPx(context, 34));
        mLeftBarItemTitleHeight = (int) array.getDimension(R.styleable.ScheduleTable_leftbar_item_with, dipToPx(context, 50));
        mLeftBarItemHeight = (int) array.getDimension(R.styleable.ScheduleTable_leftbar_item_with, dipToPx(context, 50));

        mWeekDayBgColor = array.getColor(R.styleable.ScheduleTable_weekday_background_color, Color.WHITE);
        mWeekDayTextColor = array.getColor(R.styleable.ScheduleTable_weekday_text_color, Color.parseColor("#ffffff"));
        mWeekDayTextSize = array.getDimensionPixelSize(R.styleable.ScheduleTable_weekday_text_size, dipToPx(context, 12));
        mWeekDayBoderWith = (int) array.getDimension(R.styleable.ScheduleTable_weekday_border_with, 1);
        mWeekDayBoderColor = array.getColor(R.styleable.ScheduleTable_weekday_border_color, Color.parseColor("#eeeeee"));
        mWeekDayItemHeight = (int) array.getDimension(R.styleable.ScheduleTable_weekday_item_height, dipToPx(context, 50));
        mWeekDayItemWith = (int) array.getDimension(R.styleable.ScheduleTable_weekday_item_with, dipToPx(context, 78));

        mSchemeBgColor = array.getColor(R.styleable.ScheduleTable_scheme_background_color, Color.BLUE);
        mSchemeTextColor = array.getColor(R.styleable.ScheduleTable_scheme_text_color, Color.parseColor("#ffffff"));
        mSchemeTextSize = array.getDimensionPixelSize(R.styleable.ScheduleTable_scheme_text_size, dipToPx(context, 12));
        mSchemeBoderWith = (int) array.getDimension(R.styleable.ScheduleTable_scheme_border_with, 1);
        mSchemeBoderColor = array.getColor(R.styleable.ScheduleTable_scheme_border_color, Color.parseColor("#eeeeee"));

        array.recycle();
    }

    // ===========================================================================================
    private OnCalendarInterceptListener mOnCalendarInterceptListener;

    public interface OnCalendarInterceptListener {
        void onCalendarInterceptClick(DayData dayData, boolean isClick);
    }


    public void setOnCalendarInterceptListener(OnCalendarInterceptListener onCalendarInterceptListener) {
        this.mOnCalendarInterceptListener = onCalendarInterceptListener;
        isClickAble = true;
    }

    // region setter getter

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isClickAble() {
        return isClickAble;
    }

    public void setClickAble(boolean clickAble) {
        isClickAble = clickAble;
    }

    public int getWeekCount() {
        return mWeekCount;
    }

    public void setWeekCount(int mWeekCount) {
        this.mWeekCount = mWeekCount;
    }

    public int getWeekBarBgColor() {
        return mWeekBarBgColor;
    }

    public void setWeekBarBgColor(int mWeekBarBgColor) {
        this.mWeekBarBgColor = mWeekBarBgColor;
    }

    public int getWeekBarTextColor() {
        return mWeekBarTextColor;
    }

    public void setWeekBarTextColor(int mWeekBarTextColor) {
        this.mWeekBarTextColor = mWeekBarTextColor;
    }

    public int getWeekBarTextSize() {
        return mWeekBarTextSize;
    }

    public void setWeekBarTextSize(int mWeekBarTextSize) {
        this.mWeekBarTextSize = mWeekBarTextSize;
    }

    public float getWeekBarBoderWith() {
        return mWeekBarBoderWith;
    }

    public void setWeekBarBoderWith(int mWeekBarBoderWith) {
        this.mWeekBarBoderWith = mWeekBarBoderWith;
    }

    public int getWeekBarBoderColor() {
        return mWeekBarBoderColor;
    }

    public void setWeekBarBoderColor(int mWeekBarBoderColor) {
        this.mWeekBarBoderColor = mWeekBarBoderColor;
    }

    public int getWeekBarItemWith() {
        return mWeekBarItemWith;
    }

    public void setWeekBarItemWith(int mWeekBarItemWith) {
        this.mWeekBarItemWith = mWeekBarItemWith;
    }

    public int getWeekBarItemHeight() {
        return mWeekBarItemHeight;
    }

    public void setWeekBarItemHeight(int mWeekBarItemHeight) {
        this.mWeekBarItemHeight = mWeekBarItemHeight;
    }

    public int getLeftBarItemWith() {
        return mLeftBarItemWith;
    }

    public void setLeftBarItemWith(int mLeftBarItemWith) {
        this.mLeftBarItemWith = mLeftBarItemWith;
    }

    public int getLeftBarItemHeight() {
        return mLeftBarItemHeight;
    }

    public void setLeftBarItemHeight(int mLeftBarItemHeight) {
        this.mLeftBarItemHeight = mLeftBarItemHeight;
    }

    public int getLeftBarItemTitleHeight() {
        return mLeftBarItemTitleHeight;
    }

    public void setLeftBarItemTitleHeight(int mLeftBarItemTitleHeight) {
        this.mLeftBarItemTitleHeight = mLeftBarItemTitleHeight;
    }

    public int getWeekDayItemWith() {
        return mWeekDayItemWith;
    }

    public void setWeekDayItemWith(int mWeekDayItemWith) {
        this.mWeekDayItemWith = mWeekDayItemWith;
    }

    public int getWeekDayItemHeight() {
        return mWeekDayItemHeight;
    }

    public void setWeekDayItemHeight(int mWeekDayItemHeight) {
        this.mWeekDayItemHeight = mWeekDayItemHeight;
    }

    public int getLeftBarTextColor() {
        return mLeftBarTextColor;
    }

    public void setLeftBarTextColor(int mLeftBarTextColor) {
        this.mLeftBarTextColor = mLeftBarTextColor;
    }

    public int getLeftBarTextSize() {
        return mLeftBarTextSize;
    }

    public void setLeftBarTextSize(int mLeftBarTextSize) {
        this.mLeftBarTextSize = mLeftBarTextSize;
    }

    public int getLeftBarBoderWith() {
        return mLeftBarBoderWith;
    }

    public void setLeftBarBoderWith(int mLeftBarBoderWith) {
        this.mLeftBarBoderWith = mLeftBarBoderWith;
    }

    public int getLeftBarBoderColor() {
        return mLeftBarBoderColor;
    }

    public void setLeftBarBoderColor(int mLeftBarBoderColor) {
        this.mLeftBarBoderColor = mLeftBarBoderColor;
    }

    public int getLeftBarBgColor() {
        return mLeftBarBgColor;
    }

    public void setLeftBarBgColor(int mLeftBarBgColor) {
        this.mLeftBarBgColor = mLeftBarBgColor;
    }

    public int getWeekDayBgColor() {
        return mWeekDayBgColor;
    }

    public void setWeekDayBgColor(int mWeekDayBgColor) {
        this.mWeekDayBgColor = mWeekDayBgColor;
    }

    public int getWeekDayTextColor() {
        return mWeekDayTextColor;
    }

    public void setWeekDayTextColor(int mWeekDayTextColor) {
        this.mWeekDayTextColor = mWeekDayTextColor;
    }

    public int getWeekDayTextSize() {
        return mWeekDayTextSize;
    }

    public void setWeekDayTextSize(int mWeekDayTextSize) {
        this.mWeekDayTextSize = mWeekDayTextSize;
    }

    public int getWeekDayBoderWith() {
        return mWeekDayBoderWith;
    }

    public void setWeekDayBoderWith(int mWeekDayBoderWith) {
        this.mWeekDayBoderWith = mWeekDayBoderWith;
    }

    public int getWeekDayBoderColor() {
        return mWeekDayBoderColor;
    }

    public void setWeekDayBoderColor(int mWeekDayBoderColor) {
        this.mWeekDayBoderColor = mWeekDayBoderColor;
    }

    public int getSchemeBgColor() {
        return mSchemeBgColor;
    }

    public void setSchemeBgColor(int mSchemeBgColor) {
        this.mSchemeBgColor = mSchemeBgColor;
    }

    public int getSchemeTextColor() {
        return mSchemeTextColor;
    }

    public void setSchemeTextColor(int mSchemeTextColor) {
        this.mSchemeTextColor = mSchemeTextColor;
    }

    public int getSchemeTextSize() {
        return mSchemeTextSize;
    }

    public void setSchemeTextSize(int mSchemeTextSize) {
        this.mSchemeTextSize = mSchemeTextSize;
    }

    public int getSchemeBoderWith() {
        return mSchemeBoderWith;
    }

    public void setSchemeBoderWith(int mSchemeBoderWith) {
        this.mSchemeBoderWith = mSchemeBoderWith;
    }

    public int getSchemeBoderColor() {
        return mSchemeBoderColor;
    }

    public void setSchemeBoderColor(int mSchemeBoderColor) {
        this.mSchemeBoderColor = mSchemeBoderColor;
    }

    public Map<String, List<DayData>> getMarkDatas() {
        return mMarkDatas;
    }

    public void setMarkData(Map<String, List<DayData>> mMarkDatas) {
        this.mMarkDatas = mMarkDatas;
    }

    public OnCalendarInterceptListener getOnCalendarInterceptListener() {
        return mOnCalendarInterceptListener;
    }
    // endregion

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

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
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
}