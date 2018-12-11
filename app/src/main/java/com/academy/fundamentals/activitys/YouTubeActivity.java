package com.academy.fundamentals.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_ID = "YOUTUBE_ID";


    private static final String YOUTUBE_API_KEY = "AIzaSyBdchAnsT9Y-AaI_twMLy2Q5OUxyhp7owY";
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;



    public static void start(Context context, String video_id) {
        Intent openYouTubeActivity = new Intent(context, YouTubeActivity.class);
        openYouTubeActivity.putExtra(YOUTUBE_ID, video_id);
        context.startActivity(openYouTubeActivity);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        String video_id = getIntent().getStringExtra(YOUTUBE_ID);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Toast.makeText(YouTubeActivity.this, "Starting Play", Toast.LENGTH_LONG).show();
                youTubePlayer.loadVideo(video_id);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult
                    youTubeInitializationResult) {
                Toast.makeText(YouTubeActivity.this, "YouTube Player ex...", Toast.LENGTH_LONG).show();
            }
        };

        youTubePlayerView.initialize(YOUTUBE_API_KEY, onInitializedListener);
    }


}
