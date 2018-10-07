package music_38.framgia.com.musicup.data.source;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;

public interface TrackDataSource {

    interface RemoteDataSource {
        void getGenre(Genre genre, int limit, Callback<Genre> callback);

        void getSearchTrack(String searchKey, int limit, Callback<Genre> callback);
    }

    interface LocalDataSource {
        void getGenre(Callback<ArrayList<Genre>> callback);

        void getSuggest(Callback<ArrayList<String>> callback);
    }

    interface LocalStorageDataSource {
        void getTotalLocalMusic(Callback<Integer> callBack);

        void getTrackLocal(Callback<List<Track>> callBack);

        void getTrackDownload(Callback<List<Track>> callback);
    }
}
