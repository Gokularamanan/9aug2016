package com.example.rbp687.a9aug2016;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rbp687.a9aug2016.mFireBase.FireBaseClient;
import com.firebase.client.Firebase;

public class MainActivity1 extends AppCompatActivity {

    final static String DB_URL = "https://angular-vector-139809.firebaseio.com/todoItems";
    private static final String TAG = "MainActivity1";
    EditText nameEditText,urlEditText;
    Button saveBtn;
    RecyclerView rv;

    FireBaseClient fireBaseClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        Log.d(TAG, "onCreate");

        rv=(RecyclerView)findViewById(R.id.mRecycleID);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "Layout and view are ready");

        fireBaseClient = new FireBaseClient(this,DB_URL,rv);
        fireBaseClient.refreshData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask().execute(new Pair<Context, String>(getApplicationContext(), "App part"));
                displayDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_settings1) {
            Intent intent = new Intent(this, ScrollingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_settings2) {
            Intent intent = new Intent(this, FirebasePic.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Show input dialog
    private void displayDialog(){
        Dialog d = new Dialog(this);
        d.setTitle("Save Online");
        d.setContentView(R.layout.dialoglayout);

        nameEditText=(EditText)d.findViewById(R.id.nameEditText);
        urlEditText=(EditText)d.findViewById(R.id.uriEditText);

        saveBtn = (Button)d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseClient.saveOnline(nameEditText.getText().toString(), urlEditText.getText().toString());

                nameEditText.setText("");
                urlEditText.setText("");
            }
        });

        //Show
        d.show();
    }
}
