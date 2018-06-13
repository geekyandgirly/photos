package com.luping.photos.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.luping.photos.R;
import com.luping.photos.model.Album;

public class MainActivity extends AppCompatActivity
        implements AlbumListFragment.OnAlbumSelectedListener {
    private static final int REQUEST_CODE_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            // User hasn't signed in, start login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SIGN_IN);
        } else if (savedInstanceState == null) {
            Log.d(TAG, "activity oncreate creating new list fragment");
            AlbumListFragment albumListFragment = new AlbumListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, albumListFragment, AlbumListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN && resultCode == Activity.RESULT_OK) {
            Fragment albumListFragment =
                    getSupportFragmentManager().findFragmentByTag(AlbumListFragment.TAG);
            if (albumListFragment == null) {
                albumListFragment = new AlbumListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, albumListFragment, AlbumListFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onAlbumSelected(Album album) {
        Log.d(TAG, "album selected: " + album.getId());
        AlbumFragment albumFragment = AlbumFragment.newInstance(album);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("albumDetail")
                .replace(R.id.fragment_container, albumFragment, AlbumFragment.TAG)
                .commit();
    }
}
