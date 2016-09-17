package com.example.rbp687.a9aug2016.mPicasso;

import android.content.Context;
import android.widget.ImageView;

import com.example.rbp687.a9aug2016.R;
import com.squareup.picasso.Picasso;

/**
 * Created by RBP687 on 9/17/2016.
 */

public class PicasoClient {
    public static void downloadImage(Context c, String url, ImageView img) {
        if(url != null && url.length()>0){
            Picasso.with(c).load(url).placeholder(R.drawable.placeholder).into(img);
        }else {
            Picasso.with(c).load(R.drawable.placeholder).into(img);
        }
    }
}
