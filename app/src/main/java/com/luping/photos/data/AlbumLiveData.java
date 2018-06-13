package com.luping.photos.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.luping.photos.data.network.GooglePhotosRestApiService;
import com.luping.photos.data.network.MediaItemsResponse;
import com.luping.photos.data.network.TokenAuthenticator;
import com.luping.photos.model.Album;
import com.luping.photos.model.MediaItem;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luping on 6/9/18.
 */

public class AlbumLiveData extends LiveData<List<MediaItem>> {
    private static final String TAG = "AlbumLiveData";
    private final GooglePhotosRestApiService service;

    private static final int LRU_CASH_CAP = 8;
    private LinkedHashMap<String, List<MediaItem>> cache =
            new LinkedHashMap<String, List<MediaItem>>(
                    LRU_CASH_CAP, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, List<MediaItem>> eldest) {
            return size() >= LRU_CASH_CAP;
        }
    };

    public AlbumLiveData(@NonNull Application application) {
        TokenAuthenticator authenticator = new TokenAuthenticator(application);
        OkHttpClient client = new OkHttpClient().newBuilder().authenticator(authenticator).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://photoslibrary.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GooglePhotosRestApiService.class);
    }

    public void loadMediaItems(String albumId) {
        if (cache.get(albumId) == null) {
            Log.d(TAG, "loadMediaItems loading new photos");
            Call<MediaItemsResponse> mediaItemsCall = service.getMediaItems(albumId, 20);
            mediaItemsCall.enqueue(new Callback<MediaItemsResponse>() {
                @Override
                public void onResponse(Call<MediaItemsResponse> call, Response<MediaItemsResponse> response) {
                    if (response.isSuccessful()) {
                        MediaItemsResponse mediaItemsResponse = response.body();
                        List<MediaItem> mediaItems = mediaItemsResponse.getMediaItems();
                        if (mediaItems != null) {
                            cache.put(albumId, mediaItems);
                            setValue(mediaItems);
                        }
                    } else {
                        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            Log.e(TAG,"Unauthorized: " + response.toString());
                        } else {
                            Log.e(TAG,"response code: " + response.code());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MediaItemsResponse> call, Throwable t) {
                    Log.e(TAG, "loadMediaItems failed: " + t.getMessage());
                }
            });

        }
    }
}
