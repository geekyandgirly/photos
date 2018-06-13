package com.luping.photos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luping on 6/9/18.
 */

public class MediaItem {
    @SerializedName("id")
    private String id;

    @SerializedName("description")
    private String description;

    @SerializedName("baseUrl")
    private String baseUrl;

    @SerializedName("mimeType")
    private String mimeType;

    public MediaItem(String id, String description, String baseUrl, String mimeType) {
        this.id = id;
        this.description = description;
        this.baseUrl = baseUrl;
        this.mimeType = mimeType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
