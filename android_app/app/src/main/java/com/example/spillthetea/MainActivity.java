package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;
    private File pictureFile;


    private ImageButton takePhotoButton;
    private Button viewTeaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Start view tea activity when clicking view tea
        this.viewTeaButton = findViewById(R.id.view_tea_button);
        this.viewTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTeaActivity.class);
                startActivity(intent);
            }
        });

        //Take photo when capture photo button is clicked
        this.takePhotoButton = findViewById(R.id.take_photo_button);
        this.takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    //Takes the photo and then calls the Camera.getPicture method below
                    camera.takePicture(null, null, getPicture);
                }
                else{
                    System.out.println("CAMERA IS NULL");
                }
            }
        });


    }

    Camera.PictureCallback getPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            pictureFile = getOutputMediaFile();
            System.out.println("TEST");
            System.out.println(pictureFile);

            //Open TeaPreview intent with new photo
            Intent intent = new Intent(getBaseContext(), PreviewTea.class);
            intent.putExtra("EXTRA_TEA_PREVIEW_PATH", pictureFile.getAbsolutePath());
            startActivity(intent);

            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found exception, photo capture failed");
            } catch (IOException e) {
                System.out.println("IO exception, photo capture failed");
            }
        }
    };

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        long timestamp = System.currentTimeMillis();
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timestamp + ".jpg");

        return mediaFile;
    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Launching camera");

        //Try to launch the camera, will fail if it is in use by another app
        this.camera = getCameraInstance();

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