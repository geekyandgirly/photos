package com.luping.photos.view;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

        view.findViewById(R.id.photo).setTransitionName(bundle.getString("photoUrl"));

        Glide.with(this)
                .load(bundle.getString("photoUrl"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }
                })
                .into((ImageView) view.findViewById(R.id.photo));
        return view;
    }
}
