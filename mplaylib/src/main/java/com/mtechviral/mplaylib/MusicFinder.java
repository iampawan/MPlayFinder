package com.mtechviral.mplaylib;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pawankumar on 12/01/18.
 */

public class MusicFinder {
    private static final String TAG = "MPLAY_MUSIC_FINDER";

    private ContentResolver mContentResolver;
    private List<Song> mSongs = new ArrayList<>();
    private Random mRandom = new Random();

    public MusicFinder(ContentResolver cr) {
        mContentResolver = cr;
    }

    public void prepare() {
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = mContentResolver.query(uri, null,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);

        if (cur == null) {
            return;
        }
        if (!cur.moveToFirst()) {
            return;
        }

        int artistColumn = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int albumColumn = cur.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int albumArtColumn = cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int durationColumn = cur.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idColumn = cur.getColumnIndex(MediaStore.Audio.Media._ID);

        do {
            mSongs.add(new Song(
                    cur.getLong(idColumn),
                    cur.getString(artistColumn),
                    cur.getString(titleColumn),
                    cur.getString(albumColumn),
                    cur.getLong(durationColumn),
                    cur.getLong(albumArtColumn)));
        } while (cur.moveToNext());

    }

    public ContentResolver getContentResolver() {
        return mContentResolver;
    }

    public Song getRandomSong() {
        if (mSongs.size() <= 0) return null;
        return mSongs.get(mRandom.nextInt(mSongs.size()));
    }

    public List<Song> getAllSongs() {
        return mSongs;
    }

    public static class Song {
        long id;
        String artist;
        String title;
        String album;
        long albumId;
        long duration;

        public Song(long id, String artist, String title, String album, long duration, long albumId) {
            this.id = id;
            this.artist = artist;
            this.title = title;
            this.album = album;
            this.duration = duration;
            this.albumId = albumId;
        }

        public long getId() {
            return id;
        }

        public String getArtist() {
            return artist;
        }

        public String getTitle() {
            return title;
        }

        public String getAlbum() {
            return album;
        }

        public long getDuration() {
            return duration;
        }

        public long getAlbumId() {
            return albumId;
        }

        public Uri getURI() {
            return ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        }

        public Uri getAlbumArt() {
            try {
                Uri genericArtUri = Uri.parse("content://media/external/audio/albumart");
                Uri actualArtUri = ContentUris.withAppendedId(genericArtUri, albumId);
                return actualArtUri;
            } catch(Exception e) {
                return null;
            }
        }
    }
}
