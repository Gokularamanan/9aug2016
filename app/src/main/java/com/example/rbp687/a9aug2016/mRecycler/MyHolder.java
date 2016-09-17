package com.example.rbp687.a9aug2016.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rbp687.a9aug2016.R;

/**
 * Created by RBP687 on 9/17/2016.
 */

public class MyHolder extends RecyclerView.ViewHolder{

    TextView nameTxt;
    ImageView img;

    public MyHolder(View itemView) {
        super(itemView);

        nameTxt = (TextView)itemView.findViewById(R.id.nameTxt);
        img = (ImageView)itemView.findViewById(R.id.movieImage);

    }
}
