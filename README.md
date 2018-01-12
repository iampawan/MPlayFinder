# MPlayLib

This is an easiest-to-use library to access all the music files on Android using Kotlin or Java.

## Usage

Add to your Android Studio project the following dependency:

```
implementation 'com.mtechviral.mplaylib:mplaylib:1.0.0'
```

If you want to get a list of all songs available on an Android device, all you need is the following code:

```
MusicFinder musicFinder = new MusicFinder(getContentResolver());
musicFinder.prepare();

List<MusicFinder.Song> songs = musicFinder.getAllSongs();
System.out.println(songs.size());
for(MusicFinder.Song song:songs) {
    System.out.println(song.getTitle());
    System.out.println(song.getArtist());
}
```

On devices running Android Marshmallow or higher, you'll need to request the `READ_EXTERNAL_STORAGE` permission at runtime in to access music files on the SD Card. You can either request it using the usual code, or you can use the `MusicHelper` helper class:

```
// Check if permission granted. If not, request
if(MusicHelper.hasExternalStorageAccess(this)) {
    // Do something
}

...
...

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                                        @NonNull int[] grantResults) {
    if(MusicHelper.isAccessGranted(requestCode, permissions, grantResults)) {
        // Do something
    }
}
```