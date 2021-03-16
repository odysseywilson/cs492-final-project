package com.example.spillthetea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ViewTeaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TeaAdapter teaAdapter;

    private ImageButton refreshButton;
    private ImageButton toCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tea);

        //Setup recycler view
        this.recyclerView = findViewById(R.id.tea_rv);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setHasFixedSize(true);
        this.teaAdapter = new TeaAdapter();
        this.recyclerView.setAdapter(this.teaAdapter);

        TeaItem tea1 = new TeaItem();
        tea1.author = "Odyssey";
        tea1.time = "420";
        TeaItem tea2 = new TeaItem();
        tea2.author = "Xander";
        tea2.time = "420";
        this.teaAdapter.addTea(tea1);
        this.teaAdapter.addTea(tea2);

        //Setup camera button
        this.toCameraButton = findViewById(R.id.to_camera_button);
        this.toCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //Setup refresh button
        this.refreshButton = findViewById(R.id.refresh_button);
        this.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Make call to server to refresh tea listing

            }
        });
    }
}