package com.example.rbp687.a9aug2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FirebasePic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView targetImageView = (ImageView) findViewById(R.id.image_firebase);
        String internetUrl = "https://firebasestorage.googleapis.com/v0/b/angular-vector-139809.appspot.com/o/Koala.jpg?alt=media&token=5f8a80e0-f968-4caa-b93e-a252096684c1";

        Glide.with(this).load(internetUrl).into(targetImageView);
    }
}
