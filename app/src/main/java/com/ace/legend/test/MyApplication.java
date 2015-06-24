package com.ace.legend.test;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by rohan on 6/15/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(getString(R.string.twitter_consumer_key), getString(R.string.twitter_consumer_secret_key));
        Fabric.with(this, new Twitter(authConfig));
    }
}
