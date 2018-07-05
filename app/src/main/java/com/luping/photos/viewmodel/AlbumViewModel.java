package com.luping.photos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.luping.photos.data.network.MediaItemsResponse;
import com.luping.photos.data.network.NetworkApi;
import com.luping.photos.data.network.NetworkService;
import com.luping.photos.model.MediaItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel responsible for fetching photos of an given albumId.
 */
public class AlbumViewModel extends AndroidViewModel {
    private static final String TAG = "AlbumViewModel";
    private final NetworkService service = new NetworkService();
    private final NetworkApi api;

    private MutableLiveData<List<MediaItem>> photoList = new MutableLiveData<>();
    private SingleLiveEvent<Integer> selectedIndex = new SingleLiveEvent<>();

    private static final int LRU_CASH_CAP = 5; // 5 albums
    private final LinkedHashMap<String, List<MediaItem>> cache =
            new LinkedHashMap<String, List<MediaItem>>(LRU_CASH_CAP, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, List<MediaItem>> eldest) {
                    return size() >= LRU_CASH_CAP;
                }
            };

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        api = service.getApi(application);
    }

    public void loadAlbum(String albumId) {
        if (!cache.containsKey(albumId)) {

            Call<MediaItemsResponse> call = api.getMediaItems(albumId, 20);
            call.enqueue(new Callback<MediaItemsResponse>() {
                @Override
                public void onResponse(Call<MediaItemsResponse> call, Response<MediaItemsResponse> response) {
                    if (response.isSuccessful()) {
                        MediaItemsResponse res = response.body();
                        List<MediaItem> photos = res.getMediaItems();
                        if (photos != null) {
                            photoList.setValue(photos);
                            cache.put(albumId, photos);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MediaItemsResponse> call, Throwable t) {
                    Log.d(TAG, "loadAlbum failed: " + t.getStackTrace());
                }
            });
        } else {
            photoList.setValue(cache.get(albumId));
        }
    }

    public LiveData<List<MediaItem>> getAlbum() {
        return photoList;
    }

    public SingleLiveEvent<Integer> getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        selectedIndex.setValue(index);
    }
}
