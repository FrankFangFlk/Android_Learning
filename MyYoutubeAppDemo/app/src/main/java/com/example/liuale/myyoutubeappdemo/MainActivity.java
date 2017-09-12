package com.example.liuale.myyoutubeappdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.android.youtube.player.YouTubeBaseActivity;

public class MainActivity extends YouTubeBaseActivity
{
    public static final int GONE = 0x00000008;
    public static final int VISIBLE = 0x00000000;

    private RecyclerView MyRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager MyLinearLayoutManager;



    private static Context context;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();

        MyRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyRecyclerView.setHasFixedSize(true);

        MyLinearLayoutManager = new LinearLayoutManager(this);
//        MyLinearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        MyRecyclerView.setLayoutManager(MyLinearLayoutManager);

        mAdapter = new MyAdapter(MyRecyclerView);



    }

    public static Context getAppContext(){
        return context;
    }


}
