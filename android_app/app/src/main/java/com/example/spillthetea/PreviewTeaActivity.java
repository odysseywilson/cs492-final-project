package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewTeaActivity extends AppCompatActivity {
    private Uri imageFilePath;
    private ImageView imageView;
    private Bitmap imageBitmap;

    //Buttons
    private ImageButton discardTeaButton;
    private ImageButton snapchatButton;
    private ImageButton addCaptionButton;
    private ImageButton serveTeaButton;

    private EditText captionEditText;
    private String captionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_tea);

        //Get image Uri from MainActivity and render image as bitmap
        this.imageView = findViewById(R.id.tea_preview_IV);
        System.out.println("FILEPATH: " + getIntent().getStringExtra("EXTRA_TEA_PREVIEW_PATH"));
        this.imageFilePath = Uri.parse(getIntent().getStringExtra("EXTRA_TEA_PREVIEW_PATH"));
        this.imageView.setImageURI(imageFilePath);

        //Caption
        this.captionEditText = findViewById(R.id.caption_ET);
        this.captionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //Menu buttons
        this.discardTeaButton = findViewById(R.id.discard_tea_button);
        this.snapchatButton = findViewById(R.id.snapchat_button);
        this.addCaptionButton = findViewById(R.id.add_caption_button);
        this.serveTeaButton = findViewById(R.id.serve_tea_button);

        this.discardTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to main activity to take new photo
                finish();
            }
        });

        this.snapchatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent snapchatIntent = new Intent(Intent.ACTION_SEND);
                snapchatIntent.setType("image/jpg");
                snapchatIntent.setPackage("com.snapchat.android");

                snapchatIntent.putExtra(Intent.EXTRA_STREAM, imageFilePath);
                startActivity(Intent.createChooser(snapchatIntent, "Open Snapchat"));
            }*/
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.setPackage("com.snapchat.android");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_STREAM, imageFilePath);
                startActivity(Intent.createChooser(intent, "Share Snapchat"));
            }
        });

        this.addCaptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.serveTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get caption from textview
                captionString = captionEditText.getText().toString();
                System.out.println("Caption: " + captionString);
            }
        });



    }
}