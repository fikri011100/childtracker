
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoLogin {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Boolean mResult;
    @SerializedName("user")
    private List<User> mUser;

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

    public List<User> getUser() {
        return mUser;
    }

    public void setUser(List<User> user) {
        mUser = user;
    }

}
