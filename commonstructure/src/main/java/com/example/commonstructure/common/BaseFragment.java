package com.example.commonstructure.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseFragment extends Fragment {

    ProgressDialog m_pd = null;
    boolean checkdialog = true;
    private ModelDelegates.Permissions delegate = null;
    private List<String> newListPermissionsNeeded = new ArrayList<>();
    private int PERMISSIONS_REQUEST = 123;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideKeyboard(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            m_pd = new ProgressDialog(getActivity());
            m_pd.setCancelable(false);
            m_pd.setMessage("Please Wait");
            m_pd.setIndeterminate(true);
        } else {
            m_pd = new ProgressDialog(getActivity());
            m_pd.setCancelable(false);
            m_pd.setMessage("Please Wait");
            m_pd.setIndeterminate(true);
        }
    }

    public void showProgress() {
        if (m_pd != null) {
            m_pd.setMessage("Please Wait");
            m_pd.setCancelable(false);
            checkdialog = false;
            m_pd.show();
        }
    }

    protected void showProgress(String msg) {
        if (m_pd != null) {
            // m_pd.setCancelable(false);
            m_pd.setMessage(msg);
            checkdialog = false;
            m_pd.show();
        }
    }

    protected void showProgress(int style, String msg) {
        if (m_pd != null) {
            m_pd.setCancelable(false);
            m_pd.setProgressStyle(style);
            m_pd.setIndeterminate(true);
            m_pd.setMessage(msg);
            m_pd.setProgressNumberFormat(null);
            m_pd.setProgressPercentFormat(null);
            checkdialog = false;
            m_pd.show();
        }
    }

    protected void showProgress(boolean flag, String msg) {
        if (m_pd != null) {
            m_pd.setCancelable(flag);
            m_pd.setMessage(msg);
            checkdialog = false;
            m_pd.show();
        }
    }

    public void showProgress(boolean flag) {
        if (m_pd != null) {
            m_pd.setMessage("Please Wait");
            m_pd.setCancelable(flag);
            checkdialog = false;
            m_pd.show();
        }
    }

    protected void hideProgress() {
        if (m_pd != null) {
            try {
                m_pd.dismiss();
            } catch (Exception e) {
            }
            if (!checkdialog) {
                m_pd.dismiss();
                checkdialog = true;
            }
        }
    }

    public void HideKeyboard(Activity context) {
        View view = context.findViewById(android.R.id.content).getRootView();//context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void checkAndRequestPermissions(List<String> listPermissionsNeeded, ModelDelegates.Permissions permissions) {
        delegate = permissions;
        newListPermissionsNeeded = listPermissionsNeeded;

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSIONS_REQUEST);
//            return false;
        } else {
            if (delegate != null)
                delegate.Granted();
        }
        //return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            Map<String, Integer> perms = new HashMap<>();
            // Initial
            for (int i = 0; i < newListPermissionsNeeded.size(); i++) {
                perms.put(newListPermissionsNeeded.get(i), PackageManager.PERMISSION_GRANTED);
            }
            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);

            boolean isDenied = true;
            for (int i = 0; i < newListPermissionsNeeded.size(); i++) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), newListPermissionsNeeded.get(i))) {
                    isDenied = false;
                } else {
                    isDenied = true;
                    break;
                }
            }
            if (!isDenied) {
                if (delegate != null)
                    delegate.Denied();
            } else {
                boolean isGenrated = false;
                for (int i = 0; i < perms.size(); i++) {
                    if (perms.get(newListPermissionsNeeded.get(i)) == PackageManager.PERMISSION_GRANTED) {
                        isGenrated = true;
                    } else {
                        isGenrated = false;
                        break;
                    }
                }
                if (isGenrated) {
                    if (delegate != null)
                        delegate.Granted();
                } else {
                    if (delegate != null)
                        delegate.NeverAskAgain();
                }
            }
        }
    }

}