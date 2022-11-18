package com.example.gymmanagementapp.uitl.api;



import com.example.gymmanagementapp.pojo.AddUserRequest;
import com.example.gymmanagementapp.pojo.AddUserScheduleRequest;
import com.example.gymmanagementapp.pojo.BaseRequest;
import com.example.gymmanagementapp.pojo.EquipmentModel;
import com.example.gymmanagementapp.pojo.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @POST("/users")
    Call<AddUserRequest> create(@Body AddUserRequest request);

    @GET("/users")
    Call<List<UserModel>> users();

    @GET("/users/{number}")
    Call<UserModel> getUser(@Path("number") String number);

    @POST("/{route}")
    Call<BaseRequest> add(@Path("route") String route, @Body BaseRequest request);

    @GET("/{route}")
    Call<List<BaseRequest>> get(@Path("route") String route);

    @GET("/equipment")
    Call<List<EquipmentModel>> getEquips();

    @PUT("/equipment/{id}")
    Call<EquipmentModel> update(@Path("id") String id, @Body EquipmentModel model);

    @POST("/userschedules")
    Call<AddUserScheduleRequest> addSchedule(@Body AddUserScheduleRequest request);

    @GET("/userschedules/{id}")
    Call<List<AddUserScheduleRequest>> getSchedules(@Path("id") String userId);
    @DELETE("/{route}/{id}")
    Call<String> delete(@Path("route") String route, @Path("id") String id);
}
