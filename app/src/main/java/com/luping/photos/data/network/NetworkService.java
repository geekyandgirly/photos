package com.luping.photos.data.network;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luping on 7/3/18.
 */

public class NetworkService {
    private Retrofit retrofit = null;
    private NetworkApi api = null;

    private Retrofit getRetrofit(Application application) {
        if (retrofit == null) {
            TokenAuthenticator authenticator = new TokenAuthenticator(application);
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .authenticator(authenticator)
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://photoslibrary.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public NetworkApi getApi(Application application) {
        if (api == null) {
            api = getRetrofit(application).create(NetworkApi.class);
        }
        return api;
    }
}
