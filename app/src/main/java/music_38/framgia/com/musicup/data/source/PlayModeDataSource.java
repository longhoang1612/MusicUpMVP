package music_38.framgia.com.musicup.data.source;

import music_38.framgia.com.musicup.data.source.local.PlayMode;

public interface PlayModeDataSource {
    void savePlayMode(PlayMode mode);

    PlayMode getPlayMode();
}
