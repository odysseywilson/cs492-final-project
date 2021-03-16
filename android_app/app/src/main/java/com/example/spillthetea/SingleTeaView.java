package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class SingleTeaView extends AppCompatActivity {
    public static final String TEA_ITEM = "SingleTeaView.TeaItem";
    private TeaItem teaItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tea_view);

        this.teaItem = (TeaItem) getIntent().getSerializableExtra(TEA_ITEM);
        System.out.println(this.teaItem.text);
    }
}