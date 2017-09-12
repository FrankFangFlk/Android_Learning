package com.example.liuale.videodemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set the video
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath("https://youtu.be/0oY1WBbXQoQ");

        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);


        //Set the audio
        MediaPlayer mediaPlayer = new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource("http://soundbible.com/grab.php?id=2186&type=wav");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Play both the video and Audio
        mediaPlayer.start();
        videoView.start();
    }
}
