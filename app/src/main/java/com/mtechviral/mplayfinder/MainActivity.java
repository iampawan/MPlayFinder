package com.mtechviral.mplayfinder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mtechviral.mplaylib.MusicFinder;
import com.mtechviral.mplaylib.MusicHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(MusicHelper.hasExternalStorageAccess(this)) {
            start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(MusicHelper.isAccessGranted(requestCode, permissions, grantResults)) {
            start();
        }
    }

    private void start() {
        MusicFinder songFinder = new MusicFinder(getContentResolver());
        songFinder.prepare();
        List<MusicFinder.Song> songs = songFinder.getAllSongs();
        System.out.println(songs.size());
        for(MusicFinder.Song song:songs) {
            System.out.println(song.getTitle());
            System.out.println(song.getArtist());
        }
    }
}
