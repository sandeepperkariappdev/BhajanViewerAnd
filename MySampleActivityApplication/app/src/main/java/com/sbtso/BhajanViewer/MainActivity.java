package com.sbtso.BhajanViewer;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.sbtso.BhajanViewer.BhajanXmlParser.Bhajan;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.DbxClientV2;

import org.xmlpull.v1.XmlPullParserException;

public class MainActivity extends ActionBarActivity{

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navigationMenu;
    Toolbar toolbar;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        navigationMenu = getResources().getStringArray(R.array.menus_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();



        mNavItems.add(new NavItem("Home"));
        mNavItems.add(new NavItem("Bhajans"));
        mNavItems.add(new NavItem("Aarathi"));

        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

       // viewPager = (ViewPager) findViewById(R.id.pager);
       // mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

       // viewPager.setAdapter(mAdapter);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
         // set up the drawer's list view with items and click listener
                      //  mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                        //        R.layout.drawer_list_item, navigationMenu));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle(mTitle);
                // toolbartitle.setText(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        if (savedInstanceState == null) {
            selectItem(0);
        }


        DbxRequestConfig config = new DbxRequestConfig("dropbox/SaiTempleBhajanViewer","en_US");
        new DownloadFile(getApplicationContext(),new DbxClientV2(config, getAccessToken()),new DownloadFile.Callback(){
            @Override
            public void onDownloadComplete(File result){
//                InputStream inputStream = null;
//                try{
//                    if(result != null){
////                       inputStream= new FileInputStream(result);
////                        String xmlStringResp = processXmlResponse(inputStream);
////                        Log.e("File Read", xmlStringResp );
////                        Toast.makeText(getBaseContext(), xmlStringResp,Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch (IOException e){
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onError(Exception e){
                Log.e("FileError", "Failed to download file.", e);
            }
        }).execute();
    }


    private String getAccessToken(){
        // String accessToken = Auth.getOAuth2Token();
        /*if(accessToken != null){
            // Store access token in the shared preference.
            SharedPreferences prefs = getSharedPreferences("com.sbtso.BhajanViewer", Context.MODE_PRIVATE);
            prefs.edit().putString("access-token", accessToken).apply();
        }*/
        String accessToken = "xE1p44spcEkAAAAAAAACUJIszUaA4Q4-oIuNJFmZ-Wy_Wwspe0KES9vqpYr-aYI5";
        return  accessToken;
    }

    private String processXmlResponse(InputStream inputStream){
        List<Bhajan> bhajanList = null;
        String title = null;
        String url = null;
        String summary = null;
        Calendar rightNow = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");
        StringBuilder xmlBuilder = new StringBuilder();

        try {
            BhajanXmlParser bhajanXmlParser = new BhajanXmlParser();
            bhajanList = bhajanXmlParser.parseBhajan(inputStream);

        } catch(IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        for(Bhajan bhajan: bhajanList){
            xmlBuilder.append("SingerName " +bhajan.singerName);
            xmlBuilder.append("BhajanText " +bhajan.bhajanText);
            xmlBuilder.append("NextSinger " +bhajan.nextSinger);
            xmlBuilder.append("NextBhajan " +bhajan.nextBhajan);
            xmlBuilder.append("BhajanName " +bhajan.bhajanName);
        }
        return xmlBuilder.toString();
    }

    private void selectItem(int position) {
        switch(position){
            case 1:
                Intent intentBhajan = new Intent(MainActivity.this, BhajanActivity.class);
                startActivity(intentBhajan);
                finish();
                break;
            case 2:
                Intent intentAarathi = new Intent(MainActivity.this, AarathiActivity.class);
                startActivity(intentAarathi);
                finish();
                break;
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu items for use in the action bar
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle presses on the action bar items
        switch (item.getItemId()) {


        }
        return false;
    }
}
