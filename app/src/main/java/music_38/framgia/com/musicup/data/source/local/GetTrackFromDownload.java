package music_38.framgia.com.musicup.data.source.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.model.TypeTrack;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.local.realm.RealmDownloadSong;

class GetTrackFromDownload {
    private Context mContext;
    private Callback mCallBack;

    GetTrackFromDownload(Context context, Callback callBack) {
        mContext = context;
        mCallBack = callBack;
    }

    void getDownloadTracks() {
        List<Track> tracks = new ArrayList<>(RealmDownloadSong.getDownloadList());
        for (Track track : tracks) {
            track.setTypeTrack(TypeTrack.TYPE_OFFLINE);
        }
        mCallBack.getDataSuccess(tracks);
    }
}
