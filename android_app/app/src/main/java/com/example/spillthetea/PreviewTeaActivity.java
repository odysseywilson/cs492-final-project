package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PreviewTeaActivity extends AppCompatActivity {
    private Uri imageFilePath;
    private ImageView imageView;

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
                ApiRepository apiRepository = new ApiRepository();

                // evil workaround for file not working
                File dir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "SpillTheTea");

                long timestamp = System.currentTimeMillis();
                File mediaFile;
                mediaFile = new File(dir.getPath() + File.separator
                        + "IMG_" + timestamp + ".jpg");

                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageFilePath);
                    OutputStream outputStream = new FileOutputStream(mediaFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while((len=inputStream.read(buf))>0){
                        outputStream.write(buf,0,len);
                    }
                    outputStream.close();
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);
                MultipartBody.Part image = MultipartBody.Part.createFormData("upload", mediaFile.getName(), body);

                apiRepository.postImage("test1", "test2", image);

                finish();
            }
        });


    }
}