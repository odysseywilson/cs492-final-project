package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Launching camera");

        //Try to launch the camera, will fail if it is in use by another app
        Camera camera = getCameraInstance();

        //Create preview and set it as the intent of this activity
        this.cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.releaseCameraAndPreview();
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            System.out.println("Camera launched");
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            System.out.println("Camera failed to launch: " + e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseCameraAndPreview() {
        this.cameraPreview.setCamera(null);
        if (this.camera != null) {
            this.camera.release();
            this.camera = null;
        }
    }
}