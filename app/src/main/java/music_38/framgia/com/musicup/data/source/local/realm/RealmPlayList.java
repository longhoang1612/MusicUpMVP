package music_38.framgia.com.musicup.data.source.local.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import music_38.framgia.com.musicup.data.model.GenreSave;

/**
 * Created by apple on 09/08/2017.
 */

public class RealmPlayList {
    private static final String PRIMARY_KEY = "mTitle";
    private static Realm realm = Realm.getDefaultInstance();

    public static void addPLayList(GenreSave playList) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(playList);
        realm.commitTransaction();
    }

    public static void delPlayList(final GenreSave playList) {
        RealmResults<GenreSave> genreSaves = realm.where(GenreSave.class).equalTo(PRIMARY_KEY, playList.getTitle()).findAll();
        realm.beginTransaction();
        genreSaves.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static void deleteFavoritesPlaylist(final GenreSave playList, int position) {
        GenreSave genreSave = realm.where(GenreSave.class).equalTo(PRIMARY_KEY, playList.getTitle()).findFirst();
        realm.beginTransaction();
        if (genreSave != null) {
            genreSave.getTracks().remove(position);
        }
        realm.commitTransaction();
    }

    public static List<GenreSave> getPLayList() {
        return realm.where(GenreSave.class).findAll();
    }

    public static void editNamePlaylistFavorites(final GenreSave newPlayList, String rename) {
        GenreSave genreSave = realm.where(GenreSave.class).equalTo(PRIMARY_KEY, newPlayList.getTitle()).findFirst();
        realm.beginTransaction();
        if (genreSave != null) {
            genreSave.setTitle(rename);
            realm.copyToRealmOrUpdate(genreSave);
            realm.commitTransaction();
        }
    }


}
