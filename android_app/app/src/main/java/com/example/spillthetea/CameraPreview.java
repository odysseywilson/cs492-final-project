package com.example.spillthetea;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera cam;
    private SurfaceHolder holder;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.cam = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        this.holder = getHolder();
        this.holder.addCallback(this);


    }

    public void setCamera(Camera c){
        this.cam = c;
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try{
            if (this.cam != null) {
                this.cam.setPreviewDisplay(holder);
                this.cam.setDisplayOrientation(90);
                this.cam.startPreview();
            }
        } catch (IOException e){
            System.out.println("Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        /*//This is where we handle changes to orientation
        //You must stop the preview before resizing or reformatting it

        if (holder.getSurface() == null){
            return; // surface preview does not exist
        }

        try{
            cam.stopPreview();
        } catch (Exception e){
            //Ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try{
            cam.setPreviewDisplay(holder);
            cam.startPreview();
        } catch (Exception e){
            System.out.println("Error starting camera preview: " + e.getMessage());
        }*/

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //Unneeded, we release the camera in main activity when we leave the camera page or close
        //the app
    }
}
