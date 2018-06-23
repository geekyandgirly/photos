package com.luping.photos.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.luping.photos.data.network.AlbumsResponse;
import com.luping.photos.data.network.GooglePhotosRestApiService;
import com.luping.photos.data.network.TokenAuthenticator;
import com.luping.photos.model.Album;

import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * LiveData wrapping list of Albums, cached in memory or fetched from the network.
 */
public class AlbumListLiveData extends LiveData<List<Album>> {
    private static final String TAG = "AlbumListLiveData";
    private GooglePhotosRestApiService service;
    private List<Album> albums;

    public AlbumListLiveData(@NonNull Application application) {
        if (albums == null) {
            TokenAuthenticator authenticator = new TokenAuthenticator(application);
            OkHttpClient client = new OkHttpClient().newBuilder().authenticator(authenticator).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://photoslibrary.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(GooglePhotosRestApiService.class);
            loadAlbums();
        }
    }

    private void loadAlbums() {
        Call<AlbumsResponse> albumListCall = service.getAlbumList();
        albumListCall.enqueue(new Callback<AlbumsResponse>() {

            @Override
            public void onResponse(Call<AlbumsResponse> call, Response<AlbumsResponse> response) {
                Log.d(TAG, "response: " + response.toString());
                if (response.isSuccessful()) {
                    AlbumsResponse albumResponse = response.body();
                    albums = albumResponse.getAlbums();
                    if(albums != null){
                        setValue(albums);
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
            public void onFailure(Call<AlbumsResponse> call, Throwable t) {
                Log.e(TAG, "loadAlbums failed: " + t.getMessage());
            }
        });
    }
}
