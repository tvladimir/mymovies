package com.academy.fundamentals.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.R;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

import com.academy.fundamentals.activitys.ScreenSlideActivity;
import com.academy.fundamentals.transformation.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static List<MovieDb> movies;
    private Context context;

    private int lastPosition = -1;

    public MoviesAdapter(Context ctx, List<MovieDb> moviesList) {
        context = ctx;
        movies = moviesList;
    }

    /**
     * Provides a direct reference to each of the views within a data item
     * Used to cache the views within the item layout for fast access
     */
    public class ViewHolder extends RecyclerView.ViewHolder /* implements View.OnClickListener */{

        public TextView movieTitle;
        public TextView movieOverview;
        public ImageView moviePosterImage;
        public ConstraintLayout movieItem;

        public ViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieOverview = itemView.findViewById(R.id.movieOverview);
            moviePosterImage = itemView.findViewById(R.id.moviePosterImage);
            movieItem = itemView.findViewById(R.id.movie_item);
//            itemView.setOnClickListener(view1->{
//
//            }); //this is necessary to setOnClickListener of RecyclerView of Hourly Button

        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//
//            MovieDb clickedMovie = movies.get(position);
//
//            DetailsActivity.start(context,clickedMovie);
//
//        }
    }

    //This method inflates a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the custom layout
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_list_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    // Populates data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MovieDb movie = movies.get(position);

        String _Title = movie.getTitle() != null ? movie.getTitle() : "Empty Text";
        String _Overview = movie.getOverview() != null ? movie.getOverview() : "Empty Text";

        holder.movieTitle.setText(_Title);
        holder.movieOverview.setText(_Overview);
        holder.movieItem.setOnClickListener(v -> {
            int _position = movies.indexOf(movie);
            ScreenSlideActivity.start( context, _position);
        });
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                .transform(new RoundedTransformation(20, 5))
                .error(R.drawable.noprofile)
                .into(holder.moviePosterImage);

        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }


}