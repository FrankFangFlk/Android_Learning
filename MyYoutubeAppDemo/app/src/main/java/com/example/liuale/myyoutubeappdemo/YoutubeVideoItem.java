package com.example.liuale.myyoutubeappdemo;

/**
 * Created by liuale on 8/31/17.
 */

public class YoutubeVideoItem
{
    private String videoTitle;
    private String releaseDate;
    private String videoId;
    private String coverImageUrl;

    public YoutubeVideoItem(String videoTitle, String releaseDate, String videoId, String coverImageUrl){
        this.videoTitle = videoTitle;
        this.releaseDate = releaseDate;
        this.videoId = videoId;
        this.coverImageUrl = coverImageUrl;
    }

    public void setVideoTitle(String videoTitle){
        this.videoTitle = videoTitle;
    }

    public String getVideoTitle(){
        return videoTitle;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public void setVideoId(String videoId){
        this.videoId = videoId;
    }

    public String getVideoId(){
        return videoId;
    }

    public void setCoverImageUrl(String coverImageUrl){
        this.coverImageUrl = coverImageUrl;
    }

    public String getCoverImageUrl(){
        return coverImageUrl;
    }
}
