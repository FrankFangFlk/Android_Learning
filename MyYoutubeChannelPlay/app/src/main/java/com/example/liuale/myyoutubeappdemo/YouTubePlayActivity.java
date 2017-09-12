package com.example.liuale.myyoutubeappdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by liuale on 8/30/17.
 */

public class YouTubePlayActivity extends YouTubeBaseActivity
{
    public static final String DEVELOPER_KEY = "AIzaSyD1MYeUEVB1nMuqNt7BmjXx5RvggMpJGMc";
    public static final String INTENT_VIDEO_ID = "videoId";
    public static String videoId = null;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtubeplayer);

        Intent intent = getIntent();
        videoId = intent.getStringExtra(INTENT_VIDEO_ID);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errReason){
                Log.i("AlexDebugging","YouTubePlayer initialized failed");
            }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored){
                player.loadVideo(videoId);
            }
        };
        mYouTubePlayerView.initialize(DEVELOPER_KEY,mOnInitializedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RECOVERY_DIALOG_REQUEST){
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, mOnInitializedListener);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}
