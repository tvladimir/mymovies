package com.academy.fundamentals.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.MyMoviesApplication;
import com.academy.fundamentals.R;
import com.academy.fundamentals.activitys.YouTubeActivity;
import com.academy.fundamentals.transformation.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;

public class FragmentScreenMovieDetails extends Fragment {

    private Button btn_run_trailer;
    private ImageView main_movie_poster;
    private ImageView side_movie_poster;
    private TextView movie_name;
    private TextView released_date;
    private TextView overview_text;

    private String youtubeVideoId;

    List<MovieDb> moviesList;
    MovieDb movie;

    private int position;

    public static FragmentScreenMovieDetails newInstance(int position) {


//        Bundle args = new Bundle();

        FragmentScreenMovieDetails fragment = new FragmentScreenMovieDetails();
        fragment.position = position;
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        youtubeVideoId = null;

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_movie_details, container, false);
        btn_run_trailer = rootView.findViewById(R.id.btn_run_trailer);
        main_movie_poster = rootView.findViewById(R.id.main_movie_poster);
        side_movie_poster = rootView.findViewById(R.id.side_movie_poster);
        movie_name = rootView.findViewById(R.id.movie_name);
        overview_text = rootView.findViewById(R.id.overview_text);
        released_date = rootView.findViewById(R.id.released_date);

        return rootView;
    }

    private void YouTubeActivityStart(){
        if (youtubeVideoId != null){
            YouTubeActivity.start(getActivity(),youtubeVideoId);
        }

    }

    @Override
    public void onResume() {
        movie=MyMoviesApplication.getMovieList().get(position);

        GetVideoFromApiTask getVideosTask = new GetVideoFromApiTask();
        getVideosTask.execute();

        Picasso.with(getActivity())
                .load("https://image.tmdb.org/t/p/w300" + movie.getBackdropPath())
                .error(R.drawable.noprofile)
                .into(main_movie_poster);

        Picasso.with(getActivity())
                .load("https://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                .transform(new RoundedTransformation(20, 5))
                .error(R.drawable.noprofile)
                .into(side_movie_poster);

        String _Title = movie.getTitle() != null ? movie.getTitle() : "-----";
        movie_name.setText(_Title);

        String _Overview = movie.getOverview() != null ? movie.getOverview() : "-----";
        overview_text.setText(_Overview);

        String _Released_date = movie.getReleaseDate() != null ? movie.getReleaseDate() : "-----";
        released_date.setText(_Released_date);

        btn_run_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YouTubeActivityStart();
            }
        });


        super.onResume();

    }

    private class GetVideoFromApiTask extends AsyncTask<Void, Void, List<Video> >
    {
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected List<Video> doInBackground (Void...params){

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            TmdbMovies tmdbMovies = new TmdbApi(getString(R.string.TMDB_API_KEY)).getMovies();

            List<Video> videosResult = tmdbMovies.getVideos(movie.getId(), "en");

            return videosResult;
        }

        @Override
        protected void onPostExecute (List<Video> s){

            super.onPostExecute(s);

            if (s.size() > 0){
                youtubeVideoId = s.get(0).getKey();
            }


        }
    }
}
