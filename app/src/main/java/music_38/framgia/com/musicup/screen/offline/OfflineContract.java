package music_38.framgia.com.musicup.screen.offline;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class OfflineContract {
    interface View {

        void onGetTrackLocal(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {

        void getTotalLocalMusicLocal();
    }
}
