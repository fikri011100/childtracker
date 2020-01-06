
package com.titi.remotbayi.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("username")
    private String mUsername;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
