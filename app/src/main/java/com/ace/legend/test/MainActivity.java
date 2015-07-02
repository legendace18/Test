package com.ace.legend.test;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import static com.ace.legend.test.R.layout.activity_main;


public class MainActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;
    private FragmentManager fm;

    private int navItemId;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            navItemId = savedInstanceState.getInt("NAV_ITEM_ID");
            frag = fm.getFragment(savedInstanceState, "MAINFRAGMENT");
            fm.beginTransaction().replace(R.id.container, frag).commit();
        } else {
            navItemId = R.id.action_main;
            frag = new MainFragment();
            fm.beginTransaction().add(R.id.container, frag).commit();
        }

        setToolBar();
        setNavigationDrawer();


    }

    private void setToolBar() {
        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.getMenu().findItem(navItemId).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                MainActivity.this.invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                MainActivity.this.invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        navItemId = menuItem.getItemId();
        switch (navItemId) {
            case R.id.action_main:
                fm.beginTransaction().replace(R.id.container, new MainFragment()).commit();
                break;
            case R.id.action_fusedLocation:
                fm.beginTransaction().replace(R.id.container, new LocationFragment()).commit();
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("NAV_ITEM_ID", navItemId);
        fm.putFragment(outState, "MAINFRAGMENT", fm.findFragmentById(R.id.container));
    }


}
