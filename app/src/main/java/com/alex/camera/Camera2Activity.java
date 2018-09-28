package com.alex.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.alex.camera.v2.Camera2Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Camera2Activity extends AppCompatActivity {

    private final String[] PERMISSIONS = {
            Manifest.permission.CAMERA
    };
    /**
     * If you want use samsung api change openCamera() to openCameraSamsung()
     */
    //camera2 api
    private Camera2Manager cameraApi2Manager;
    private SurfaceView surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        surface = findViewById(R.id.surfaceView);

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "This app need camera permission, closing app..", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    /**
     * Example Camera2Manager
     */
    private void openCamera() {
        surface.setVisibility(View.VISIBLE);
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                cameraApi2Manager =
                        new Camera2Manager(surfaceHolder.getSurface(), getApplicationContext());
                cameraApi2Manager.openCameraBack();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraApi2Manager != null) cameraApi2Manager.closeCamera();
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null
                && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
