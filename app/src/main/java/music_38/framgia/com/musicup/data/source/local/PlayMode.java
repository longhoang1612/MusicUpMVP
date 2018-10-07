package music_38.framgia.com.musicup.data.source.local;

import music_38.framgia.com.musicup.data.model.DownloadMode;
import music_38.framgia.com.musicup.data.model.FavoritesMode;
import music_38.framgia.com.musicup.data.model.LoopMode;
import music_38.framgia.com.musicup.data.model.ShuffleMode;

public class PlayMode {
    private int mLoopMode;
    private int mShuffleMode;
    private int mFavoritesMode;
    private int mDownload;

    public int getLoopMode() {
        return mLoopMode;
    }

    public void setLoopMode(@LoopMode int loopMode) {
        mLoopMode = loopMode;
    }

    public int getShuffleMode() {
        return mShuffleMode;
    }

    public void setShuffleMode(@ShuffleMode int shuffleMode) {
        mShuffleMode = shuffleMode;
    }

    public int getFavoritesMode() {
        return mFavoritesMode;
    }

    public void setFavoritesMode(@FavoritesMode int favoritesMode) {
        mFavoritesMode = favoritesMode;
    }

    public int getDownload() {
        return mDownload;
    }

    public void setDownload(@DownloadMode int download) {
        mDownload = download;
    }
}
