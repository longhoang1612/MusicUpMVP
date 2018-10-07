package music_38.framgia.com.musicup.screen.playlist;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.Callback;

public class PlayListPresenter implements PlayListContract.Presenter {
    private PlayListContract.View mView;
    private TrackRepository mTrackRepository;

    public PlayListPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void getTrackByGenre(Genre genre, int limit) {
        mTrackRepository.getGenre(genre, limit, new Callback<Genre>() {
            @Override
            public void getDataSuccess(Genre data) {
                mView.getDataTrackSuccess(data);
                mView.hideProgress();
                mView.displayPlaylistBanner(data.getTracks().get(0).getArtworkUrl());
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.getDataError(e);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void setView(Object view) {
        mView = (PlayListContract.View) view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
