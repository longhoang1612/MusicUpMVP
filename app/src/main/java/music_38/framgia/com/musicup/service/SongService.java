package music_38.framgia.com.musicup.service;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.utils.SharedPrefs;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SongService extends Service implements SongServiceContract.ISongService, UpdateNotification {

    public static final String EXTRA_LIST = "EXTRA_LIST";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final String EXTRA_TYPE_TRACK = "EXTRA_TYPE_TRACK";
    public static final String ACTION_NEXT_TRACK = "ACTION_NEXT_TRACK";
    public static final String ACTION_PREVIOUS_TRACK = "ACTION_PREVIOUS_TRACK";
    public static final String ACTION_CHANGE_STATE = "ACTION_CHANGE_STATE";
    public static final String ACTION_MEDIA_CLEAR = "ACTION_MEDIA_CLEAR";
    private static final String CHANNEL_ID_NOTIFY = "CHANNEL_ID_NOTIFY";
    private static final String PREF_HEADSET_PLUG = "PREF_HEADSET_PLUG";
    public static final int ID_NOTIFICATION = 112;
    private static final int REQUEST_CODE_NEXT = 1;
    private static final int REQUEST_CODE_PREVIOUS = 2;
    private static final int REQUEST_CODE_PAUSE = 3;
    private static final int REQUEST_CODE_CLEAR = 4;
    private static final String PREF_STATE_ACTION = "PREF_STATE_ACTION";
    private final IBinder mSongBind = new SongBinder();
    private SongManager mSongManager;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private MusicIntentReceiver mMusicIntentReceiver;
    private SongManager.DownloadReceiver mDownloadReceiver;

    public static Intent getIntentService(Context context, List<Track> tracks, int position) {
        if (context != null) {
            Intent intent = new Intent(context, SongService.class);
            intent.putParcelableArrayListExtra(EXTRA_LIST, (ArrayList<? extends Parcelable>) tracks);
            intent.putExtra(EXTRA_POSITION, position);
            return intent;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int getDurationSong() {
        if (mSongManager == null) {
            return 0;
        }
        return mSongManager.getDurationTrack();
    }

    @Override
    public int getCurrentDurationSong() {
        if (mSongManager == null) {
            return 0;
        }
        return mSongManager.getCurrentDurationTrack();
    }

    @Override
    public void playPauseSong() {
        if (mSongManager == null) {
            return;
        }
        mSongManager.playPauseSong();
        updateNotificationState();
    }

    @Override
    public void play() {
        if (mSongManager == null) {
            return;
        }
        mSongManager.play();
    }

    @Override
    public void previousSong() {
        if (mSongManager == null) {
            return;
        }
        mSongManager.previousSong();
        updateNotificationChangeTrack(mSongManager.getTrackCurrent());
    }

    @Override
    public void nextSong() {
        if (mSongManager == null) {
            return;
        }
        mSongManager.nextSong();
        updateNotificationChangeTrack(mSongManager.getTrackCurrent());
    }

    @Override
    public void shuffleSong(int shuffleType) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.shuffleTrack(shuffleType);
    }

    @Override
    public void loopSong(int loopType) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.loopTrack(loopType);
    }

    @Override
    public void downloadCurrentTrack(int state) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.downloadCurrentTrack(state);
    }

    @Override
    public void seekTo(int seekTo) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.seekTo(seekTo);
    }

    @Override
    public Track getTrackCurrent() {
        if (mSongManager == null)
            return null;
        return mSongManager.getTrackCurrent();
    }

    @Override
    public void favoritesSong(int state) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.favoritesSong(state);
    }

    @Override
    public void onUpdateWhenTrackComplete(Track track) {
        updateNotificationChangeTrack(track);
    }

    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }

    @Override
    public List<Track> getListTrack() {
        if (mSongManager == null) {
            return null;
        }
        return mSongManager.getTracks();
    }

    @Override
    public int getCurrentPosition() {
        return mSongManager.getSongPosition();
    }

    @Override
    public int getTypeTrack() {
        if (mSongManager == null) {
            return 0;
        }
        return mSongManager.getTypeTrack();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMusicIntentReceiver = new SongService.MusicIntentReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mMusicIntentReceiver, filter);
        mDownloadReceiver = new SongManager.DownloadReceiver();
        IntentFilter filterDownLoad = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        IntentFilter filterDownLoad1 = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(mDownloadReceiver, filterDownLoad);
        registerReceiver(mDownloadReceiver, filterDownLoad1);

        if (intent != null) {
            List<Track> tracks = intent.getParcelableArrayListExtra(EXTRA_LIST);
            if (tracks != null) {
                int position = intent.getIntExtra(EXTRA_POSITION, 0);
                mSongManager = new SongManager(getApplicationContext(), tracks, position);
                createNotification(tracks.get(position).getTitle(), tracks.get(position).getArtist(),
                        tracks.get(position).getArtworkUrl());
                mSongManager.setUpdateNotification(this);
                mSongManager.play();
            }

            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_NEXT_TRACK:
                        nextSong();
                        break;
                    case ACTION_PREVIOUS_TRACK:
                        previousSong();
                        break;
                    case ACTION_CHANGE_STATE:
                        playPauseSong();
                        break;
                    case ACTION_MEDIA_CLEAR:
                        mSongManager.playPauseSong();
                        stopForeground(true);
                        break;
                }
            }
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSongBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        if (mMusicIntentReceiver != null) {
            unregisterReceiver(mMusicIntentReceiver);
        }
        if (mDownloadReceiver != null) {
            unregisterReceiver(mDownloadReceiver);
        }
        stopForeground(true);
    }

    public SongServiceContract.ISongService getISongService() {
        return this;
    }

    public void setOnChangePlayNow(SongServiceContract.OnChangePlayNow onChangePlayNow) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.setOnChangePlayNow(onChangePlayNow);
    }

    public void setOnMediaPlayerChangeListener(SongServiceContract.OnMediaPlayerChangeListener onMediaPlayerChangeListener) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.setMediaPlayerChangeListener(onMediaPlayerChangeListener);
    }

    public void setOnMiniPlayerChangeListener(SongServiceContract.OnMiniPlayerChangeListener onMiniPlayerChangeListener) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.setMiniPlayerChangeListener(onMiniPlayerChangeListener);
    }

    public void setOnChangeButtonMediaPlayer(SongServiceContract.OnChangeButtonMediaPlayer onChangeButtonMediaPlayer) {
        if (mSongManager == null) {
            return;
        }
        mSongManager.setOnChangeButtonMediaPlayer(onChangeButtonMediaPlayer);
    }


    public void createNotification(String title, String singer, String url) {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        setDataForNotification(title, singer);
        createIntentNotify();
        buildNotify(url);
    }

    private void createIntentNotify() {
        createIntent(R.id.image_notify_next, ACTION_NEXT_TRACK, REQUEST_CODE_NEXT);
        createIntent(R.id.image_notify_previous, ACTION_PREVIOUS_TRACK,
                REQUEST_CODE_PREVIOUS);
        createIntent(R.id.image_notify_pause, ACTION_CHANGE_STATE, REQUEST_CODE_PAUSE);
        createIntent(R.id.image_notify_clear, ACTION_MEDIA_CLEAR, REQUEST_CODE_CLEAR);
    }

    private void createIntent(int id, String action, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setClass(getApplicationContext(), SongService.class);
        PendingIntent pendingIntent =
                PendingIntent.getService(getApplicationContext(), requestCode, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    private void buildNotify(String url) {
        Intent intent = PlayActivity.getPlayerIntent(getApplicationContext());
        PendingIntent pendingIntent =
                PendingIntent.getActivities(getApplicationContext(), (int) System.currentTimeMillis(),
                        new Intent[]{intent}, 0);
        Notification.Builder notificationBuilder =
                new Notification.Builder(getApplicationContext());
        mNotification = notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContent(mRemoteViews)
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buildChannel(notificationBuilder);
        }
        loadImageNotification(url);
        startForeground(ID_NOTIFICATION, mNotification);

    }

    private void loadImageNotification(String url) {
        NotificationTarget notificationTarget = new NotificationTarget(
                getApplicationContext(),
                R.id.image_notify_avatar,
                mRemoteViews,
                mNotification,
                ID_NOTIFICATION);
        NotificationTarget notificationTargetBackGround = new NotificationTarget(
                getApplicationContext(),
                R.id.image_background_notification,
                mRemoteViews,
                mNotification,
                ID_NOTIFICATION);
        if (url != null && !url.equals("null")) {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(url)
                    .into(notificationTarget);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(url)
                    .apply(bitmapTransform(new BlurTransformation(25, 25)))
                    .into(notificationTargetBackGround);
        } else {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(R.drawable.image_default)
                    .into(notificationTarget);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(R.drawable.image_top_country)
                    .apply(bitmapTransform(new BlurTransformation(25, 25)))
                    .into(notificationTargetBackGround);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildChannel(android.app.Notification.Builder notificationBuilder) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        CharSequence name = getString(R.string.app_name);
        NotificationChannel mChannel =
                new NotificationChannel(CHANNEL_ID_NOTIFY, name, importance);
        mNotification = notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(CHANNEL_ID_NOTIFY)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(ID_NOTIFICATION, mNotification);
        }
    }

    private void setDataForNotification(String title, String singer) {
        mRemoteViews.setTextViewText(R.id.text_notify_name, title);
        mRemoteViews.setTextViewText(R.id.text_notify_singer, singer);
        mRemoteViews.setImageViewResource(R.id.image_notify_clear, R.drawable.ic_power_settings_new_white);
        mRemoteViews.setImageViewResource(R.id.image_notify_next, R.drawable.ic_skip_next_white);
        mRemoteViews.setImageViewResource(R.id.image_notify_pause, R.drawable.ic_pause_mini);
        mRemoteViews.setImageViewResource(R.id.image_notify_previous, R.drawable.ic_skip_previous_white);
    }

    public void updateNotificationChangeTrack(Track track) {
        mRemoteViews.setTextViewText(R.id.text_notify_name, track.getTitle());
        mRemoteViews.setTextViewText(R.id.text_notify_singer,
                track.getArtist());
        loadImageNotification(track.getArtworkUrl());
        startForeground(ID_NOTIFICATION, mNotification);
    }

    public void updateNotificationState() {
        if (mSongManager.isPlaying()) {
            mRemoteViews.setImageViewResource(R.id.image_notify_pause, R.drawable.ic_pause_mini);
        } else {
            mRemoteViews.setImageViewResource(R.id.image_notify_pause, R.drawable.ic_play_mini);
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    public class MusicIntentReceiver extends BroadcastReceiver {
        private int mCount = SharedPrefs.getInstance().get(PREF_HEADSET_PLUG, Integer.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                return;
            }
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra(PREF_STATE_ACTION, -1);
                switch (state) {
                    case 0:
                        if (mSongManager == null) {
                            return;
                        }
                        mSongManager.pauseTrack();
                        updateNotificationState();
                        mCount = 0;
                        SharedPrefs.getInstance().put(PREF_HEADSET_PLUG, mCount);
                        break;
                    case 1:
                        if (mSongManager == null) {
                            return;
                        }
                        if (mCount == 0) {
                            mSongManager.resumeTrack();
                            updateNotificationState();
                        }
                        mCount++;
                        SharedPrefs.getInstance().put(PREF_HEADSET_PLUG, mCount);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
