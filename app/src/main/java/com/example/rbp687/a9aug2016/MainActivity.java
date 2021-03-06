package com.example.rbp687.a9aug2016;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ///mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new EndpointsAsyncTask().execute(new Pair<Context, String>(getApplicationContext(), "App part"));


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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    ListView list;
                    String[] web = {
                            "Google Plus",
                            "Twitter",
                            "Windows",
                            "Bing",
                            "Itunes",
                            "Wordpress",
                            "Drupal"
                    } ;
                    String[] imageId = {
                            Constants.internetUrl,
                            Constants.internetUrlChick,
                            Constants.internetUrlMutton,
                            Constants.internetUrlFish,
                            Constants.internetUrlChick,
                            Constants.internetUrlMutton,
                            Constants.internetUrlFish
                    };
                    CustomList adapter = new
                            CustomList(getActivity(), web, imageId);
                    list=(ListView)rootView.findViewById(R.id.list);
                    list.setAdapter(adapter);
/*                    internetUrl = Constants.internetUrlChick;
                    Glide.with(this).load(internetUrl).into(targetImageView);*/
                    return rootView;
                case 2:
/*                    internetUrl = Constants.internetUrlMutton;
                    Glide.with(this).load(internetUrl).into(targetImageView);*/
                    return rootView;
                case 3:
/*                    internetUrl = Constants.internetUrlFish;
                    Glide.with(this).load(internetUrl).into(targetImageView);*/
                    return rootView;
            }
            return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            switch (position) {
                case 0:
                    switch (sharedPreferences.getInt("a",0)){
                        case 0:
                            return "00";
                        case 1:
                            return "01";
                        case 2:
                            return "02";

                    }
                case 1:
                    switch (sharedPreferences.getInt("a",0)){
                        case 0:
                            return "10";
                        case 1:
                            return "11";
                        case 2:
                            return "12";

                    }
                case 2:
                    switch (sharedPreferences.getInt("a",0)){
                        case 0:
                            return "20";
                        case 1:
                            return "21";
                        case 2:
                            return "22";

                    }
            }
            return null;
        }
    }
}
