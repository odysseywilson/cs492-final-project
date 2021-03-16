package com.example.spillthetea;

import android.net.Uri;

import java.io.Serializable;

public class TeaItem implements Serializable {
    public Uri uri;
    public String text;
    public String author;
    public String time;
}
