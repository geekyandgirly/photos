package com.luping.photos.data.network;

import com.google.gson.annotations.SerializedName;
import com.luping.photos.model.Album;

import java.util.List;

/**
 * Created by luping on 6/10/18.
 */

public class AlbumsResponse {
    @SerializedName("nextPageToken")
    private String nextPageToken;

    @SerializedName("albums")
    private List<Album> albums;

    public AlbumsResponse(String nextPageToken, List<Album> albums) {
        this.nextPageToken = nextPageToken;
        this.albums = albums;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
