package com.example.rbp687.a9aug2016.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rbp687.a9aug2016.R;
import com.example.rbp687.a9aug2016.mData.Movie;
import com.example.rbp687.a9aug2016.mPicasso.PicasoClient;

import java.util.ArrayList;

/**
 * Created by RBP687 on 9/17/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    private static final String TAG = "MyAdapter";
    Context c;
    ArrayList<Movie> movies;

    public MyAdapter(Context c, ArrayList<Movie> movies) {
        Log.d(TAG, "Construct");
        this.c = c;
        this.movies = movies;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        holder.nameTxt.setText(movies.get(position).getName());

        PicasoClient.downloadImage(c,movies.get(position).getUrl(),holder.img);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
