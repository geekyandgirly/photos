package com.luping.photos.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.luping.photos.R;
import com.luping.photos.model.Album;
import com.luping.photos.viewmodel.AlbumViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    public static String TAG = "AlbumFragment";
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
            mediaItemsAdapter.setMediaItems(albumLiveData);
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        RecyclerView mediaItemsView = view.findViewById(R.id.media_items);
        mediaItemsView.setHasFixedSize(true);
        mediaItemsView.setLayoutManager(new LinearLayoutManager(getContext()));

        mediaItemsAdapter = new MediaItemsAdapter();
        mediaItemsView.setAdapter(mediaItemsAdapter);
        return view;
    }

}
