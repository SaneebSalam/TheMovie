package com.saneebsalam.www.themovie.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */

public class MovieResponse_POJO {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie_POJO> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("backdrop_path")
    private String backdrop_path;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @SerializedName("overview")
    private String overview;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public List<Movie_POJO> getResults() {
        return results;
    }

}
