package music_38.framgia.com.musicup.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RecentSearch extends RealmObject {
    @PrimaryKey
    private String mRecentSearch;

    public RecentSearch() {
    }

    public RecentSearch(String recentSearch) {
        mRecentSearch = recentSearch;
    }

    public String getRecentSearch() {
        return mRecentSearch;
    }

    public void setRecentSearch(String recentSearch) {
        mRecentSearch = recentSearch;
    }
}
