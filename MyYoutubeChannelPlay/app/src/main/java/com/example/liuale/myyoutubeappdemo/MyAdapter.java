package com.example.liuale.myyoutubeappdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liuale on 8/30/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements YoutubeDataListener
{
    private List<YoutubeVideoItem> mYoutubeVideoItemList;
    private YoutubeDataProvider mYoutubeDataProvider;
    private RecyclerView mRecyclerView;
    public static final String INTENT_VIDEO_ID = "videoId";

    public MyAdapter(RecyclerView recyclerView) {
        mYoutubeDataProvider = new YoutubeDataProvider(this);
        mRecyclerView = recyclerView;
        mYoutubeDataProvider.getData();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final TextView titleTextView = holder.titleTextView;
        final TextView dateTextView = holder.dateTextView;
        final ImageView likeImageView = holder.likeImageView;
        final ImageView shareImageView = holder.shareImageView;
        final ImageView coverImageView = holder.coverImageView;

        titleTextView.setText(mYoutubeVideoItemList.get(position).getVideoTitle());
        dateTextView.setText(mYoutubeVideoItemList.get(position).getReleaseDate());
        holder.setVideoId(mYoutubeVideoItemList.get(position).getVideoId());
        likeImageView.setTag(R.drawable.ic_like);
        shareImageView.setTag(R.drawable.ic_share);

        Picasso.with(MainActivity.getAppContext()).load(mYoutubeVideoItemList.get(position).getCoverImageUrl()).into(coverImageView);

        final Context context = MainActivity.getAppContext();

        coverImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.getAppContext(), YouTubePlayActivity.class);
                intent.putExtra(INTENT_VIDEO_ID,holder.getVideoId());
                MainActivity.getAppContext().startActivity(intent);
            }
        });


        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int)likeImageView.getTag();

                if( id == R.drawable.ic_like){

                    likeImageView.setTag(R.drawable.ic_liked);
                    likeImageView.setImageResource(R.drawable.ic_liked);
                    Toast.makeText(MainActivity.getAppContext(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                }else{

                    likeImageView.setTag(R.drawable.ic_like);
                    likeImageView.setImageResource(R.drawable.ic_like);
                    Toast.makeText(MainActivity.getAppContext(),titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();

                }
            }
        });

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + context.getResources().getResourcePackageName(shareImageView.getId())
                        + '/' + "drawable" + '/' + context.getResources().getResourceEntryName((int)shareImageView.getTag()));

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                shareIntent.setType("image/jpeg");
                context.startActivity(Intent.createChooser(shareIntent, context.getResources().getText(R.string.send_to)));
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.i("AlexDebugging","I get the searchResults " + mYoutubeVideoItemList.size());
        return mYoutubeVideoItemList.size();
    }

    @Override
    public void dataRecieved(List<YoutubeVideoItem> youTubeDataList)
    {
        this.mYoutubeVideoItemList = youTubeDataList;
        mRecyclerView.setAdapter(this);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView dateTextView;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;
        public String videoId;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            dateTextView = (TextView) v.findViewById(R.id.dataTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);

        }

        public void setVideoId(String Id){
            this.videoId = Id;
        }

        public String getVideoId(){
            return videoId;
        }
    }
}