package com.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MoviesDB extends SQLiteOpenHelper {

    MoviesDB(Context context) {
        super(context, "movies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE movies( title TEXT, image TEXT, rating REAL, releaseYear INTEGER, genre TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void insertMovie(MovieObject movieObject) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", movieObject.getTitle());
        values.put("image", movieObject.getImage());
        values.put("rating", movieObject.getRating());
        values.put("releaseYear", movieObject.getReleaseYear());
        values.put("genre", movieObject.getGenre());

        db.insert("movies", null, values);

        db.close();
    }

    public ArrayList<MovieObject> getMovies() {
        ArrayList<MovieObject> moviesArray = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("movies", null, null, null, null, null, "releaseYear DESC");

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            double rating = cursor.getDouble(cursor.getColumnIndex("rating"));
            int releaseYear = cursor.getInt(cursor.getColumnIndex("releaseYear"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));

            moviesArray.add(new MovieObject(title, image, rating, releaseYear, genre));
        }

        db.close();
        return moviesArray;
    }

}
