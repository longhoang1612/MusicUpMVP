package music_38.framgia.com.musicup.screen.favorites;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.local.realm.RealmFavoritesSong;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private FavoritesContract.View mView;
    private TrackRepository mTrackRepository;

    FavoritesPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void setView(FavoritesContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getFavorites() {
        List<Track> tracks = new ArrayList<>(RealmFavoritesSong.getFavorites());
        if (RealmFavoritesSong.getFavorites() == null) {
            mView.getDataError();
            mView.hideProgress();
        } else {
            mView.getDataFavoritesSuccess(tracks);
            mView.hideProgress();
        }
    }
}
