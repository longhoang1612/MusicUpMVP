package music_38.framgia.com.musicup.screen.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BaseActivity;
import music_38.framgia.com.musicup.screen.home.HomeFragment;
import music_38.framgia.com.musicup.screen.person.PersonFragment;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.screen.search.SearchFragment;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.service.SongServiceContract;
import music_38.framgia.com.musicup.utils.Constants;
import music_38.framgia.com.musicup.utils.FragmentTransactionUtils;
import music_38.framgia.com.musicup.utils.Utils;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener
        , OnHideViewCallback, SongServiceContract.OnMiniPlayerChangeListener {

    private static int DELAY_SEEK_BAR = 100;
    private BottomNavigationView mBottomBar;
    private ConstraintLayout mConstraintLayout;
    private Group mGroup;
    private ImageView mImageInfo;
    private ImageView mImagePlay;
    private ImageView mImageNext;
    private TextView mTextTitle;
    private TextView mTextSong;
    private TextView mTextAuth;
    private SimpleDraweeView mImageMini;
    private SimpleDraweeView mImageAll;
    private Handler mHandler;
    private Runnable mRunnable;
    private SeekBar mSeekBar;
    private boolean mMusicBound;
    private SongServiceContract.ISongService mISongService;

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName mName, IBinder mService) {
            SongService.SongBinder binder = (SongService.SongBinder) mService;
            SongService mMusicSrv = binder.getService();
            if (mMusicSrv != null) {
                mISongService = mMusicSrv.getISongService();
                Track mTrack = mMusicSrv.getTrackCurrent();
                mMusicSrv.setOnMiniPlayerChangeListener(MainActivity.this);
                if (mTrack != null) {
                    updateUI(mTrack);
                }
                mHandler = new Handler();
                mHandler.postDelayed(mRunnable, DELAY_SEEK_BAR);
            }
            mMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        initView();
        initListener();
        openDefaultFragment();
    }

    private void initView() {
        mBottomBar = findViewById(R.id.bottom_bar);
        mTextTitle = findViewById(R.id.text_title);
        mImageInfo = findViewById(R.id.image_info);
        mGroup = findViewById(R.id.constraint_group);
        mConstraintLayout = findViewById(R.id.constraint_mini_play);
        mImageMini = findViewById(R.id.image_mini);
        mImageAll = findViewById(R.id.image_all_mini);
        mTextSong = findViewById(R.id.title_mini_play);
        mTextAuth = findViewById(R.id.auth_mini_play);
        mImagePlay = findViewById(R.id.image_play);
        mImageNext = findViewById(R.id.image_next);
        mSeekBar = findViewById(R.id.sb_mini);
    }

    private void initListener() {
        mBottomBar.setOnNavigationItemSelectedListener(this);
        mImageInfo.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mConstraintLayout.setOnClickListener(this);
    }

    private void openDefaultFragment() {
        mTextTitle.setText(R.string.title_discover);
        mBottomBar.setSelectedItemId(R.id.action_home);
        FragmentTransactionUtils.addFragment(getSupportFragmentManager(), new HomeFragment(),
                R.id.container, HomeFragment.TAG);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;

        switch (menuItem.getItemId()) {
            case R.id.action_home:
                fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                mTextTitle.setText(R.string.title_discover);
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(), fragment,
                        R.id.container, HomeFragment.TAG);
                mGroup.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_trending:
                fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
                if (fragment == null) {
                    fragment = new SearchFragment();
                }
                mTextTitle.setText(R.string.title_trending);
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(), fragment,
                        R.id.container, SearchFragment.TAG);
                mGroup.setVisibility(View.GONE);
                return true;
            case R.id.action_person:
                fragment = getSupportFragmentManager().findFragmentByTag(PersonFragment.TAG);
                if (fragment == null) {
                    fragment = new PersonFragment();
                }
                mTextTitle.setText(R.string.title_person);
                mGroup.setVisibility(View.VISIBLE);
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(), fragment,
                        R.id.container, PersonFragment.TAG);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_info:
                //TODO: info
                break;
            case R.id.image_next:
                mISongService.nextSong();
                break;
            case R.id.image_play:
                mISongService.playPauseSong();
                break;
            case R.id.constraint_mini_play:
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent mPlayIntent = new Intent(getApplicationContext(), SongService.class);
        bindService(mPlayIntent, mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (mMusicBound) {
            unbindService(mMusicConnection);
            mMusicBound = false;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHideBottomBar(int visibility) {
        mBottomBar.setVisibility(visibility);
    }

    @Override
    public void onHideMiniPlayer(int visibility) {
        mConstraintLayout.setVisibility(visibility);
    }

    private void updateUI(Track track) {
        mTextAuth.setText(track.getArtist());
        mTextSong.setText(track.getTitle());
        Uri uri;
        if (track.getArtworkUrl() == null) {
            uri = Uri.parse(Constants.URI_IMAGE);
            Utils.blurImageWithFresco(mImageAll, Uri.parse(Constants.URI_IMAGE_PLAYLIST));
        } else {
            uri = Uri.parse(track.getArtworkUrl());
            Utils.blurImageWithFresco(mImageAll, uri);
        }
        Utils.roundImageWithFresco(getApplicationContext(), mImageMini, uri);

        if (track.getDuration() != 0) {
            mSeekBar.setMax(track.getDuration());
        } else {
            mSeekBar.setMax(mISongService.getDurationSong());
        }
        updateSeekBar();
    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        if (isPlaying) {
            mImagePlay.setImageResource(R.drawable.ic_pause_mini);
        } else {
            mImagePlay.setImageResource(R.drawable.ic_play_mini);
        }
    }

    @Override
    public void onTrackChange(Track track) {
        updateUI(track);
    }

    private void updateSeekBar() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                int currentDuration = mISongService.getCurrentDurationSong();
                mSeekBar.setMax(mISongService.getDurationSong());
                mSeekBar.setProgress(currentDuration);
                mHandler.postDelayed(mRunnable, DELAY_SEEK_BAR);
            }
        };
    }
}
