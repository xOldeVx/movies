package com.movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    private Context context;
    private ArrayList<MovieObject> movieArray;

    MoviesAdapter(Context context, ArrayList<MovieObject> movieArray) {
        this.context = context;
        this.movieArray = movieArray;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_movie, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        movieHolder.bind(movieArray.get(i));
    }


    @Override
    public int getItemCount() {
        return movieArray.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private ImageView ivMovieImage;
        private TextView tvMovieTitle;
        private MovieObject movieObject;

        MovieHolder(final View view) {
            super(view);

            ivMovieImage = view.findViewById(R.id.ivMovieImage);
            tvMovieTitle = view.findViewById(R.id.tvMovieTitle);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("movieObject", movieObject);
                    context.startActivity(intent);
                }
            });
        }

        void bind(MovieObject movieObject) {
            this.movieObject = movieObject;
            Picasso.with(context).load(movieObject.getImage()).into(ivMovieImage);
            tvMovieTitle.setText(movieObject.getTitle());
        }
    }
}
