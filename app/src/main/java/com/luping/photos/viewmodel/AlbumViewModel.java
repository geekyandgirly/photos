package com.luping.photos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.luping.photos.data.AlbumLiveData;
import com.luping.photos.model.MediaItem;

import java.util.List;

/**
 * ViewModel responsible for fetching photos of an given albumId.
 */
public class AlbumViewModel extends AndroidViewModel {
    private AlbumLiveData photoList;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        photoList = new AlbumLiveData(application);
    }

    public void loadAlbum(String albumId) {
        photoList.loadMediaItems(albumId);
    }

    public LiveData<List<MediaItem>> getAlbum() {
        return photoList;
    }
}
