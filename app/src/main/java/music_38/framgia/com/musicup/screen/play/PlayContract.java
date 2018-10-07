package music_38.framgia.com.musicup.screen.play;

import music_38.framgia.com.musicup.data.source.local.PlayMode;
import music_38.framgia.com.musicup.screen.base.BasePresenter;

class PlayContract {

    interface Presenter extends BasePresenter {
        PlayMode getPlayMode();

        void savePlayMode(PlayMode playMode);
    }
}
