package music_38.framgia.com.musicup.data.source.local.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import music_38.framgia.com.musicup.data.model.Track;

public class RealmDownloadSong {
    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_DOWNLOAD = "mId";

    public static void addDownload(Track track) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(track);
        realm.commitTransaction();
    }

    public static Track getTrack(Track track) {
        RealmObject realmObject = realm.where(Track.class).equalTo(PRIMARY_KEY_DOWNLOAD, track.getId()).findFirst();
        return (Track) realmObject;
    }

    public static void deleteTrack(Track track) {
        RealmResults<Track> favoritesTrack = realm.where(Track.class).equalTo(PRIMARY_KEY_DOWNLOAD, track.getId()).findAll();
        realm.beginTransaction();
        favoritesTrack.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static List<Track> getDownloadList() {
        return realm.where(Track.class).findAll();
    }
}
