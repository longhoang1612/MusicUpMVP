package music_38.framgia.com.musicup.screen.play.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.PlayModeRepository;
import music_38.framgia.com.musicup.data.source.local.PlayMode;
import music_38.framgia.com.musicup.data.source.local.PlayModeLocalDataSource;
import music_38.framgia.com.musicup.data.source.local.realm.RealmDownloadSong;
import music_38.framgia.com.musicup.data.source.local.realm.RealmFavoritesSong;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.play.PlayPresenter;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.service.SongServiceContract;
import music_38.framgia.com.musicup.utils.Constants;
import music_38.framgia.com.musicup.utils.Utils;

import static music_38.framgia.com.musicup.data.model.DownloadMode.DOWNLOAD_ABLE;
import static music_38.framgia.com.musicup.data.model.DownloadMode.DOWNLOAD_DISABLE;
import static music_38.framgia.com.musicup.data.model.FavoritesMode.FAVORITES;
import static music_38.framgia.com.musicup.data.model.FavoritesMode.UN_FAVORITES;

public class PlayControlFragment extends BaseFragment implements SongServiceContract.OnChangeButtonMediaPlayer, View.OnClickListener {
    private SimpleDraweeView mImageSong;
    private ImageView mImageDownload;
    private ImageView mImageFav;
    private ImageView mImageBottom;
    private SongServiceContract.ISongService mISongService;
    private boolean mMusicBound;
    private PlayPresenter mPlayPresenter;
    private PlayMode mPlayMode;

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;
            SongService songService = binder.getService();
            mISongService = songService.getISongService();
            Track track = songService.getTrackCurrent();
            songService.setOnChangeButtonMediaPlayer(PlayControlFragment.this);
            if (track != null) {
                updateUI(track);
            }
            mMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() == null) {
            return;
        }
        Intent mPlayIntent = new Intent(getBaseActivity(), SongService.class);
        getContext().bindService(mPlayIntent, mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        if (!mMusicBound) {
            if (getContext() == null) {
                return;
            }
            getContext().unbindService(mMusicConnection);
            mMusicBound = false;
        }
        super.onDestroy();
    }

    private void updateUI(Track track) {
        Uri uri;
        if (getContext() == null) {
            return;
        }
        if (track.getArtworkUrl() != null && !track.getArtworkUrl().equals("null")) {
            uri = Uri.parse(track.getArtworkUrl());
        } else {
            uri = Uri.parse(Constants.URI_IMAGE);
        }
        Utils.roundImageWithFresco(getContext(), mImageSong, uri);
        //Downloadable
        if (track.isDownloadable()) {
            mImageDownload.setVisibility(View.VISIBLE);
        } else {
            mImageDownload.setVisibility(View.GONE);
        }
        //UPDATE FAVORITES
        int favorites;
        if (RealmFavoritesSong.getTrack(track) != null) {
            favorites = FAVORITES;
        } else {
            favorites = UN_FAVORITES;
        }
        updateFavorites(favorites);
        mPlayMode.setFavoritesMode(favorites);
        //UPDATE DOWNLOAD
        int download;
        if (RealmDownloadSong.getTrack(track) != null) {
            download = DOWNLOAD_DISABLE;
        } else {
            download = DOWNLOAD_ABLE;
        }
        updateStateDownload(download);
        mPlayMode.setDownload(download);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_play_control;
    }

    @Override
    protected void initComponent(View view) {
        mImageSong = view.findViewById(R.id.image_song);
        mImageDownload = view.findViewById(R.id.icon_download);
        mImageFav = view.findViewById(R.id.icon_fav);
        mImageBottom = view.findViewById(R.id.icon_bottom);
        initListener();
    }

    private void initListener() {
        mImageBottom.setOnClickListener(this);
        mImageFav.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        PlayModeLocalDataSource playModeLocalDataSource = new PlayModeLocalDataSource();
        PlayModeRepository playModeRepository = new PlayModeRepository(playModeLocalDataSource);
        mPlayPresenter = new PlayPresenter(playModeRepository);
        mPlayMode = mPlayPresenter.getPlayMode();
    }

    @Override
    public void onTrackChange(Track track) {
        updateUI(track);
    }

    @Override
    public void onDownLoadChange(int state) {
        updateStateDownload(state);
    }

    @Override
    public void onFavoritesChange(int state) {
        updateFavorites(state);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_download:
                clickDownload();
                break;
            case R.id.icon_bottom:
                break;
            case R.id.icon_fav:
                clickFavorites();
                break;
        }
    }

    private void clickDownload() {
        int downloadType = DOWNLOAD_ABLE;
        switch (mPlayMode.getDownload()) {
            case DOWNLOAD_ABLE:
                downloadType = DOWNLOAD_DISABLE;
                break;
            case DOWNLOAD_DISABLE:
                downloadType = DOWNLOAD_ABLE;
                break;
        }
        mPlayMode.setDownload(downloadType);
        mPlayPresenter.savePlayMode(mPlayMode);
        mISongService.downloadCurrentTrack(mPlayMode.getDownload());
    }

    private void clickFavorites() {
        int favoritesType = FAVORITES;
        switch (mPlayMode.getFavoritesMode()) {
            case UN_FAVORITES:
                favoritesType = FAVORITES;
                break;
            case FAVORITES:
                favoritesType = UN_FAVORITES;
                break;
        }
        mPlayMode.setFavoritesMode(favoritesType);
        mPlayPresenter.savePlayMode(mPlayMode);
        mISongService.favoritesSong(mPlayMode.getFavoritesMode());
    }

    private void updateFavorites(int state) {
        switch (state) {
            case UN_FAVORITES:
                mImageFav.setImageResource(R.drawable.ic_heart);
                break;
            case FAVORITES:
                mImageFav.setImageResource(R.drawable.ic_favorite_red);
                break;
        }
    }

    private void updateStateDownload(int state) {
        switch (state) {
            case DOWNLOAD_ABLE:
                mImageDownload.setImageResource(R.drawable.ic_cloud_download_white);
                mImageDownload.setClickable(true);
                break;
            case DOWNLOAD_DISABLE:
                mImageDownload.setImageResource(R.drawable.ic_offline_pin_blue_a700);
                mImageDownload.setClickable(false);
                break;
        }
    }
}
