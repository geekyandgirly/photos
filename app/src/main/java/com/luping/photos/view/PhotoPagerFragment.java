package com.luping.photos.view;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luping.photos.R;
import com.luping.photos.model.Album;
import com.luping.photos.viewmodel.AlbumViewModel;

public class PhotoPagerFragment extends Fragment {
    static String TAG = "PhotoPagerFragment";
    private ViewPager viewPager;
    private PhotoPagerAdapter pagerAdapter;

    public PhotoPagerFragment() {
        // Required empty public constructor
    }

    public static PhotoPagerFragment newInstance(String albumId) {
        PhotoPagerFragment fragment = new PhotoPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("albumId", albumId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new PhotoPagerAdapter(this);

        AlbumViewModel albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        String albumId = getArguments() != null ? getArguments().getString("albumId") : null;
        if (albumId != null) {
            albumViewModel.loadAlbum(albumId);
        }
        albumViewModel.getAlbum().observe(this, album -> {
            Log.d(TAG, "album loaded");
            pagerAdapter.setItems(album);
            if (viewPager != null) {
                viewPager.setCurrentItem(MainActivity.selectedPhotoIndex);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewPager = (ViewPager) inflater.inflate(
                R.layout.fragment_photo_pager, container, false);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(MainActivity.selectedPhotoIndex);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                MainActivity.selectedPhotoIndex = position;
            }
        });

        return viewPager;
    }
}
