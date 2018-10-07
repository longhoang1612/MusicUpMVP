package music_38.framgia.com.musicup.data.model;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.model.ShuffleMode.NO_SHUFFLE;
import static music_38.framgia.com.musicup.data.model.ShuffleMode.SHUFFLE_ALL;

@IntDef({SHUFFLE_ALL, NO_SHUFFLE})
public @interface ShuffleMode {
    int SHUFFLE_ALL = 0;
    int NO_SHUFFLE = 1;
}
