package com.saneebsalam.www.themovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.saneebsalam.www.themovie.MyApplication;
import com.saneebsalam.www.themovie.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Saneeb Salam
 * on 20-02-2018.
 */

public class Activity_Login extends AppCompatActivity {

    private static final String EMAIL = "email";
    CallbackManager callbackManager;
    String personName, Email, personPhotoUrl;
    Button guest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Login");

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.login_button);
        guest = findViewById(R.id.guest);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);    

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        RequestDataFacebook();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.setsharedprefString("Name", getString(R.string.app_name));
                MyApplication.setsharedprefString("Email", "");
                MyApplication.setsharedprefString("Profile", "");

                startActivity(new Intent(Activity_Login.this, MainActivity.class));
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void RequestDataFacebook() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {

                    if (json != null) {
                        personName = json.getString("name");
                        MyApplication.setsharedprefString("Name", personName);
                        if (json.has("email")) {
                            Email = json.getString("email");
                            MyApplication.setsharedprefString("Email", Email);
                        }
                        personPhotoUrl = "https://graph.facebook.com/" + json.getString("id") + "/picture?type=large";
                        MyApplication.setsharedprefString("Profile", personPhotoUrl);
                        MyApplication.setsharedprefBoolean("IsLogin", true);

                        startActivity(new Intent(Activity_Login.this, MainActivity.class));
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
