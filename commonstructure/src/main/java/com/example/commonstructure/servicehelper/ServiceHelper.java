package com.example.commonstructure.servicehelper;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceHelper {

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static String BASE_URL;
    private static Retrofit retrofit = null;

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static Retrofit getService() throws Exception {

        if (BASE_URL == null) {
            throw new Exception("Base url not found");
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpHeaderInterceptor headers = new HttpHeaderInterceptor();
        headers.addHeader("Content-Type", "application/json");
        headers.addHeader("Accept", "*/*");

        OkHttpClient client = new OkHttpClient.
                Builder()
                .connectTimeout(7000, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .addInterceptor(headers)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    @NonNull
    public static RequestBody createRequestBody(String descriptionString) {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
