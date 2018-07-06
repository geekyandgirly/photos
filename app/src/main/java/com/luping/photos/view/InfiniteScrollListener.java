package com.luping.photos.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by luping on 7/5/18.
 */

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    private final GridLayoutManager layoutManager;
    private int latestTotalItemCount = 0;
    private boolean isLoading = true;

    public InfiniteScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy){
        int lastVisibleItemPos = layoutManager.findLastCompletelyVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();

        if (totalItemCount < latestTotalItemCount) {
            this.latestTotalItemCount = totalItemCount;
        } else if (isLoading && totalItemCount > latestTotalItemCount) {
            isLoading = false;
            latestTotalItemCount = totalItemCount;
        } else if (!isLoading && (lastVisibleItemPos + 6 > totalItemCount)) {
            onLoadMore();
            isLoading = true;
        }
    }

    public abstract void onLoadMore();
}
