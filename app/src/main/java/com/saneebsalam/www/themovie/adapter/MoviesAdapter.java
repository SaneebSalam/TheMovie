package com.saneebsalam.www.themovie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.saneebsalam.www.themovie.R;
import com.saneebsalam.www.themovie.model.Movie_POJO;
import com.saneebsalam.www.themovie.ui.Activity_Details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie_POJO> movies;
    private int rowLayout;
    private Context context;
    private List<Movie_POJO> mFilteredList;

    public MoviesAdapter(List<Movie_POJO> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        mFilteredList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.movieTitle.setText(mFilteredList.get(position).getTitle());
        Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/" + mFilteredList.get(position).getBackdropPath());
        holder.image.setImageURI(uri);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Details.class);
                intent.putExtra("ID", mFilteredList.get(position).getId());
                intent.putExtra("Name", mFilteredList.get(position).getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = movies;
                } else {

                    ArrayList<Movie_POJO> filteredList = new ArrayList<>();

                    for (Movie_POJO moviePOJO : movies) {

                        if (moviePOJO.getTitle().toLowerCase().contains(charString)) {

                            filteredList.add(moviePOJO);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Movie_POJO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        //        LinearLayout moviesLayout;
        TextView movieTitle;
        SimpleDraweeView image;


        MovieViewHolder(View v) {
            super(v);
            movieTitle = v.findViewById(R.id.name);
            image = v.findViewById(R.id.image);

        }
    }
}