package music_38.framgia.com.musicup.screen.play.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.screen.play.adapter.PlayNowAdapter;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.service.SongServiceContract;

public class PlayNowFragment extends BaseFragment implements SongServiceContract.OnChangePlayNow, PlayNowAdapter.OnItemPlaylistClickListener {

    private SongServiceContract.ISongService mISongService;
    private boolean mMusicBound;
    private RecyclerView mRecyclerPlayNow;

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;
            SongService songService = binder.getService();
            mISongService = songService.getISongService();
            if (mISongService == null) {
                return;
            }
            songService.setOnChangePlayNow(PlayNowFragment.this);
            if (mISongService.getListTrack() == null) {
                return;
            }
            List<Track> tracks = mISongService.getListTrack();
            int position = mISongService.getCurrentPosition();
            initRecyclerView(tracks, position);
            mMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    private void initRecyclerView(List<Track> tracks, int position) {
        PlayNowAdapter playNowAdapter = new PlayNowAdapter(getContext(), tracks, this, position);
        playNowAdapter.setPosition(mISongService.getCurrentPosition());
        mRecyclerPlayNow.setAdapter(playNowAdapter);
        mRecyclerPlayNow.scrollToPosition(mISongService.getCurrentPosition());
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent mPlayIntent = new Intent(getBaseActivity(), SongService.class);
        if (getContext() == null) {
            return;
        }
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

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_play_now;
    }

    @Override
    protected void initComponent(View view) {
        mRecyclerPlayNow = view.findViewById(R.id.recycler_play_now);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onTrackChange(Track track, int position) {

    }

    @Override
    public void onListChange(List<Track> tracks) {
        initRecyclerView(tracks, 0);
    }

    @Override
    public void onItemPlayNowClick(List<Track> tracks, int position) {
        Intent intentService = SongService.getIntentService(
                getContext(),
                tracks,
                position);
        if (getActivity() == null) {
            return;
        }
        getActivity().startService(intentService);
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
