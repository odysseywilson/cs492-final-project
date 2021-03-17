package com.example.spillthetea;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ViewTeaActivity extends AppCompatActivity
        implements TeaAdapter.OnTeaItemClickedListener{

    private RecyclerView recyclerView;
    private TeaAdapter teaAdapter;
    private TeaViewModel teaViewModel;

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
        this.teaAdapter = new TeaAdapter(this);
        this.recyclerView.setAdapter(this.teaAdapter);

        this.teaViewModel = new ViewModelProvider(this).get(TeaViewModel.class);

        this.teaViewModel.getTea().observe(
                this,
                new Observer<ArrayList<TeaItem>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<TeaItem> teaItems) {
                        for(TeaItem i : teaItems)
                        {
                            Log.d("TEA TEST", i.username + ": " + i.caption);
                        }
                        teaAdapter.updateTea(teaItems);
                    }
        });


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
                teaViewModel.getTea();
            }
        });

        teaViewModel.getTea();
    }

    @Override
    public void onTeaItemClicked(TeaItem teaItem) {
        Intent intent = new Intent(this, SingleTeaView.class);
        intent.putExtra(SingleTeaView.TEA_ITEM, teaItem);
        startActivity(intent);
    }
}