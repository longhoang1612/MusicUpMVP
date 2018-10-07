package music_38.framgia.com.musicup.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.model.TypeTrack;
import music_38.framgia.com.musicup.data.source.Callback;

public class GetTrackFromStorage {
    private Context mContext;
    private Callback mCallBack;

    GetTrackFromStorage(Context context, Callback callBack) {
        mContext = context;
        mCallBack = callBack;
    }

    public List<Track> getMusic() {
        List<Track> tracks = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int idColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int durationColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);

            do {
                long id = cursor.getLong(idColumn);
                String currentTitle = cursor.getString(songTitle);
                String currentArtist = cursor.getString(songArtist);
                String path = ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id).toString();
                int duration = cursor.getInt(durationColumn);
                Track track = new Track(0, duration, currentTitle, currentArtist, null, path, TypeTrack.TYPE_OFFLINE);
                tracks.add(track);
            } while (cursor.moveToNext());
        }
        return tracks;
    }

    public void getSongLocal() {
        if (mCallBack == null) {
            return;
        }
        mCallBack.getDataSuccess(getMusic());
    }

    public void getTotalSongLocal() {
        if (mCallBack == null) {
            return;
        }
        mCallBack.getDataSuccess(getMusic().size());
    }
}
