package com.ace.legend.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;


public class LoginActivity extends Activity {

    TwitterLoginButton twt_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        twt_login_button = (TwitterLoginButton) findViewById(R.id.twt_login_button);
        twt_login_button.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a
                // TwitterSession for making API calls
                Log.d("legend.ace18", "login success");
                //requestEmail(result.data);
                getToken(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d("legend.ace18", "login failed");
            }
        });
    }

    private void getToken(TwitterSession session) {
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        String user_id = String.valueOf(session.getUserId());
        String username = session.getUserName();
        Log.d("legend.ace18", "Token"+token+", Secret" + secret);
        Log.d("legend.ace18", "ID " + user_id + ", Username" + username);

        Twitter.getApiClient().getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                User user = userResult.data;
                String name = user.name;
                String pic = user.profileImageUrl;
                Log.d("legend.ace18", "Name = " + name);
                Log.d("legend.ace18", "Pic = " + pic);
            }

            @Override
            public void failure(TwitterException e) {
            }
        });
    }

  /*  private void requestEmail(TwitterSession session) {
        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback() {
            @Override
            public void success(Result result) {
                Log.d("legend.ace18", "email success");
                Toast.makeText(LoginActivity.this, "Email " + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d("legend.ace18", "email failed");
            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twt_login_button.onActivityResult(requestCode, resultCode,
                data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }

}
