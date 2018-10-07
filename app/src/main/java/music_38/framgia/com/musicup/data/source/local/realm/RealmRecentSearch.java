package music_38.framgia.com.musicup.data.source.local.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import music_38.framgia.com.musicup.data.model.RecentSearch;

public class RealmRecentSearch {

    private static Realm realm = Realm.getDefaultInstance();
    private static final String KEY_RECENT_SEARCH = "recentSearch";
    private static final String PRIMARY_KEY = "mRecentSearch";

    public static void addRecentSearch(RecentSearch recentSearch) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(recentSearch);
        realm.commitTransaction();
    }

    public static RecentSearch getRecentSearch(RecentSearch recentSearch) {
        RealmObject realmObject = realm.where(RecentSearch.class).equalTo(KEY_RECENT_SEARCH, recentSearch.getRecentSearch()).findFirst();
        return (RecentSearch) realmObject;
    }

    public static void deleteRecentSearch(final RecentSearch recentSearch) {
        RealmResults<RecentSearch> movies = realm.where(RecentSearch.class).equalTo(PRIMARY_KEY, recentSearch.getRecentSearch()).findAll();
        realm.beginTransaction();
        movies.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static List<RecentSearch> getRecentSearchList() {
        return realm.where(RecentSearch.class).findAll();
    }
}
