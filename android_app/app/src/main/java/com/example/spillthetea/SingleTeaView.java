package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class SingleTeaView extends AppCompatActivity {
    public static final String TEA_ITEM = "SingleTeaView.TeaItem";
    private TeaItem teaItem = null;

    private FloatingActionButton closeTeaButton;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tea_view);

        this.teaItem = (TeaItem) getIntent().getSerializableExtra(TEA_ITEM);
        System.out.println(this.teaItem.text);

        //Setup close tea button
        this.closeTeaButton = findViewById(R.id.close_tea_button);
        this.closeTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}