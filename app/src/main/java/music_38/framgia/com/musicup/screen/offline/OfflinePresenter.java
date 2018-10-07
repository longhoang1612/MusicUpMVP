package music_38.framgia.com.musicup.screen.offline;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.Callback;

public class OfflinePresenter implements OfflineContract.Presenter {

    private OfflineContract.View mView;
    private TrackRepository mTrackRepository;

    OfflinePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void getTotalLocalMusicLocal() {
        mTrackRepository.getTrackLocal(new Callback<List<Track>>() {
            @Override
            public void getDataSuccess(List<Track> data) {
                mView.onGetTrackLocal(data);
            }

            @Override
            public void getDataFailure(Exception e) {

            }
        });
    }

    @Override
    public void setView(OfflineContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
