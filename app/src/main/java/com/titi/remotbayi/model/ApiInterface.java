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
    @POST("addDetailApp.php")
    Call<PojoEditSchedule> editDetail(@Field("title") String title);

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

    @FormUrlEncoded
    @POST("editDataImmunization.php")
    Call<PojoEditSchedule> editSchedule(@Field("schedule_id") String id, @Field("schedule_title") String title,
                                   @Field("schedule_desc") String desc, @Field("schedule_time") String time);

    @FormUrlEncoded
    @POST("deleteImmunization.php")
    Call<PojoEditSchedule> deleteSchedule(@Field("schedule_id") String id);
}
