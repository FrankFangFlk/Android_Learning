package com.example.liuale.myyoutubeappdemo;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuale on 8/30/17.
 */

public class YoutubeDataProvider
{
    private static final long Return_Item_NUMBER = 18;
    private static YouTube youtubeData;
    private static String CHANNEL_ID = "UCJX2nUYKjeq3S7ZoqzSyooA";
    private static YouTube.Search.List searchList;
    private YoutubeDataListener mListener;

    public YoutubeDataProvider(YoutubeDataListener mListener){

        NetHttpTransport netHttpTransport = new NetHttpTransport();
        JacksonFactory jacksonFactory = new JacksonFactory();
        HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer()
        {
            @Override
            public void initialize(HttpRequest request) throws IOException
            {
                //No-op
            }
        };

        try {
            this.mListener = mListener;
            youtubeData = new YouTube.Builder(netHttpTransport, jacksonFactory, httpRequestInitializer).setApplicationName("youtube-data-api").build();

            ResourceId resourceId = new ResourceId();
            resourceId.setChannelId(CHANNEL_ID);
            resourceId.setKind("youtube#channel");

            searchList = youtubeData.search().list("id, snippet");

            searchList.setKey(YouTubePlayActivity.DEVELOPER_KEY);
            searchList.setChannelId(CHANNEL_ID);
            searchList.setType("video");
            searchList.setOrder("date");
            searchList.setFields("items(id/videoId,snippet/title,snippet/publishedAt,snippet/thumbnails/medium/url)");
            searchList.setMaxResults(Return_Item_NUMBER);
        } catch (GoogleJsonResponseException e) {
            System.err.println("Service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static YouTube.Search.List getSearchList(){
        return YoutubeDataProvider.searchList;
    }

    public static void setChannelId(String channelId){
        CHANNEL_ID = channelId;
    }

    public void getData() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    final SearchListResponse searchResponse;
                    searchResponse = searchList.execute();
                    List<SearchResult> searchResultList = searchResponse.getItems();
                    final List<YoutubeVideoItem> youtubeDataList = new ArrayList<YoutubeVideoItem>();

                    if (searchResultList != null) {
                        int listSize = searchResultList.size();
                        if(listSize <= 0){
                            System.out.println("The list is empty");
                        }

                        for(int i = 0; i < listSize;i++){
                            SearchResult currentVideo = searchResultList.get(i);
                            ResourceId resourceId = currentVideo.getId();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");

                            youtubeDataList.add(new YoutubeVideoItem(currentVideo.getSnippet().getTitle(),
                                    simpleDateFormat.format(new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss").parse(currentVideo.getSnippet().getPublishedAt().toString())).toString(),
                                    resourceId.getVideoId(),currentVideo.getSnippet().getThumbnails().getMedium().getUrl()));
                        }
                    }

                    Handler mHandler = new Handler(Looper.getMainLooper());

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.dataRecieved(youtubeDataList);
                        }
                    });
                } catch (IOException e) {
                    System.err.println("IO error: " + e.getCause() + " : " + e.getMessage());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

    }

}
