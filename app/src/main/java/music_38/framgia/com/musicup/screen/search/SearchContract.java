package music_38.framgia.com.musicup.screen.search;


import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class SearchContract {
    interface View {

        void getDataTrackSuccess(Genre genre);

        void hideProgress();

        void getDataError(Exception exception);

        void onGetSuggestSuccess(ArrayList<String> suggest);

        void onGetSuggestError(Exception e);
    }

    interface Presenter extends BasePresenter {
        void getTrackBySearch(String searchKe,int limit);

        void getSuggests();
    }
}
