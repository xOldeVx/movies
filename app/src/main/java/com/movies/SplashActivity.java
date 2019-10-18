package com.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class SplashActivity extends AppCompatActivity {

    private MovieTask movieTask;
    private MoviesDB moviesDB;
    private LinearLayout containerError;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pbLoading = findViewById(R.id.pbLoading);
        containerError = findViewById(R.id.containerError);

        moviesDB = new MoviesDB(this);

        if (moviesDB.getMovies() != null && moviesDB.getMovies().size() > 0) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            movieTask = new MovieTask();
            movieTask.execute();
        }

        findViewById(R.id.btnReload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTask = new MovieTask();
                movieTask.execute();
                pbLoading.setVisibility(View.VISIBLE);
                containerError.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieTask != null)
            movieTask.cancel(true);
    }


    public class MovieTask extends AsyncTask<Void, Void, ArrayList<MovieObject>> {

        @Override
        protected ArrayList<MovieObject> doInBackground(Void... voids) {

            HttpsURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<MovieObject> movieArray = new ArrayList<>();

            try {
                URL url = new URL("https://api.androidhive.info/json/movies.json");
                connection = (HttpsURLConnection) url.openConnection();
                if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = reader.readLine();
                }
                JSONArray rootArr = new JSONArray(stringBuilder.toString());
                for (int i = 0; i < rootArr.length(); i++) {
                    String title = rootArr.getJSONObject(i).getString("title");
                    String image = rootArr.getJSONObject(i).getString("image");
                    double rating = rootArr.getJSONObject(i).getDouble("rating");
                    int releaseYear = rootArr.getJSONObject(i).getInt("releaseYear");
                    JSONArray genreArray = rootArr.getJSONObject(i).getJSONArray("genre");
                    String genreString = String.valueOf(genreArray).replace("[", "")
                            .replace("]", "")
                            .replace("\"", "")
                            .replace(",", ", ");
                    moviesDB.insertMovie(new MovieObject(title, image, rating, releaseYear, genreString));
                    movieArray.add(new MovieObject(title, image, rating, releaseYear, genreString));
                }
            } catch (
                    MalformedURLException e) {
                e.printStackTrace();
            } catch ( JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return movieArray;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieObject> movieArray) {
            super.onPostExecute(movieArray);
            if (movieArray != null) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                pbLoading.setVisibility(View.GONE);
                containerError.setVisibility(View.VISIBLE);
            }
        }
    }
}
