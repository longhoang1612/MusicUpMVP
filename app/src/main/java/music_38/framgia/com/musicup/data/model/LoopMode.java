package music_38.framgia.com.musicup.data.model;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.model.LoopMode.LOOP_ALL;
import static music_38.framgia.com.musicup.data.model.LoopMode.LOOP_ONE;
import static music_38.framgia.com.musicup.data.model.LoopMode.NO_LOOP;

@IntDef({LOOP_ONE, LOOP_ALL, NO_LOOP})
public @interface LoopMode {
    int LOOP_ONE = 2;
    int LOOP_ALL = 1;
    int NO_LOOP = 0;
}
