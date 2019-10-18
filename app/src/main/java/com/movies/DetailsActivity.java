package com.movies;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private MovieObject movieObject;
    private String title, image, genre;
    private int releaseYear;
    private double rating;
    private RatingBar ratingBar;

    private ImageView ivDetailsImage;
    private TextView tvDetailsTitle, tvDetailsRating, tvDetailsReleaseYear, tvDetailsGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivDetailsImage = findViewById(R.id.ivDetailsImage);
        tvDetailsTitle = findViewById(R.id.tvDetailsTitle);
        tvDetailsRating = findViewById(R.id.tvDetailsRating);
        tvDetailsReleaseYear = findViewById(R.id.tvDetailsReleaseYear);
        tvDetailsGenre = findViewById(R.id.tvDetailsGenre);
        ratingBar = findViewById(R.id.ratingBar);

        movieObject = (MovieObject) getIntent().getSerializableExtra("movieObject");

        title = movieObject.getTitle();
        image = movieObject.getImage();
        releaseYear = movieObject.getReleaseYear();
        rating = movieObject.getRating();
        genre = movieObject.getGenre();

        Picasso.with(this).load(image).into(ivDetailsImage);
        tvDetailsTitle.setText("Title: " + title);
        tvDetailsReleaseYear.setText("Release year: " + String.valueOf(releaseYear));
        tvDetailsGenre.setText("Genre: " + genre);

        tvDetailsRating.setText("Rating: " + String.valueOf(rating));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        ratingBar.setRating((float) rating);
        ratingBar.setClickable(false);
    }
}
