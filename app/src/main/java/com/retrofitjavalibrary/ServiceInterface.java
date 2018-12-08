package com.retrofitjavalibrary;

import com.example.commonstructure.servicehelper.ServiceResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceInterface {

    @POST("api/call/getTodaysCallsWithMissedCalls")
    Call<ServiceResponse> getTodaysCalls(@Body JsonObject jsonObject);

}
