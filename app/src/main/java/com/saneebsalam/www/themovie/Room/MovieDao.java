package com.saneebsalam.www.themovie.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.saneebsalam.www.themovie.model.Movie_POJO;

import java.util.List;

/**
 * Created by Saneeb Salam
 * on 19-02-2018.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie_POJO> getAll();

    @Query("SELECT * FROM movies where title LIKE  :title AND backdrop_path LIKE :backdrop_path")
    Movie_POJO findByName(String title, String backdrop_path);

    @Query("SELECT COUNT(*) from movies")
    int countMovie();

    @Insert
    void insertAll(Movie_POJO... movies);

    @Delete
    void delete(Movie_POJO movies);

    @Query("DELETE FROM movies")
    public void DeleteTable();
}