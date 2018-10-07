package music_38.framgia.com.musicup.screen.play;

import music_38.framgia.com.musicup.data.repository.PlayModeRepository;
import music_38.framgia.com.musicup.data.source.local.PlayMode;

public class PlayPresenter implements PlayContract.Presenter {
    private PlayModeRepository mPlayModeRepository;

    public PlayPresenter(PlayModeRepository playModeRepository) {
        mPlayModeRepository = playModeRepository;
    }

    @Override
    public void setView(Object view) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public PlayMode getPlayMode() {
        return mPlayModeRepository.getPlayMode();
    }

    @Override
    public void savePlayMode(PlayMode playMode) {
        mPlayModeRepository.savePlayMode(playMode);
    }
}
