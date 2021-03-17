package com.example.spillthetea;

import android.net.Uri;

import com.google.android.material.transition.ScaleProvider;

import java.io.Serializable;

public class TeaItem implements Serializable {
    public int id;
    public String username;
    public String caption;
    public long time;

    public TeaItem(int id, String username, String caption, long time){
        this.id = id;
        this.username = username;
        this.caption = caption;
        this.time = time;
    }

}
