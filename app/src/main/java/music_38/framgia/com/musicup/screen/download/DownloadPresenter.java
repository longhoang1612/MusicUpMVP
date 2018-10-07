package music_38.framgia.com.musicup.screen.download;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.Callback;

public class DownloadPresenter implements DownloadContract.Presenter {
    private TrackRepository mTrackRepository;
    private DownloadContract.View mView;

    public DownloadPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void getTotalDownloadTrack() {
        mTrackRepository.getTrackDownload(new Callback<List<Track>>() {
            @Override
            public void getDataSuccess(List<Track> data) {
                mView.onGetTrackDownload(data);
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.onGetDataError(e);
            }
        });
    }

    @Override
    public void setView(DownloadContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
