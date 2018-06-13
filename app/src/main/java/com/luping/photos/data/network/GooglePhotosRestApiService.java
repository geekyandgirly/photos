package com.luping.photos.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by luping on 6/9/18.
 */

public interface GooglePhotosRestApiService {
    @GET("v1/albums")
    Call<AlbumsResponse> getAlbumList();

    @POST("v1/mediaItems:search")
    Call<MediaItemsResponse> getMediaItems(@Query("albumId") String albumId,
                                           @Query("pageSize") int pageSize);
}
