package com.luping.photos.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.luping.photos.R;
import com.luping.photos.model.MediaItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * List Adapter containing photos in an album. This adapter backs the RecyclerView in
 * {@link AlbumFragment}.
 */
public class MediaItemsAdapter extends RecyclerView.Adapter<MediaItemsAdapter.ViewHolder> {

    private List<MediaItem> mediaItems;

    MediaItemsAdapter() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.media_item_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_media_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaItem item = mediaItems == null ? null : mediaItems.get(position);
        if (item != null) {
            Picasso.get().cancelRequest(holder.imageView);
            Picasso.get().load(item.getBaseUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mediaItems == null ? 0 : mediaItems.size();
    }

    void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
        notifyDataSetChanged();
    }
}
