package music_38.framgia.com.musicup.screen.home;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.Callback;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private TrackRepository mTrackRepository;

    HomePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void getGenre() {
        mTrackRepository.getGenre(new Callback<ArrayList<Genre>>() {
            @Override
            public void getDataSuccess(ArrayList<Genre> data) {
                mView.onGetGenreSuccess(data);
                mView.hideProgress();
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.onGetGenreError(e);
                mView.hideProgress();
            }
        });
    }
}
