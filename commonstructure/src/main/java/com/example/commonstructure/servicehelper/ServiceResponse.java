package com.example.commonstructure.servicehelper;

import com.google.gson.annotations.SerializedName;

public class ServiceResponse {

    @SerializedName("data")
    public Object RawResponse;
    @SerializedName("is_error")
    public boolean is_error;
    @SerializedName("message")
    public String Message = "";

}
