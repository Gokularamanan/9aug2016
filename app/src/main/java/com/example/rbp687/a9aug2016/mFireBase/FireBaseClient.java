package com.example.rbp687.a9aug2016.mFireBase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.rbp687.a9aug2016.Constants;
import com.example.rbp687.a9aug2016.Userinput;
import com.example.rbp687.a9aug2016.mData.Movie;
import com.example.rbp687.a9aug2016.mRecycler.MyAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by RBP687 on 9/17/2016.
 */

public class FireBaseClient {

    private static final String TAG = "FireBaseClient";
    Context c;
    String DB_URL;
    RecyclerView rv;

    Firebase fire;
    ArrayList<Movie> movies = new ArrayList<>();
    MyAdapter adapter;


    public FireBaseClient(Context c, String DB_URL, RecyclerView rv) {
        this.c = c;
        this.DB_URL = DB_URL;
        this.rv = rv;

        //Initialize
        Firebase.setAndroidContext(c);
        //Instantiate
        fire=new Firebase(DB_URL);
    }

    public FireBaseClient(Context c, String fireBaseBaseUrl) {
        this.c = c;
        this.DB_URL = fireBaseBaseUrl;

        //Initialize
        Firebase.setAndroidContext(c);
        //Instantiate
        fire=new Firebase(DB_URL);

    }

    //Save
    public void saveOnline(String name, String url, String desc, int status) {
        Movie m = new Movie();
        m.setName(name);
        m.setUrl(url);
        m.setStatus(status);
        m.setDesc(desc);

        fire.child("AllIncoming").push().setValue(m);
    }

    //Retrive
    public void refreshData(){
        Log.d(TAG, "refreshData");
        Log.d(TAG, fire.toString());
        fire.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded");
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged");
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, "onCancelled");
            }
        });

    }

    private void getUpdates(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUpdates");
        movies.clear();
        for(DataSnapshot ds :dataSnapshot.getChildren()) {
            int status = ds.getValue(Movie.class).getStatus();
            if (status <= Constants.publishReviewPending) {
                String name= ds.getValue(Movie.class).getName();
                String url= ds.getValue(Movie.class).getUrl();
                Log.i(TAG, name + " " + url + " " + Integer.toString(status));
                Movie m=new Movie();
                m.setName(name);
                m.setUrl(url);
                movies.add(m);
            }
        }
        if(movies.size()>0) {
            adapter=new MyAdapter(c,movies);
            rv.setAdapter(adapter);
        }else {
            Toast.makeText(c,"No data", Toast.LENGTH_LONG);
        }
    }

}
