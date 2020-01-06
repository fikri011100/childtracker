package com.titi.remotbayi.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getImmunization.php")
    Call<PojoSchedule> getSchedule();

    @FormUrlEncoded
    @POST("userLogin.php")
    Call<PojoLogin> doLogin(@Field("email") String str_email, @Field("password") String str_password);

    @FormUrlEncoded
    @POST("userRegister.php")
    Call<PojoRegister> doRegister(@Field("username") String str_name, @Field("email") String str_email,
                                @Field("password") String str_password);

    @FormUrlEncoded
    @POST("addImmunization.php")
        Call<PojoRegister> addSchedule(@Field("title") String title, @Field("desc") String desc,
                                  @Field("time") String time);
}