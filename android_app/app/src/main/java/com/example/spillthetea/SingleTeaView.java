package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class SingleTeaView extends AppCompatActivity {
    public static final String TEA_ITEM = "SingleTeaView.TeaItem";
    private TeaItem teaItem = null;

    private FloatingActionButton closeTeaButton;

    private TextView captionTV;
    private ImageView imageIV;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tea_view);

        this.captionTV = findViewById(R.id.single_tea_tv);
        this.imageIV = findViewById(R.id.single_tea_iv);

        this.teaItem = (TeaItem) getIntent().getSerializableExtra(TEA_ITEM);

        if(teaItem.caption.isEmpty())
        {
            this.captionTV.setVisibility(View.INVISIBLE);
        }
        this.captionTV.setText(teaItem.caption);

        DownloadImageTask task = new DownloadImageTask(imageIV);
        task.execute("http://192.168.1.100:3000/" + teaItem.id + ".png");

        //Setup close tea button
        this.closeTeaButton = findViewById(R.id.close_tea_button);
        this.closeTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bmp = BitmapFactory.decodeStream(in);
                Matrix m = new Matrix();
                m.postRotate(90);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}