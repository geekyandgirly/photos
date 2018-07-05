package com.luping.photos.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luping.photos.R;
import com.luping.photos.model.Album;
import com.luping.photos.model.MediaItem;
import com.luping.photos.viewmodel.AlbumViewModel;

/**
 * Fragment displaying photos in an Album.
 */
public class AlbumFragment extends Fragment {
    public static String TAG = "AlbumFragment";
    private RecyclerView recyclerView;
    private MediaItemsAdapter mediaItemsAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance(Album album) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString("albumId", album.getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        AlbumViewModel albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        String albumId = getArguments() != null ? getArguments().getString("albumId") : null;
        if (albumId != null) {
            albumViewModel.loadAlbum(albumId);
        }
        albumViewModel.getAlbum().observe(this, albumLiveData -> {
            Log.d(TAG, "album loaded");
            mediaItemsAdapter.setMediaItems(albumLiveData);
        });

        albumViewModel.getSelectedIndex().observe(this, selectedIndex -> {
                Log.d(TAG, "photo selected: " + selectedIndex);
                MainActivity.selectedPhotoIndex = selectedIndex;
                getFragmentManager().beginTransaction()
                    .addToBackStack("viewPager")
                    .replace(R.id.fragment_container, PhotoPagerFragment.newInstance(albumId), PhotoPagerFragment.TAG)
                    .commit();
        });

        mediaItemsAdapter = new MediaItemsAdapter(albumViewModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.media_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.setAdapter(mediaItemsAdapter);
        return view;
    }

}
