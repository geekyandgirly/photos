package com.luping.photos.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luping.photos.model.MediaItem;

import java.util.List;

/**
 * Created by luping on 7/4/18.
 */

public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<MediaItem> items;

    public PhotoPagerAdapter(Fragment fragment) {
        super(fragment.getChildFragmentManager());
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(items.get(position));
    }

    public void setItems(List<MediaItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
