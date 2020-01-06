
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;


public class Schedule {

    @SerializedName("schedule_desc")
    private String mScheduleDesc;
    @SerializedName("schedule_id")
    private String mScheduleId;
    @SerializedName("schedule_time")
    private String mScheduleTime;
    @SerializedName("schedule_title")
    private String mScheduleTitle;

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

}
