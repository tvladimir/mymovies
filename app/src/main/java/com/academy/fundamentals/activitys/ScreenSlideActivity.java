package com.academy.fundamentals.activitys;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.academy.fundamentals.MyMoviesApplication;
import com.academy.fundamentals.R;
import com.academy.fundamentals.fragments.FragmentScreenMovieDetails;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.core.MovieResultsPage;


public class ScreenSlideActivity extends FragmentActivity {
    private ViewPager vPager;
    private PagerAdapter vPageAdapter;

    private List<MovieDb> moviesList;
    private MovieDb movie;

    private static final String MOVIE_ITEM_ID = "MOVIE_ITEM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        Intent intent = getIntent();

        if (intent != null) {
            moviesList = MyMoviesApplication.getMovieList();
            movie = moviesList.get(intent.getExtras().getInt(MOVIE_ITEM_ID, 0));
        }

        // Instantiate a ViewPager and a PagerAdapter.
        vPager = (ViewPager) findViewById(R.id.screen_slide);
        vPageAdapter = new ScreenSlideAdapter(getSupportFragmentManager());
        vPager.setAdapter(vPageAdapter);
        vPager.setCurrentItem(intent.getExtras().getInt(MOVIE_ITEM_ID, 0));

    }

    @Override
    public void onBackPressed() {
        if (vPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            vPager.setCurrentItem(vPager.getCurrentItem() - 1);
        }
    }

    public static void start(Context context, int movieItemId) {
        Intent openScreenSlideActivity = new Intent(context, ScreenSlideActivity.class);

        openScreenSlideActivity.putExtra(MOVIE_ITEM_ID, movieItemId);

        context.startActivity(openScreenSlideActivity);
    }



    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlideAdapter extends FragmentStatePagerAdapter {
        public ScreenSlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentScreenMovieDetails.newInstance(position);
        }

        @Override
        public int getCount() {
            return moviesList.size();
        }
    }

}
