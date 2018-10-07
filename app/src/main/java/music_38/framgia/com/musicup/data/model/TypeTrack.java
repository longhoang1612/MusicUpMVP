package music_38.framgia.com.musicup.data.model;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.model.TypeTrack.TYPE_OFFLINE;
import static music_38.framgia.com.musicup.data.model.TypeTrack.TYPE_ONLINE;

@IntDef({TYPE_ONLINE, TYPE_OFFLINE})
public @interface TypeTrack {
    int TYPE_ONLINE = 1;
    int TYPE_OFFLINE = 0;
}
