package com.academy.fundamentals.activitys;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.academy.fundamentals.MyMoviesApplication;
import com.academy.fundamentals.R;
import com.academy.fundamentals.adapters.MoviesAdapter;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.TmdbMovies;

public class MovieListActivity extends AppCompatActivity {

    List<MovieDb> moviesList;

    private RecyclerView moviesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);


    }

    @Override
    protected void onResume() {
        GetMoviesFromApiTask getMoviesFromApiTask = new GetMoviesFromApiTask();
        getMoviesFromApiTask.execute();
        super.onResume();
    }

    void saveMovieList(List<MovieDb> s){
        MyMoviesApplication.setMovieList(s);
    }

    private class GetMoviesFromApiTask extends AsyncTask <Void, Void, List<MovieDb> >
    {
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected List<MovieDb> doInBackground (Void...params){

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            TmdbMovies tmdbMovies = new TmdbApi(getString(R.string.TMDB_API_KEY)).getMovies();

            MovieResultsPage movieResultsPage = tmdbMovies.getPopularMovies("en", 0);
//            tmdbMovies.getVideos();

            return movieResultsPage.getResults();
        }

        @Override
        protected void onPostExecute (List<MovieDb> s){

            super.onPostExecute(s);

            saveMovieList(s);

            populateRecyclerView();
        }
    }

    private void populateRecyclerView() {

        moviesListView = findViewById(R.id.movies_list_view);

        moviesList = MyMoviesApplication.getMovieList();

        MoviesAdapter adapter = new MoviesAdapter(MovieListActivity.this, moviesList);

        moviesListView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieListActivity.this);

        moviesListView.setLayoutManager(layoutManager);

        moviesListView.setHasFixedSize(true);
    }


}
