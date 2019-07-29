package com.guideme.guideme.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsHelper {

    public static final int CALL_PHONE_REQUEST = 1000;

    public static boolean isPermissionGranted(Context context, String permission) {
        int deviceVersion = Build.VERSION.SDK_INT;
        if (deviceVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static void requestPermission(Context context, String permission, int requestCode) {
        requestPermissions(context, new String[]{permission}, requestCode);
    }

    public static void requestPermissions(Context context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }
}
