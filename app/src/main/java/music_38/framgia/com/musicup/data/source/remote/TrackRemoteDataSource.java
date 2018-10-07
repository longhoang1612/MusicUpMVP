package music_38.framgia.com.musicup.data.source.remote;


import music_38.framgia.com.musicup.BuildConfig;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.TrackDataSource;
import music_38.framgia.com.musicup.utils.Constants;

import static music_38.framgia.com.musicup.BuildConfig.API_KEY;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {

    private void getGenreByTrackFromApi(Genre genre, int limit, Callback callBack) {
        String url = Constants.SoundCloud.BASE_URL +
                Constants.SoundCloud.PARAM_KIND +
                Constants.SoundCloud.PARAM_GENRE +
                Constants.SoundCloud.PARAM_TYPE +
                genre.getType() +
                Constants.SoundCloud.PARAM_CLIENT_ID +
                API_KEY +
                Constants.SoundCloud.PARAM_LIMIT +
                limit;
        new GenreRemoteAsyncTask(callBack, genre).execute(url);
    }

    public static String getStreamUrl(int id) {
        String stringBuilder = Constants.Stream.STREAM_URL +
                String.valueOf(id) +
                Constants.Stream.STREAM +
                Constants.Stream.STREAM_CLIENT_ID +
                API_KEY;
        return stringBuilder;
    }

    @Override
    public void getGenre(Genre genre, int limit, Callback<Genre> callback) {
        getGenreByTrackFromApi(genre, limit, callback);
    }

    @Override
    public void getSearchTrack(String searchKey, int limit, Callback<Genre> callback) {
        getTrackBySearch(searchKey, limit, callback);
    }

    private void getTrackBySearch(String searchKey, int limit, Callback callBack) {

        String url = Constants.SoundCloud.BASE_URL +
                Constants.SoundCloud.SEARCH +
                Constants.SoundCloud.QUERY_SEARCH +
                searchKey +
                Constants.SoundCloud.PARAM_CLIENT_ID +
                BuildConfig.API_KEY +
                Constants.SoundCloud.PARAM_LIMIT +
                limit;

        new SearchTrackRemoteAsyncTask(callBack, searchKey).execute(url);
    }

}
