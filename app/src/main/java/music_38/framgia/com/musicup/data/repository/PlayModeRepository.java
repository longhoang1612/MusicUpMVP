package music_38.framgia.com.musicup.data.repository;


import music_38.framgia.com.musicup.data.source.PlayModeDataSource;
import music_38.framgia.com.musicup.data.source.local.PlayMode;
import music_38.framgia.com.musicup.data.source.local.PlayModeLocalDataSource;

public class PlayModeRepository implements PlayModeDataSource {

    private PlayModeLocalDataSource mDataSource;

    public PlayModeRepository(PlayModeLocalDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void savePlayMode(PlayMode mode) {
        mDataSource.savePlayMode(mode);
    }

    @Override
    public PlayMode getPlayMode() {
        return mDataSource.getPlayMode();
    }
}
