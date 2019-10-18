package com.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies;
        MoviesAdapter adapter;
        MoviesDB moviesDB;

        rvMovies = findViewById(R.id.rvMovies);

        moviesDB = new MoviesDB(this);

        adapter = new MoviesAdapter(MainActivity.this, moviesDB.getMovies());
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

    }
}
