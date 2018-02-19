package com.saneebsalam.www.themovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.saneebsalam.www.themovie.R;

/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */

public class Activity_Splash extends AppCompatActivity {

    Animation zoomin;
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        title = findViewById(R.id.title);
        zoomin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_zoom_in);

        title.startAnimation(zoomin);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Activity_Splash.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
