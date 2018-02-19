package com.saneebsalam.www.themovie.retrofit;

import com.saneebsalam.www.themovie.model.MovieResponse_POJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse_POJO> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieResponse_POJO> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse_POJO> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse_POJO> getLatestMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse_POJO> getUpcomingMovies(@Query("api_key") String apiKey);

}
