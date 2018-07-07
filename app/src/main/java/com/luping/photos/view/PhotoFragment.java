package com.luping.photos.view;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.util.Log;
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
    static String TAG = "PhotoFragment";

    public PhotoFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(MediaItem mediaItem) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle argument = new Bundle();
        argument.putString("photoUrl", mediaItem.getBaseUrl());
        argument.putString("photoId", mediaItem.getId());
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.image_shared_element_transition));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ImageView imageView = (ImageView) view.findViewById(R.id.photo);

//        Log.d(TAG, "transition name: " + bundle.getString("photoId"));
        imageView.setTransitionName(bundle.getString("photoId"));

        Glide.with(this)
                .load(bundle.getString("photoUrl"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into((ImageView) view.findViewById(R.id.photo));
    }
}
