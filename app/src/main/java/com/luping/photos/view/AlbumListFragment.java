package com.luping.photos.view;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.luping.photos.R;
import com.luping.photos.model.Album;
import com.luping.photos.viewmodel.AlbumListViewModel;

/**
 * Fragment displaying list of Albums. Clicking the cover photo of an Album will take you to
 * the {@link AlbumFragment}.
 */
public class AlbumListFragment extends Fragment {
    public static final String TAG = "AlbumListFragment";

    private AlbumListViewModel albumListViewModel;
    private RecyclerView albumListView;
    private AlbumListAdapter albumListAdapter;
    private OnAlbumSelectedListener onAlbumSelectedListener;

    public interface OnAlbumSelectedListener {
        void onAlbumSelected(Album album);
    }
    public AlbumListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "list fragment oncreate");
        albumListViewModel = ViewModelProviders.of(this).get(AlbumListViewModel.class);
        albumListViewModel.getAlbumList().observe(this, albumList -> {
            Log.d(TAG, "albumList changed");
            albumListAdapter.setAlbumList(albumList);
        });
        albumListViewModel.getSelectedAlbum().observe(this, album -> {
            Log.d(TAG, "album selected: ");
            if (onAlbumSelectedListener != null && album != null) {
                onAlbumSelectedListener.onAlbumSelected(album);
                albumListViewModel.getSelectedAlbum().setValue(null);
            }
        });

        albumListAdapter = new AlbumListAdapter(albumListViewModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album_list, container, false);
        albumListView = rootView.findViewById(R.id.album_list);
        albumListView.setHasFixedSize(true);
        albumListView.setLayoutManager(new GridLayoutManager(getContext(),2));
        albumListView.setHasFixedSize(true);

        albumListView.setAdapter(albumListAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        onAlbumSelectedListener = (OnAlbumSelectedListener) getActivity();
    }
}
