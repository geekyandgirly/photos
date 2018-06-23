package com.luping.photos.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.luping.photos.data.AlbumListLiveData;
import com.luping.photos.model.Album;

import java.util.List;

/**
 * ViewModel responsible for fetching list of albums.
 */
public class AlbumListViewModel extends AndroidViewModel {
    private LiveData<List<Album>> albumList;
    private MutableLiveData<Album> selectedAlbumUrl = new MutableLiveData<>();

    public AlbumListViewModel(@NonNull Application application) {
        super(application);
        albumList = new AlbumListLiveData(application);
    }

    public LiveData<List<Album>> getAlbumList() {
        return albumList;
    }

    public MutableLiveData<Album> getSelectedAlbum() {
        return selectedAlbumUrl;
    }

    public void setSelectedAlbum(MutableLiveData<Album> album) {
        this.selectedAlbumUrl = album;
    }
}
