
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;

public class PojoRegister {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Boolean mResult;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Boolean getResult() {
        return mResult;
    }

    public void setResult(Boolean result) {
        mResult = result;
    }

}
