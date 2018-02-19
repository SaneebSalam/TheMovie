package com.saneebsalam.www.themovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saneebsalam.www.themovie.R;
import com.saneebsalam.www.themovie.Room.AppDatabase;
import com.saneebsalam.www.themovie.adapter.MoviesAdapter;
import com.saneebsalam.www.themovie.model.MovieResponse_POJO;
import com.saneebsalam.www.themovie.model.Movie_POJO;
import com.saneebsalam.www.themovie.retrofit.ApiClient;
import com.saneebsalam.www.themovie.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    String API_Key = "2a4e8c3abcd298440a20ebedc2f6d7f6";
    RecyclerView recyclerView;
    MaterialSearchView searchView;
    MoviesAdapter moviesAdapter;
    ProgressBar progress;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enables Crashlytics debugger
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        //Room DB
        mDb = AppDatabase.getAppDatabase(this); // Get an Instance of Database class

        List<Movie_POJO> movieslistDB = mDb.moviesDao().getAll();
        System.out.println("list: " + movieslistDB.size());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycleview);
        progress = findViewById(R.id.progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse_POJO> call = apiService.getTopRatedMovies(API_Key);
        call.enqueue(new Callback<MovieResponse_POJO>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse_POJO> call, @NonNull Response<MovieResponse_POJO> response) {
                progress.setVisibility(View.GONE);

                //Delete DB
                mDb.moviesDao().DeleteTable();

                List<Movie_POJO> movieslist = response.body().getResults();

                for (int i = 0; i < movieslist.size(); i++) {
                    populateWithTestData(mDb, movieslist.get(i).getId(), movieslist.get(i).getTitle(), movieslist.get(i).getBackdropPath());
                }


                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                moviesAdapter = new MoviesAdapter(movieslist, R.layout.list_item_movie, getApplicationContext());
                recyclerView.setAdapter(moviesAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse_POJO> call, @NonNull Throwable t) {
                // Log error here since request failed
                progress.setVisibility(View.GONE);

                //Data from DB
                List<Movie_POJO> movieslistDB = mDb.moviesDao().getAll();

                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                moviesAdapter = new MoviesAdapter(movieslistDB, R.layout.list_item_movie, getApplicationContext());
                recyclerView.setAdapter(moviesAdapter);
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                System.out.println("Query: " + query);
                moviesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                moviesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular) {
            startActivity(new Intent(MainActivity.this, Activity_Popular.class));
        } else if (id == R.id.nav_now_playing) {
            startActivity(new Intent(MainActivity.this, Activity_Now_Playing.class));
        } else if (id == R.id.nav_upcomings) {
            startActivity(new Intent(MainActivity.this, Activity_Upcomings.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static Movie_POJO addUser(final AppDatabase db, Movie_POJO user) {
        db.moviesDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(AppDatabase db, Integer id, String title, String backdropPath) {
        Movie_POJO user = new Movie_POJO(id, title, backdropPath);
        user.setId(id);
        user.setTitle(title);
        user.setBackdropPath(backdropPath);
        addUser(db, user);
    }
}
