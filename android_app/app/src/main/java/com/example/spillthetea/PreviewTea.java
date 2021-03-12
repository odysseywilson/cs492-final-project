package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class PreviewTea extends AppCompatActivity {
    private Uri imageFilePath;
    private ImageView imageView;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_tea);

        this.imageView = findViewById(R.id.tea_preview_IV);
        System.out.println("FILEPATH: " + getIntent().getStringExtra("EXTRA_TEA_PREVIEW_PATH"));
        this.imageFilePath = Uri.parse(getIntent().getStringExtra("EXTRA_TEA_PREVIEW_PATH"));
        //Uri tempURI = Uri.parse("/storage/emulated/0/Pictures/MyCameraApp/IMG_1615534786416.jpg");
        /*Uri tempURI = this.imageFilePath;
        this.imageView.setImageURI(tempURI);*/

        try {
            String uriString = this.imageFilePath.toString();
            this.imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(uriString)));
            this.imageView.setImageBitmap(this.imageBitmap);
        } catch (IOException e) {
            System.out.println("Error with bitmap");
            e.printStackTrace();
        }
    }
}