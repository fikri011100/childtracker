
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;

public class PojoEditSchedule {

    @SerializedName("hasil")
    private String mHasil;
    @SerializedName("msg")
    private String mMsg;

    public String getHasil() {
        return mHasil;
    }

    public void setHasil(String hasil) {
        mHasil = hasil;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

}
