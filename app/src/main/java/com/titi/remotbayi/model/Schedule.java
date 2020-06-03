
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("bb_standart")
    private String mBbStandart;
    @SerializedName("schedule_desc")
    private String mScheduleDesc;
    @SerializedName("schedule_id")
    private String mScheduleId;
    @SerializedName("schedule_time")
    private String mScheduleTime;
    @SerializedName("schedule_title")
    private String mScheduleTitle;
    @SerializedName("st_standart")
    private String mStStandart;
    @SerializedName("tb_standart")
    private String mTbStandart;

    public String getBbStandart() {
        return mBbStandart;
    }

    public void setBbStandart(String bbStandart) {
        mBbStandart = bbStandart;
    }

    public String getScheduleDesc() {
        return mScheduleDesc;
    }

    public void setScheduleDesc(String scheduleDesc) {
        mScheduleDesc = scheduleDesc;
    }

    public String getScheduleId() {
        return mScheduleId;
    }

    public void setScheduleId(String scheduleId) {
        mScheduleId = scheduleId;
    }

    public String getScheduleTime() {
        return mScheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        mScheduleTime = scheduleTime;
    }

    public String getScheduleTitle() {
        return mScheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        mScheduleTitle = scheduleTitle;
    }

    public String getStStandart() {
        return mStStandart;
    }

    public void setStStandart(String stStandart) {
        mStStandart = stStandart;
    }

    public String getTbStandart() {
        return mTbStandart;
    }

    public void setTbStandart(String tbStandart) {
        mTbStandart = tbStandart;
    }

}
