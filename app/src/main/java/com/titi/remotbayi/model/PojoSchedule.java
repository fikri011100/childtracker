
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoSchedule {

    @SerializedName("schedule")
    private List<Schedule> mSchedule;
    @SerializedName("status")
    private Boolean mStatus;

    public List<Schedule> getSchedule() {
        return mSchedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        mSchedule = schedule;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
