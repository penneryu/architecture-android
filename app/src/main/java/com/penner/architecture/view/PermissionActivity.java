package com.penner.architecture.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.penner.architecture.R;
import com.penner.architecture.util.PermissionUtils;

public class PermissionActivity extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        findViewById(R.id.permission_btn_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.checkPermisssion(PermissionActivity.this, Manifest.permission.RECORD_AUDIO,
                        getString(R.string.permission_tips, getString(R.string.app_name), getString(R.string.permission_micro)), PERMISSIONS_REQUEST_RECORD_AUDIO, false)) {
                    Toast.makeText(v.getContext(), "Permission Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_RECORD_AUDIO:
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        PermissionUtils.createPermissionDialog(this, getString(R.string.permission_tips, getString(R.string.app_name), getString(R.string.permission_micro)), false);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
