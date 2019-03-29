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
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * @author weiyugang 2019年3月6日
 */
public class ScheduleTable extends LinearLayout {
    private String[] timeStr = {"日\n期", "上\n午", "下\n午", "晚\n上"};
    private ScheduleTableConfigDelegate mDelegate;
    private ScheduleView mScheduleView;

    public ScheduleTable(@NonNull Context context) {
        this(context, null);
    }

    public ScheduleTable(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleTable(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDelegate = new ScheduleTableConfigDelegate(getContext(), attrs);

        init();
    }

    private void init() {

        this.setOrientation(HORIZONTAL);
        initLeftBar();
        initWeek();
    }

    private void initLeftBar() {
        LinearLayout leftBarLayout = new LinearLayout(getContext());
        leftBarLayout.setOrientation(LinearLayout.VERTICAL);
        leftBarLayout.setBackgroundColor(mDelegate.getLeftBarBgColor());
        for (int i = 0; i < timeStr.length; i++) {
            TextView textView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(mDelegate.getLeftBarItemWith(), mDelegate.getLeftBarItemHeight());
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setText(timeStr[i]);
            textView.setTextColor(mDelegate.getLeftBarTextColor());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDelegate.getLeftBarTextSize());

            GradientDrawable drawable = new GradientDrawable();
            drawable.setStroke(mDelegate.getLeftBarBoderWith(), mDelegate.getLeftBarBoderColor());
            textView.setBackgroundDrawable(drawable);

            leftBarLayout.addView(textView);
        }
        this.addView(leftBarLayout);
    }

    private void initWeek() {
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getContext());
        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        horizontalScrollView.setLayoutParams(layoutParams2);

        mScheduleView = new ScheduleView(getContext());
        mScheduleView.setUp(mDelegate);
        horizontalScrollView.addView(mScheduleView);
        this.addView(horizontalScrollView);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    // region setting
    // region leftbar
    public void setLeftBarTextColor(int mLeftBarTextColor) {
        mDelegate.setLeftBarTextColor(mLeftBarTextColor);
    }

    public void setLeftBarTextSize(int mLeftBarTextSize) {
        mDelegate.setLeftBarTextSize(mLeftBarTextSize);
    }

    public void setLeftBarBoderWith(int mLeftBarBoderWith) {
        mDelegate.setLeftBarBoderWith(mLeftBarBoderWith);
    }

    public void setLeftBarBoderColor(int mLeftBarBoderColor) {
        mDelegate.setLeftBarBoderColor(mLeftBarBoderColor);
    }

    public void setLeftBarBgColor(int mLeftBarBgColor) {
        mDelegate.setLeftBarBgColor(mLeftBarBgColor);
    }
    // endregon

    // region schme

    /**
     * 设置标记背景色
     *
     * @param color 背景颜色
     */
    public void setSchemeBgColor(int color) {
        mDelegate.setSchemeBgColor(color);
    }

    /**
     * 设置标记文字颜色
     *
     * @param color 文字颜色
     */
    public void setSchemeTextColor(int color) {
        mDelegate.setSchemeTextColor(color);
    }

    /**
     * 设置标记文字大小
     *
     * @param size sp 文字大小
     */
    public void setSchemeTextSize(int size) {
        mDelegate.setSchemeTextSize(size);
    }

    // endregion

    public void setOnCalendarInterceptListener(ScheduleTableConfigDelegate.OnCalendarInterceptListener onCalendarInterceptListener) {
        mDelegate.setOnCalendarInterceptListener(onCalendarInterceptListener);
    }

    public void setMarkData(Map<String, List<DayData>> markDatas) {
        mDelegate.setMarkData(markDatas);
        mScheduleView.notifyMarkData();
    }
    // endregion

    public void startAndStopDraw(boolean stop) {
        mDelegate.setStop(stop);
    }

    /**
     * 设置是否可以点击
     *
     * @param clickable
     */
    public void setCalendarClickable(boolean clickable) {
        mDelegate.setClickAble(clickable);
    }

    public boolean isCalendarClickable() {
        return mDelegate.isClickAble();
    }
}
