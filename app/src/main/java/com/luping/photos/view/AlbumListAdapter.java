package com.luping.photos.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luping.photos.R;
import com.luping.photos.model.Album;
import com.luping.photos.viewmodel.AlbumListViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * List adapter containing a list of Albums. This adapter backs {@link AlbumListFragment}.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {
    private static final String TAG = "AlbumListAdapter";
    private final AlbumListViewModel albumListViewModel;
    private List<Album> albumList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.album_cover_image);
            textView = itemView.findViewById(R.id.album_title);
            itemView.setOnClickListener((view) ->  {
                int position = getAdapterPosition();
                Log.i(TAG,"item " + position + " clicked!");
                albumListViewModel.getSelectedAlbum().setValue(albumList.get(position));
            });
        }
    }

    public AlbumListAdapter(AlbumListViewModel albumListViewModel) {
        this.albumListViewModel = albumListViewModel;
        this.albumList = albumListViewModel.getAlbumList().getValue();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumList == null ? null : albumList.get(position);
        if (album != null) {
            holder.textView.setText(album.getTitle());
//            Log.d(TAG, "productUrl " + album.getProductUrl() + ", albumcoverbaseurl: " + album.getCoverPhotoBaseUrl());
            Picasso.get().cancelRequest(holder.imageView);
            Picasso.get().load(album.getCoverPhotoBaseUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
        notifyDataSetChanged();
    }
}
