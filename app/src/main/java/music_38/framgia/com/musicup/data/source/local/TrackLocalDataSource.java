package music_38.framgia.com.musicup.data.source.local;

import android.content.Context;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.TrackDataSource;


public class TrackLocalDataSource implements TrackDataSource.LocalStorageDataSource {
    private static TrackLocalDataSource sInstance;
    private static Context mContext;

    public static TrackLocalDataSource getsInstance(Context context) {
        mContext = context;
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource();
        }
        return sInstance;
    }

    @Override
    public void getTrackLocal(Callback<List<Track>> callBack) {
        new GetTrackFromStorage(mContext, callBack).getSongLocal();
    }

    @Override
    public void getTrackDownload(Callback<List<Track>> callback) {
        new GetTrackFromDownload(mContext, callback).getDownloadTracks();
    }

    @Override
    public void getTotalLocalMusic(Callback<Integer> callBack) {
        new GetTrackFromStorage(mContext, callBack).getTotalSongLocal();
    }
}
