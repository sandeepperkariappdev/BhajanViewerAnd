package com.sbtso.BhajanViewer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS;
import java.util.ArrayList;

/**
 * Created by sandeepperkari on 7/27/16.
 */
public class AarathiActivity  extends ActionBarActivity implements android.app.ActionBar.TabListener, android.support.v7.app.ActionBar.TabListener{

private DrawerLayout mDrawerLayout;
private ViewPager viewPager;
private AarathiTabsPagerAdapter mAdapter;
private ListView mDrawerList;
private ActionBarDrawerToggle mDrawerToggle;


private CharSequence mDrawerTitle;
private CharSequence mTitle;
private String[]navigationMenu;
        Toolbar toolbar;
        ArrayList<NavItem>mNavItems=new ArrayList<NavItem>();
        Context context=this;
// Tab titles
private String[]tabs={"kakad","Madyana", "Dhoop","Shej"};
// int[] iconList = new int[]{R.drawable.about, R.drawable.about,R.drawable.about,R.drawable.about};

@Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aarathi);
        mTitle=mDrawerTitle=getTitle();
        navigationMenu=getResources().getStringArray(R.array.menus_array);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        //Log.e("hgjhdbjhioh", "ghugsdbg===========" + actionBar);

        mNavItems.add(new NavItem("Home"));
        mNavItems.add(new NavItem("Bhajans"));
        mNavItems.add(new NavItem("Aarathi"));

        DrawerListAdapter adapter=new DrawerListAdapter(this,mNavItems);
        mDrawerList.setAdapter(adapter);

        viewPager=(ViewPager)findViewById(R.id.pager);
        mAdapter=new AarathiTabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setNavigationMode(NAVIGATION_MODE_TABS);

        int a=0;
        // Adding Tabs
        for(String tab_name:tabs){
        actionBar.addTab(actionBar.newTab().setText(tab_name)
        .setTabListener(this));
        actionBar=getSupportActionBar();

        a++;

        /**
         * on swiping the viewpager make respective tab selected
         */
        final android.support.v7.app.ActionBar ActionBar=actionBar;
                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

                @Override
                public void onPageSelected(int position){
                    // on changing the page
                    // make respected tab selected
                    ActionBar.setSelectedNavigationItem(position);
                }

                @Override
                public void onPageScrolled(int arg0,float arg1,int arg2){
                }

                @Override
                public void onPageScrollStateChanged(int arg0){
                }
        });


                       /* // set up the drawer's list view with items and click listener
                        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                                R.layout.drawer_list_item, navigationMenu));*/
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle=new ActionBarDrawerToggle(
                                this,                  /* host Activity */
                                mDrawerLayout,         /* DrawerLayout object */
                                toolbar,  /* nav drawer image to replace 'Up' caret */
                                R.string.drawer_open,  /* "open drawer" description for accessibility */
                                R.string.drawer_close  /* "close drawer" description for accessibility */
                                ){
                                    public void onDrawerClosed(View view){
                                            //getSupportActionBar().setTitle(mTitle);
                                            // toolbartitle.setText(mTitle);
                                            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                                    }

                                    public void onDrawerOpened(View drawerView){
                                            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                                            mDrawerList.bringToFront();
                                            mDrawerLayout.requestLayout();
                                    }
                                };
                mDrawerLayout.setDrawerListener(mDrawerToggle);

                mDrawerToggle.syncState();

                    if(savedInstanceState==null){
                            selectItem(0);
                    }

                }

        }
        private void selectItem(int position){
            switch(position){
                case 0:
                    Intent intentHome = new Intent(AarathiActivity.this, HomeActivity.class);
                    startActivity(intentHome);
                    finish();
                    break;
                case 1:
                    Intent intentBhajan = new Intent(AarathiActivity.this, BhajanActivity.class);
                    startActivity(intentBhajan);
                    finish();
                    break;
            }
        }
        @Override
        public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){

        }

        @Override
        public void onTabSelected(android.app.ActionBar.Tab tab,android.app.FragmentTransaction ft){
                viewPager.setCurrentItem(tab.getPosition());

        }

        @Override
        public void onTabUnselected(android.app.ActionBar.Tab tab,android.app.FragmentTransaction ft){

        }

        @Override
        public void onTabReselected(android.app.ActionBar.Tab tab,android.app.FragmentTransaction ft){

        }

        @Override
        public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){
                viewPager.setCurrentItem(tab.getPosition());
                // Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                }

        private class DrawerItemClickListener implements AdapterView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);

            }

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
