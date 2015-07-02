package com.ace.legend.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends ActionBarActivity {

    private Toolbar toolBar;
    private CollapsingToolbarLayout collapsingToolbar;

    String name, photo, category, description, price;

    public static final String PHOTO_BASE_URL = "http://services.hanselandpetal.com/photos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        photo = intent.getStringExtra("PHOTO");
        name = intent.getStringExtra("NAME");
        description = intent.getStringExtra("INSTRUCTIONS");
        category = intent.getStringExtra("CATEGORY");
        price = intent.getStringExtra("PRICE");

        setCollapsingAppBar();

        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_description = (TextView) findViewById(R.id.tv_description);
        TextView tv_category = (TextView) findViewById(R.id.tv_category);
        TextView tv_price = (TextView) findViewById(R.id.tv_price);

        tv_name.setText(name);
        tv_category.setText(category);
        tv_description.setText(description);
        tv_price.setText(price);

    }

    private void setCollapsingAppBar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(name);

        ImageView img_backdrop = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(PHOTO_BASE_URL + photo).fit().into(img_backdrop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
