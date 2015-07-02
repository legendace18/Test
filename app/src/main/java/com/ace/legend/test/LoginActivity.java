package com.ace.legend.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ace.legend.test.model.ResponseData;
import com.ace.legend.test.utils.Conf;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity {

    TwitterLoginButton twt_login_button;
    private EditText et_username, et_password;
    private Button btn_login;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tv_register = (TextView) findViewById(R.id.tv_register);
        twt_login_button = (TwitterLoginButton) findViewById(R.id.twt_login_button);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

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

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Conf.ENDPOINT)
                .build();

        ServerCalls api = adapter.create(ServerCalls.class);
        api.login(username, password, new retrofit.Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                pDialog.dismiss();
                if (responseData.status == 1) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (responseData.status == 0) {
                    Toast.makeText(LoginActivity.this, responseData.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getToken(TwitterSession session) {
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        String user_id = String.valueOf(session.getUserId());
        String username = session.getUserName();
        Log.d("legend.ace18", "Token" + token + ", Secret" + secret);
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
