package com.luping.photos.view;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luping.photos.R;
import com.luping.photos.viewmodel.AlbumViewModel;

import java.util.List;
import java.util.Map;

/**
 * NOT USING pager right now. This makes SharedElementTransition difficult.
 */
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

        prepareSharedElementTransition();

        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return viewPager;
    }

    private void prepareSharedElementTransition() {
        Transition transition = TransitionInflater.from(getContext())
                .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Fragment currFragment = (Fragment) viewPager.getAdapter()
                        .instantiateItem(viewPager, MainActivity.selectedPhotoIndex);
                View view = currFragment.getView();
                if (view != null) {
                    sharedElements.put(names.get(0), view.findViewById(R.id.photo));
                }
            }
        });

    }
}
