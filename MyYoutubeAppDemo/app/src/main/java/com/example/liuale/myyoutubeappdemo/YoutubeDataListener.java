package com.example.liuale.myyoutubeappdemo;

import java.util.List;

/**
 * Created by liuale on 9/5/17.
 */

public interface YoutubeDataListener
{
    void dataRecieved(List<YoutubeVideoItem> youTubeDataList);
}
