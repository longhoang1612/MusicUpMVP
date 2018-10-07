package music_38.framgia.com.musicup.service;

import java.util.List;

import music_38.framgia.com.musicup.data.model.Track;

public class SongServiceContract {

    public interface ISongService {

        void playPauseSong();

        void play();

        void previousSong();

        void nextSong();

        void shuffleSong(int shuffleType);

        void loopSong(int loopType);

        void downloadCurrentTrack(int state);

        void seekTo(int seekTo);

        int getDurationSong();

        int getCurrentDurationSong();

        Track getTrackCurrent();

        void favoritesSong(int state);

        List<Track> getListTrack();

        int getCurrentPosition();

        int getTypeTrack();
    }

    public interface OnMediaPlayerChangeListener {

        void onMediaStateChange(boolean isPlaying);

        void onTrackChange(Track track);

        void onLoopChange(int state);

        void onShuffleChange(int state);
    }

    public interface OnChangeButtonMediaPlayer {
        void onTrackChange(Track track);

        void onFavoritesChange(int state);

        void onDownLoadChange(int state);
    }

    public interface OnChangePlayNow {
        void onTrackChange(Track track, int position);

        void onListChange(List<Track> tracks);
    }

    public interface OnMiniPlayerChangeListener {
        void onMediaStateChange(boolean isPlaying);

        void onTrackChange(Track track);
    }
}
