package music_38.framgia.com.musicup.screen.download;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class DownloadContract {
    interface View {

        void onGetTrackDownload(List<Track> tracks);

        void onGetDataError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {

        void getTotalDownloadTrack();
    }
}
