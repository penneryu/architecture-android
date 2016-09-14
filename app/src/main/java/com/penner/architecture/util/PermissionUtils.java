package com.penner.architecture.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.penner.architecture.R;

/**
 * Created by penneryu on 16/9/14.
 */
public final class PermissionUtils {

    public static boolean checkPermisssion(Activity activity, String permission, String dialogMessage, int requestCode, boolean finished) {
        if (!hasPermission(activity, permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
                createPermissionDialog(activity, dialogMessage, finished);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
            return false;
        }
        return true;
    }

    public static void createPermissionDialog(final Activity activity, String message, final boolean finished) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setTitle(activity.getString(R.string.permission));
        builder.setPositiveButton(activity.getString(R.string.permission_setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                dialog.dismiss();
                activity.finish();
            }
        });
        builder.setNegativeButton(activity.getString(R.string.penner_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (finished) {
                    activity.finish();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    public static boolean hasPermission(Context context, String permisssion) {
        try {
            return ContextCompat.checkSelfPermission(context, permisssion) == PackageManager.PERMISSION_GRANTED;
        } catch (Throwable throwable) {
            return false;
        }
    }
}
