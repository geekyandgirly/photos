package com.luping.photos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Data representing an album.
 */
public class Album {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("productUrl")
    private String productUrl;

    @SerializedName("coverPhotoBaseUrl")
    private String coverPhotoBaseUrl;

    @SerializedName("isWriteable")
    private boolean isWriteable;

    @SerializedName("totalMediaItems")
    private int totalMediaItems;

    public Album(String id, String title, String productUrl, String coverPhotoBaseUrl, boolean isWriteable, int totalMediaItems) {
        this.id = id;
        this.title = title;
        this.productUrl = productUrl;
        this.coverPhotoBaseUrl = coverPhotoBaseUrl;
        this.isWriteable = isWriteable;
        this.totalMediaItems = totalMediaItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getCoverPhotoBaseUrl() {
        return coverPhotoBaseUrl;
    }

    public void setCoverPhotoBaseUrl(String coverPhotoBaseUrl) {
        this.coverPhotoBaseUrl = coverPhotoBaseUrl;
    }

    public boolean isWriteable() {
        return isWriteable;
    }

    public void setWriteable(boolean writeable) {
        isWriteable = writeable;
    }

    public int getTotalMediaItems() {
        return totalMediaItems;
    }

    public void setTotalMediaItems(int totalMediaItems) {
        this.totalMediaItems = totalMediaItems;
    }
}
