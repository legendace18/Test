package com.ace.legend.test;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolBar;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        final Button btn_recyclerView = (Button) findViewById(R.id.btn_recyclerView);
        final Button btn_cardView = (Button) findViewById(R.id.btn_cardView);

        fm = getSupportFragmentManager();

        RecyclerViewFragment frag = new RecyclerViewFragment();
        fm.beginTransaction().add(R.id.container, frag).commit();
        btn_recyclerView.setBackgroundResource(R.drawable.btn_recycler_view_pressed);

        btn_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewFragment frag = new RecyclerViewFragment();
                fm.beginTransaction().replace(R.id.container, frag).commit();
                btn_recyclerView.setBackgroundResource(R.drawable.btn_recycler_view_pressed);
                btn_cardView.setBackgroundResource(R.drawable.btn_card_view);
            }
        });

        btn_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardViewFragment frag = new CardViewFragment();
                fm.beginTransaction().replace(R.id.container, frag).commit();
                btn_cardView.setBackgroundResource(R.drawable.btn_card_view_pressed);
                btn_recyclerView.setBackgroundResource(R.drawable.btn_recycler_view);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
