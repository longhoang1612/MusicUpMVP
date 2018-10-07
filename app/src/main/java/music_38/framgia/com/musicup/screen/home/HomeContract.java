package music_38.framgia.com.musicup.screen.home;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class HomeContract {
    interface View {
        void onGetGenreSuccess(ArrayList<Genre> data);

        void hideProgress();

        void onGetGenreError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {
        void getGenre();
    }
}
