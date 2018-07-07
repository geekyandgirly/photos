package com.luping.photos.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luping.photos.R;
import com.luping.photos.model.Album;
import com.luping.photos.model.MediaItem;
import com.luping.photos.viewmodel.AlbumViewModel;

/**
 * Fragment displaying photos in an Album.
 */
public class AlbumFragment extends Fragment implements MediaItemsAdapter.PhotoOnClickListener {
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

        mediaItemsAdapter = new MediaItemsAdapter(this);

        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.media_items);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mediaItemsAdapter);
        recyclerView.addOnScrollListener(new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                AlbumViewModel albumViewModel = ViewModelProviders.of(AlbumFragment.this).get(AlbumViewModel.class);
                String albumId = getArguments() != null ? getArguments().getString("albumId") : null;
                if (albumId != null) {
                    albumViewModel.loadAlbum(albumId);
                }
            }
        });
    }


    @Override
    public void onPhotoClicked(MediaItem item, ImageView transitionView, int position) {
//        Log.d("Luping", "getTansitionname: " + transitionView.getTransitionName());
        ((TransitionSet) getExitTransition()).excludeTarget(transitionView, true);
        getFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true) // setAllowOptimization before 26.1.0
                .addSharedElement(transitionView, transitionView.getTransitionName())
                .addToBackStack("photoDetail")
                .replace(R.id.fragment_container, PhotoFragment.newInstance(item), PhotoFragment.TAG)
                .commit();

    }
}
