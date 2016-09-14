package com.penner.architecture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.penner.architecture.util.PermissionUtils;
import com.penner.architecture.view.MainActivity;

public class FirstActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 101;

    private boolean mIschecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mIschecked) {
            check();
            mIschecked = true;
        }
    }

    private void check() {
        if (PermissionUtils.checkPermisssion(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                getString(R.string.permission_tips, getString(R.string.app_name), getString(R.string.permission_storage)), PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE, true)) {
            if (PermissionUtils.checkPermisssion(this, Manifest.permission.READ_PHONE_STATE,
                    getString(R.string.permission_phone), PERMISSIONS_REQUEST_READ_PHONE_STATE, true)) {
                start();
            }
        }
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            case PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        check();
                    } else {
                        String message = requestCode == PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE ?
                                getString(R.string.permission_tips, getString(R.string.app_name), getString(R.string.permission_storage)) : getString(R.string.permission_phone, getString(R.string.app_name));
                        PermissionUtils.createPermissionDialog(this, message, true);
                    }
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
