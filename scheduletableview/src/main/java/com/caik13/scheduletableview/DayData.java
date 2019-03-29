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

import java.io.Serializable;

/**
 * @author weiyugang 2019年3月6日
 */
public class DayData implements Serializable {

    public static final int TIME_TYPE_MORNING = 0;
    public static final int TIME_TYPE_NOON = 1;
    public static final int TIME_TYPE_NIGHT = 2;

    private int year;
    private int month;
    private int day;
    private boolean isPreMonth = false;
    private boolean isNextMonth = false;

    private String[] mWeekBarStr = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private String[] mTimeTypeStr = {"上午", "下午", "晚上"};
    private String date; // 日期 2019-03-06
    private String time; // 03-06
    private int dayOfWeek;
    private int timeType = -1; // 0:上午、1下午、2晚上
    private int schemeBgColor;
    private int schemeTextColor;
    private int schemeTextSize; // 单位 dp
    private boolean isScheme = false;

    private String SchmeText;
    private String DefaultText;

    private boolean isWeekBar = false;

    public DayData() {}


    public String getTime() {
        return time;
    }

    public String getTimeStr() {
        String[] strs = time.split("-");
        if(strs.length == 2) {
            return strs[0] + "月" + strs[1] + "日";
        }
        return time;
    }

    public String getDayOfWeekStr() {
        return mWeekBarStr[dayOfWeek - 1];
    }

    public String getTimeTypeStr() {
        return timeType == -1 ? "" : mTimeTypeStr[timeType];
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchmeText() {
        return SchmeText == null ? "" : SchmeText;
    }

    public void setSchmeText(String schmeText) {
        SchmeText = schmeText;
    }

    public String getDefaultText() {
        return DefaultText;
    }

    public void setDefaultText(String defaultText) {
        DefaultText = defaultText;
    }

    public boolean isWeekBar() {
        return isWeekBar;
    }

    public void setWeekBar(boolean weekBar) {
        isWeekBar = weekBar;
    }

    public boolean isScheme() {
        return isScheme;
    }

    public void setScheme(boolean scheme) {
        isScheme = scheme;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getSchemeBgColor() {
        return schemeBgColor;
    }

    public void setSchemeBgColor(int schemeBgColor) {
        this.schemeBgColor = schemeBgColor;
    }

    public int getSchemeTextColor() {
        return schemeTextColor;
    }

    public void setSchemeTextColor(int schemeTextColor) {
        this.schemeTextColor = schemeTextColor;
    }

    public int getSchemeTextSize() {
        return schemeTextSize;
    }

    public void setSchemeTextSize(int schemeTextSize) {
        this.schemeTextSize = schemeTextSize;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isPreMonth() {
        return isPreMonth;
    }

    public void setPreMonth(boolean preMonth) {
        isPreMonth = preMonth;
    }

    public boolean isNextMonth() {
        return isNextMonth;
    }

    public void setNextMonth(boolean nextMonth) {
        isNextMonth = nextMonth;
    }
}
