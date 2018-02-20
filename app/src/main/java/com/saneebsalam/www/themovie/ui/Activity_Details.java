package com.saneebsalam.www.themovie.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.saneebsalam.www.themovie.R;
import com.saneebsalam.www.themovie.model.MovieResponse_POJO;
import com.saneebsalam.www.themovie.retrofit.ApiClient;
import com.saneebsalam.www.themovie.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Details extends AppCompatActivity {

    String API_Key = "2a4e8c3abcd298440a20ebedc2f6d7f6";
    TextView desc, longdesc;
    CollapsingToolbarLayout collapsingToolbar;
    int ID;
    ProgressBar progress;
    SimpleDraweeView image;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details);

        intent = getIntent();


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        progress = findViewById(R.id.progress);
        // Set title of Detail page
        collapsingToolbar.setTitle(intent.getStringExtra("Name"));

        ID = intent.getIntExtra("ID", 0);

        desc = findViewById(R.id.desc);
        longdesc = findViewById(R.id.longdesc);
        image = findViewById(R.id.image);

        //Getting getMovieDetails
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse_POJO> call = apiService.getMovieDetails(ID, API_Key);
        call.enqueue(new Callback<MovieResponse_POJO>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse_POJO> call, @NonNull Response<MovieResponse_POJO> response) {
                progress.setVisibility(View.GONE);

                String img = response.body().getBackdrop_path();

                Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/" + img);
                image.setImageURI(uri);

                desc.setText(response.body().getOverview());

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse_POJO> call, @NonNull Throwable t) {
                // Log error here since request failed
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}

