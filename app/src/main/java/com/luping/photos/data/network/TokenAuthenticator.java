package com.luping.photos.data.network;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by luping on 6/11/18.
 */

public class TokenAuthenticator implements Authenticator {
    private static final String TAG = "TokenAuthenticator";
    public static String AUTHORIZATION = "Authorization";
    public static String SCOPE = "oauth2:https://www.googleapis.com/auth/photoslibrary.readonly";

    private final Context context;
//    private int mCounter = 0;

    public TokenAuthenticator(@NonNull Application application) {
        context = application;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
//        if (mCounter++ > 3) {
//            return null;
//        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null) {
            Log.d(TAG, "account is null");
            return null;
        }

        Log.d(TAG,"getAccount().name : " + account.getAccount().name);

        if (response.request().header(AUTHORIZATION) != null) {
            Log.d(TAG, "already failed authentication");
            return null; // Give up, we've already attempted to authenticate.
        }

        try {
            String token = GoogleAuthUtil.getToken(context, account.getAccount(), SCOPE);
            Log.d(TAG,"token: " + token);
            return response.request().newBuilder()
                    .header(AUTHORIZATION, "Bearer " + token)
                    .build();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
}
