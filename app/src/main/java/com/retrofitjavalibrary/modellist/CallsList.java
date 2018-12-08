package com.retrofitjavalibrary.modellist;

import com.example.commonstructure.common.BaseList;
import com.example.commonstructure.common.ModelDelegates;
import com.example.commonstructure.common.NetworkConnectivity;
import com.example.commonstructure.servicehelper.ServiceHelper;
import com.example.commonstructure.servicehelper.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.retrofitjavalibrary.ServiceInterface;
import com.retrofitjavalibrary.app.CommonApplication;
import com.retrofitjavalibrary.model.CallsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallsList extends BaseList<CallsInfo> {
    private static volatile CallsList _instance = null;
    private ServiceInterface mInterface;
    private ModelDelegates.ModelDelegate<CallsInfo> m_delegate = null;
    private Realm realm;

    private CallsList() {
        super(CallsInfo.class);
    }

    public static CallsList Instance() {
        if (_instance == null) {
            synchronized (CallsList.class) {
                _instance = new CallsList();
            }
        }
        return _instance;
    }

    public ServiceInterface getInterface(){
        try{
            mInterface = ServiceHelper.getService().create(ServiceInterface.class);
        }catch (Exception ex) {

        }
        return mInterface;
    }

    //get calls for todays fragments
    public void getTodaysCalls(final ModelDelegates.ModelDelegate<CallsInfo> m_delegate) {
        JSONObject jsonObjectMain = new JSONObject();
        try {
            jsonObjectMain.put("user_id", "5b6e87bd176e885f1500f747");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObject gsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        gsonObject = (JsonObject) jsonParser.parse(String.valueOf(jsonObjectMain));

        if (NetworkConnectivity.isConnected()) {
            Call<ServiceResponse> call = getInterface().getTodaysCalls(gsonObject);
            call.enqueue(new Callback<ServiceResponse>() {

                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    m_modelList = new ArrayList<>();
                    try {
                        ServiceResponse response1 = response.body();
                        if (!response1.is_error) {
                            if (response1.RawResponse.toString() != null) {
                                String json = new Gson().toJson(response1.RawResponse);
                                JSONArray arr = new JSONArray(json);

                                if (arr != null && arr.length() > 0) {
                                    realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    realm.createOrUpdateAllFromJson(CallsInfo.class, arr);
                                    realm.commitTransaction();
                                    realm.close();
                                    loadFromDB();
                                    if (m_delegate != null)
                                        m_delegate.ModelLoaded(m_modelList);
                                } else {
                                    if (m_delegate != null)
                                        m_delegate.ModelLoadFailedWithError(NO_DATA_FOUND);
                                }
                            } else {
                                if (m_delegate != null)
                                    m_delegate.ModelLoadFailedWithError(response1.Message);
                            }
                        } else {
                            if (m_delegate != null)
                                m_delegate.ModelLoadFailedWithError(response1.Message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (m_delegate != null)
                            m_delegate.ModelLoadFailedWithError("" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {
                    if (m_delegate != null)
                        m_delegate.ModelLoadFailedWithError(t.getMessage());
                }
            });
        } else {
            if (m_delegate != null)
                m_delegate.ModelLoadFailedWithError(INTERNET_CONNECTION);
        }
    }

}
