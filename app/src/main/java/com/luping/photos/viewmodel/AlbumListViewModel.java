package com.luping.photos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.luping.photos.data.network.AlbumsResponse;
import com.luping.photos.data.network.NetworkService;
import com.luping.photos.model.Album;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel responsible for fetching list of albums.
 */
public class AlbumListViewModel extends AndroidViewModel {
    private static final String TAG = "AlbumListViewModel";
    private final NetworkService service = new NetworkService();

    private MutableLiveData<List<Album>> albumList = new MutableLiveData<>();
    private SingleLiveEvent<Album> selectedAlbumUrl = new SingleLiveEvent<>();

    public AlbumListViewModel(@NonNull Application application) {
        super(application);
        Call<AlbumsResponse> albumsListCall = service.getApi(application).getAlbumList();
        albumsListCall.enqueue(new Callback<AlbumsResponse>() {
            @Override
            public void onResponse(Call<AlbumsResponse> call, Response<AlbumsResponse> response) {
                if (response.isSuccessful()) {
                    AlbumsResponse albumsResponse = response.body();
                    List<Album> albums = albumsResponse.getAlbums();
                    if (albums != null) {
                        albumList.setValue(albums);
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

    public LiveData<List<Album>> getAlbumList() {
        return albumList;
    }

    public SingleLiveEvent<Album> getSelectedAlbum() {
        return selectedAlbumUrl;
    }
}
