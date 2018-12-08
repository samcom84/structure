package com.retrofitjavalibrary;

import android.os.Bundle;
import android.widget.Toast;

import com.example.commonstructure.common.BaseActivity;
import com.example.commonstructure.common.BaseModel;
import com.example.commonstructure.common.ModelDelegates;
import com.retrofitjavalibrary.model.CallsInfo;
import com.retrofitjavalibrary.modellist.CallsList;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getList();
    }

    private void getList() {
        showProgress();
        CallsList.Instance().getTodaysCalls(new ModelDelegates.ModelDelegate<CallsInfo>() {
            @Override
            public void ModelLoaded(ArrayList<CallsInfo> list) {
                hideProgress();
                ArrayList<CallsInfo> mList =  BaseModel.Instance().getAll(CallsInfo.class);

                Toast.makeText(MainActivity.this, "Success :- " + mList.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
