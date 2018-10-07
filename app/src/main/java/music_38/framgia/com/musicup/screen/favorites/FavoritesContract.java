package music_38.framgia.com.musicup.screen.favorites;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class FavoritesContract {
    interface View {

        void getDataFavoritesSuccess(List<Track> tracks);

        void hideProgress();

        void getDataError();
    }

    interface Presenter extends BasePresenter<View> {

        void getFavorites();
    }
}
