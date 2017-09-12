package com.example.liuale.myyoutubeappdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by liuale on 9/10/17.
 */

public class PlayListOptionActivity extends AppCompatActivity
{
    private static Context context;

    private static final String[] CHANNEL_OPTIONS = {
            "UCJX2nUYKjeq3S7ZoqzSyooA",
            "UCZXqMAO7N4F8pexJGenXDAg",
            "UC-qkPmf_BY0daRGYPY3Hh7w",
            "UCNIFiHaLZkYASaWDdkC1njg",
            "UC4wKN1tlnlNVMiRPOu7qyvQ",
            "UCiCPv2sV_D3FqMRzzUFA2Fg"

    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_option);
        context = getBaseContext();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.you_tube, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_recyclerview) {
            Intent intent = new Intent(PlayListOptionActivity.getAppContext(), MainActivity.class);
            YoutubeDataProvider.setChannelId(item.toString());
            MainActivity.getAppContext().startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Context getAppContext(){
        return context;
    }
}
