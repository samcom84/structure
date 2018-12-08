package com.example.commonstructure.common;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

public class LoadedData extends RealmObject {

  private String ClassType = "";
  private long LoadedTimestamp = 0;

  public String getClassType() {
    return ClassType;
  }

  public void setClassType(String classType) {
    ClassType = classType;
  }

  public long getLoadedTimestamp() {
    return LoadedTimestamp;
  }

  public void setLoadedTimestamp(long loadedTimestamp) {
    LoadedTimestamp = loadedTimestamp;
  }

  public static void updateLoadedTimestamp(String classtype) {
    try {
      Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();
      RealmQuery<LoadedData> query = realm.where(LoadedData.class).equalTo("ClassType", classtype);
      LoadedData results = query.findFirst();
      if (results != null) {
        results.setLoadedTimestamp(new Date().getTime());
      } else {
        LoadedData data = new LoadedData();
        data.setLoadedTimestamp(new Date().getTime());
        data.setClassType(classtype);
        realm.copyToRealm(data);
      }
      realm.commitTransaction();
      realm.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static LoadedData getLoadedData(String classtype) {
    try {
      LoadedData data = null;
      Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();
      RealmQuery<LoadedData> query = realm.where(LoadedData.class).equalTo("ClassType", classtype);
      LoadedData results = query.findFirst();
      if (results != null) {
        data = results;
      } else {
        data = new LoadedData();
        data.setLoadedTimestamp(new Date().getTime());
        data.setClassType(classtype);
        realm.copyToRealm(data);
      }
      realm.commitTransaction();
      realm.close();
      return data;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
