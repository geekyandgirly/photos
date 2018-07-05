package com.luping.photos.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luping.photos.R;
import com.luping.photos.model.MediaItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment {

    public PhotoFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(MediaItem mediaItem) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle argument = new Bundle();
        argument.putString("photoUrl", mediaItem.getBaseUrl());
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Bundle bundle = getArguments();
        Glide.with(this)
                .load(bundle.getString("photoUrl"))
                .into((ImageView) view.findViewById(R.id.photo));
        return view;
    }
}
