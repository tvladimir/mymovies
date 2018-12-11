package com.academy.fundamentals;

import android.app.Application;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class MyMoviesApplication extends Application {
        private static List<MovieDb> movieList;

        public static List<MovieDb>  getMovieList() {
            return movieList;
        }

        public static void setMovieList(List<MovieDb> movies) {
            movieList = movies;
        }
}
