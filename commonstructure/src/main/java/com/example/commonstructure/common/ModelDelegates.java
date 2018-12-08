package com.example.commonstructure.common;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmObject;

public class ModelDelegates {

  public interface LoginDelegate {
    public void LoginDidSuccess();
    public void LoginFailedWithError(String error);
  }


  public interface CommonInfoDelegate<T> {

    public void CallFailedWithError(String error);

    public void CallDidSuccess(T info);
  }


  public interface CommonDelegate1 {
    public void CallDidSuccess(String msg);

    public void CallFailedWithError(String error);
  }

  public interface ModelDelegate<T extends RealmObject> {
    public void ModelLoaded(ArrayList<T> list);

    public void ModelLoadFailedWithError(String error);
  }

  public interface CommonDelegate {
    public void CallDidSuccess(boolean flag);
    public void CallFailedWithError(String error);
  }


  public interface LatLangDelegate {
    public void CallSuccess(double d1, double d2);

    public void CallFailureWithError(String error);
  }


  public interface UniversalTempDelegate {
    public void CallDidSuccess(String res);

    public void CallFailedWithError(String error);
  }


  public interface ComonJsoObject<T extends RealmObject>  {
    public void CallDidSuccess(JSONObject res);

    public void CallFailedWithError(String error);
  }

  public interface UniversalBitmapDelegate {
    public void CallDidSuccess(Bitmap bita);

    public void CallFailedWithError(String error);
  }

  public interface Permissions {
    public void Granted();

    public void Denied();

    public void NeverAskAgain();
  }

}
