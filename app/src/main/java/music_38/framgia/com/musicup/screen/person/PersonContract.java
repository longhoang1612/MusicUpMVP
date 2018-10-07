package music_38.framgia.com.musicup.screen.person;

import java.util.List;

import music_38.framgia.com.musicup.data.model.GenreSave;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class PersonContract {
    interface View {

        void getDataSuccess(List<GenreSave> genreSaves);

        void hideProgress();

        void getDataError();
    }

    interface Presenter extends BasePresenter {
        void getPlayListFavorites();
    }
}
