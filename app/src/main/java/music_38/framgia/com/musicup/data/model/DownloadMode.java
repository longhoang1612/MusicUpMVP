package music_38.framgia.com.musicup.data.model;

import android.support.annotation.IntDef;

import static music_38.framgia.com.musicup.data.model.DownloadMode.DOWNLOAD_ABLE;
import static music_38.framgia.com.musicup.data.model.DownloadMode.DOWNLOAD_DISABLE;

@IntDef({DOWNLOAD_ABLE, DOWNLOAD_DISABLE})
public @interface DownloadMode {
    int DOWNLOAD_ABLE = 1;
    int DOWNLOAD_DISABLE = 0;
}
