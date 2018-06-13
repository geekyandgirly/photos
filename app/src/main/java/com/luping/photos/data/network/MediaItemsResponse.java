package com.luping.photos.data.network;

import com.google.gson.annotations.SerializedName;
import com.luping.photos.model.MediaItem;

import java.util.List;

/**
 * Created by luping on 6/11/18.
 */

public class MediaItemsResponse {
    @SerializedName("nextPageToken")
    private String nextPageToken;

    @SerializedName("mediaItems")
    private List<MediaItem> mediaItems;

    public MediaItemsResponse(String nextPageToken, List<MediaItem> albums) {
        this.nextPageToken = nextPageToken;
        this.mediaItems = albums;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }
}
