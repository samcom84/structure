package com.example.commonstructure.common;

import java.util.ArrayList;
import java.util.Date;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class BaseList<T extends RealmObject> {
    public static final String TRY_AGAIN = "Please try again";
    public static final String NO_DATA_FOUND = "No data found";
    public static final String INTERNET_CONNECTION = "Please check internet connection";

    long TimeStampForNewDataLoad = 1000;//1 * 60 * 60 *
    Class<T> m_type = null;
    protected ArrayList<T> m_modelList = null;

    protected BaseList(Class<T> type) {
        m_type = type;
    }

    public void ClearDB() {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmQuery<T> query = realm.where(m_type);
            RealmResults<T> results = query.findAll();
            results.deleteAllFromRealm();
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadFromDB() {
        try {
            Realm realm = Realm.getDefaultInstance();
            RealmQuery<T> query = realm.where(m_type);
            RealmResults<T> results = query.findAll();
            m_modelList = new ArrayList<>(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClearDBById(String id, String value) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmQuery<T> query = realm.where(m_type).equalTo(id, value);
            RealmResults<T> results = query.findAll();
            results.clear();
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClearDBById(String id, int value) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmQuery<T> query = realm.where(m_type).equalTo(id, value);
            RealmResults<T> results = query.findAll();
            results.clear();
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromDBById(String id, String value) {
        try {
            Realm realm = Realm.getDefaultInstance();
            RealmQuery<T> query = realm.where(m_type).equalTo(id, value);
            RealmResults<T> results = query.findAll();
            m_modelList = new ArrayList<>(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<T> getList() {
        if (m_modelList == null)
            m_modelList = new ArrayList<T>();
        return m_modelList;
    }

    public boolean shouldLoadNewData() {
        String classtype = this.m_type.getName();
        LoadedData data = LoadedData.getLoadedData(classtype);
        long currentMillies = new Date().getTime();
        if (data != null) {
            if (currentMillies - data.getLoadedTimestamp() > TimeStampForNewDataLoad) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public void updateCurrentTimestampLoaded() {
        LoadedData.updateLoadedTimestamp(this.m_type.getName());
    }

}
