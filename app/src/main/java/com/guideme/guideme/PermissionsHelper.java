package com.guideme.guideme;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsHelper {

    public static final int CALL_PHONE_REQUEST = 1000;

    private Context context;

    public PermissionsHelper(Context context) {
        this.context = context;
    }

    public boolean isPermissionGranted(String permission) {
        int deviceVersion = Build.VERSION.SDK_INT;
        if (deviceVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public void requestPermission(String permission, int requestCode) {
        requestPermissions(new String[]{permission}, requestCode);
    }

    public void requestPermissions(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }
}
