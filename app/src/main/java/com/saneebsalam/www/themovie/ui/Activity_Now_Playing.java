package com.saneebsalam.www.themovie.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saneebsalam.www.themovie.R;
import com.saneebsalam.www.themovie.adapter.MoviesAdapter;
import com.saneebsalam.www.themovie.model.MovieResponse_POJO;
import com.saneebsalam.www.themovie.model.Movie_POJO;
import com.saneebsalam.www.themovie.retrofit.ApiClient;
import com.saneebsalam.www.themovie.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saneeb Salam
 * on 2/19/2018.
 */

public class Activity_Now_Playing extends AppCompatActivity {

    String API_Key = "2a4e8c3abcd298440a20ebedc2f6d7f6";
    RecyclerView recyclerView;
    MoviesAdapter moviesAdapter;
    ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Now Playing");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recycleview);
        progress = findViewById(R.id.progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse_POJO> call = apiService.getLatestMovies(API_Key);
        call.enqueue(new Callback<MovieResponse_POJO>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse_POJO> call, @NonNull Response<MovieResponse_POJO> response) {
                progress.setVisibility(View.GONE);
                List<Movie_POJO> movies = response.body().getResults();
                recyclerView.setLayoutManager(new GridLayoutManager(Activity_Now_Playing.this, 2));
                moviesAdapter = new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());
                recyclerView.setAdapter(moviesAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse_POJO> call, @NonNull Throwable t) {
                // Log error here since request failed
                progress.setVisibility(View.GONE);
                Toast.makeText(Activity_Now_Playing.this, "Network Error", Toast.LENGTH_SHORT).show();
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
}
