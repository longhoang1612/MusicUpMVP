package music_38.framgia.com.musicup.screen.person;

import java.util.List;

import music_38.framgia.com.musicup.data.model.GenreSave;
import music_38.framgia.com.musicup.data.source.local.realm.RealmPlayList;

public class PersonPresenter implements PersonContract.Presenter {

    private PersonContract.View mView;

    @Override
    public void setView(Object view) {
        mView = (PersonContract.View) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getPlayListFavorites() {
        List<GenreSave> genreSaves = RealmPlayList.getPLayList();
        if (RealmPlayList.getPLayList() == null) {
            mView.getDataError();
        } else {
            mView.getDataSuccess(genreSaves);
        }
    }
}
