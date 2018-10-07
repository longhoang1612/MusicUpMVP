package music_38.framgia.com.musicup.data.source.local.realm;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import music_38.framgia.com.musicup.data.model.Track;

public class RealmFavoritesSong {

    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_FAVORITES = "mId";

    public static void addFavorites(Track track) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(track);
        realm.commitTransaction();
    }

    public static Track getTrack(Track track) {
        RealmObject realmObject = realm.where(Track.class).equalTo(PRIMARY_KEY_FAVORITES, track.getId()).findFirst();
        return (Track) realmObject;
    }

    public static void deleteFavorites(Track track) {
        RealmResults<Track> favoritesTrack = realm.where(Track.class).equalTo(PRIMARY_KEY_FAVORITES, track.getId()).findAll();
        realm.beginTransaction();
        favoritesTrack.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static List<Track> getFavorites() {
        return realm.where(Track.class).findAll();
    }
}
