package com.luping.photos.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luping.photos.R;
import com.luping.photos.model.MediaItem;

import java.util.List;

/**
 * List Adapter containing photos in an album. This adapter backs the RecyclerView in
 * {@link AlbumFragment}.
 */
public class MediaItemsAdapter extends RecyclerView.Adapter<MediaItemsAdapter.ViewHolder> {

    public interface PhotoOnClickListener {
        void onPhotoClicked(MediaItem item, ImageView imageView, int position);
    }

    private final PhotoOnClickListener photoOnClickListener;
    private List<MediaItem> mediaItems;

    MediaItemsAdapter(PhotoOnClickListener photoOnClickListener) {
        this.photoOnClickListener = photoOnClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.media_item_image);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                photoOnClickListener.onPhotoClicked(getItem(position), imageView, position);
            });
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
        MediaItem item = getItem(position);
        if (item != null) {
            Log.d("Luping", "mediaItem id: " + item.getId());
            holder.imageView.setTransitionName(item.getId());
            Glide.with(holder.itemView).load(item.getBaseUrl()).into(holder.imageView);
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

    MediaItem getItem(int position) {
        return mediaItems == null ? null : mediaItems.get(position);
    }
}
