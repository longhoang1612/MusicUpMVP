package music_38.framgia.com.musicup.data.repository;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.data.source.TrackDataSource;
import music_38.framgia.com.musicup.data.source.local.GenreLocalDataSource;
import music_38.framgia.com.musicup.data.source.local.TrackLocalDataSource;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;

public class TrackRepository implements TrackDataSource.RemoteDataSource,
        TrackDataSource.LocalDataSource, TrackDataSource.LocalStorageDataSource {

    private GenreLocalDataSource mGenreLocalDataSource;
    private TrackRemoteDataSource mTrackRemoteDataSource;
    private TrackLocalDataSource mTrackLocalDataSource;

    public TrackRepository(GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
    }

    public TrackRepository(TrackRemoteDataSource trackRemoteDataSource) {
        mTrackRemoteDataSource = trackRemoteDataSource;
    }

    public TrackRepository(TrackLocalDataSource trackLocalDataSource) {
        mTrackLocalDataSource = trackLocalDataSource;
    }

    public TrackRepository(GenreLocalDataSource genreLocalDataSource, TrackRemoteDataSource trackRemoteDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
        mTrackRemoteDataSource = trackRemoteDataSource;
    }

    @Override
    public void getGenre(Callback<ArrayList<Genre>> callback) {
        mGenreLocalDataSource.getGenre(callback);
    }

    @Override
    public void getSuggest(Callback<ArrayList<String>> callback) {
        mGenreLocalDataSource.getSuggest(callback);
    }

    @Override
    public void getGenre(Genre genre, int limit, Callback<Genre> callback) {
        mTrackRemoteDataSource.getGenre(genre, limit, callback);
    }

    @Override
    public void getSearchTrack(String searchKey, int limit, Callback<Genre> callback) {
        mTrackRemoteDataSource.getSearchTrack(searchKey, limit, callback);
    }

    @Override
    public void getTotalLocalMusic(Callback<Integer> callBack) {
        mTrackLocalDataSource.getTotalLocalMusic(callBack);
    }

    @Override
    public void getTrackLocal(Callback<List<Track>> callBack) {
        mTrackLocalDataSource.getTrackLocal(callBack);
    }

    @Override
    public void getTrackDownload(Callback<List<Track>> callback) {
        mTrackLocalDataSource.getTrackDownload(callback);
    }
}
