package music_38.framgia.com.musicup.service;

import music_38.framgia.com.musicup.data.model.Track;

interface UpdateNotification {
    void onUpdateWhenTrackComplete(Track track);
}
