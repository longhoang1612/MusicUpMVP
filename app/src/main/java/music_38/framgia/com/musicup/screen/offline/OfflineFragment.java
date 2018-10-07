package music_38.framgia.com.musicup.screen.offline;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.local.TrackLocalDataSource;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.main.OnHideViewCallback;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.utils.Constants;
import music_38.framgia.com.musicup.utils.Utils;

public class OfflineFragment extends BaseFragment implements OfflineAdapter.OnItemPlaylistClickListener
        , AppBarLayout.OnOffsetChangedListener, OfflineContract.View, View.OnClickListener {

    private RecyclerView mRecyclerOffline;
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mTextTitleToolbar;
    private TextView mTextTitlePlaylist;
    private TextView mTextNumberPlaylist;
    private SimpleDraweeView mImageViewPlayList;
    private SimpleDraweeView mImageBehindPlaylist;
    private OnHideViewCallback mOnHideViewCallback;
    /**
     * Check CollapsingToolbar expanded or closed
     **/
    private boolean mIsShow = true;
    /**
     * Scroll Range by appbar
     **/
    private int mScrollRange = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHideViewCallback) {
            mOnHideViewCallback = (OnHideViewCallback) activity;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() == null) {
            return;
        }
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onStart() {
        super.onStart();
        mOnHideViewCallback.onHideBottomBar(View.GONE);
    }

    @Override
    public void onDestroy() {
        if (getActivity() == null) {
            return;
        }
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mOnHideViewCallback.onHideBottomBar(View.VISIBLE);
        mOnHideViewCallback.onHideBottomBar(View.VISIBLE);
        super.onDestroy();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offline;
    }

    @Override
    protected void initComponent(View view) {
        mRecyclerOffline = view.findViewById(R.id.recycler_play_list);
        mToolbar = view.findViewById(R.id.toolbar);
        mAppBar = view.findViewById(R.id.app_bar);
        mCollapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        mTextTitleToolbar = view.findViewById(R.id.tv_toolbar_playlist);
        mTextNumberPlaylist = view.findViewById(R.id.text_number_playlist);
        mTextTitlePlaylist = view.findViewById(R.id.title_playlist);
        mImageViewPlayList = view.findViewById(R.id.iv_playlist);
        mImageBehindPlaylist = view.findViewById(R.id.iv_behind_playlist);
        ImageView imageBack = view.findViewById(R.id.ic_back);
        setUpToolbar();

        imageBack.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        TrackLocalDataSource trackLocalDataSource = TrackLocalDataSource.getsInstance(getContext());
        TrackRepository trackRepository = new TrackRepository(trackLocalDataSource);
        OfflinePresenter offlinePresenter = new OfflinePresenter(trackRepository);
        offlinePresenter.setView(this);
        offlinePresenter.getTotalLocalMusicLocal();
    }

    @Override
    public void onItemPlaylistClick(List<Track> tracks, int position) {
        mOnHideViewCallback.onHideMiniPlayer(View.VISIBLE);
        Intent intentService = SongService.getIntentService(getContext(), tracks, position);
        if (getActivity() == null) {
            return;
        }
        getActivity().startService(intentService);
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        startActivity(intent);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mScrollRange == -1) {
            mScrollRange = appBarLayout.getTotalScrollRange();
        }
        if (mScrollRange + verticalOffset == 0) {
            mCollapsingToolbar.setTitle(getString(R.string.title_default));
            mTextTitleToolbar.setVisibility(View.VISIBLE);
            mIsShow = true;
        } else if (mIsShow) {
            mCollapsingToolbar.setTitle(getString(R.string.title_blank));
            mTextTitleToolbar.setVisibility(View.GONE);
            mIsShow = false;
        }
    }

    @Override
    public void onGetTrackLocal(List<Track> tracks) {
        initRecyclerView(tracks);
        setUpView(tracks);
    }

    private void setUpToolbar() {
        if (getActivity() == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mAppBar.setExpanded(false);
        mAppBar.addOnOffsetChangedListener(this);
    }

    private void initRecyclerView(List<Track> tracks) {
        OfflineAdapter offlineAdapter = new OfflineAdapter(tracks, this);
        mRecyclerOffline.setHasFixedSize(true);
        mRecyclerOffline.setAdapter(offlineAdapter);
    }

    private void setUpView(List<Track> tracks) {
        mTextTitlePlaylist.setText(R.string.offline);
        mTextTitleToolbar.setText(R.string.offline);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tracks.size())
                .append(getString(R.string.title_blank))
                .append(getString(R.string.title_song));
        mTextNumberPlaylist.setText(stringBuilder);
        Uri uri = Uri.parse(Constants.URI_IMAGE_PLAYLIST);
        Utils.blurImageWithFresco(mImageBehindPlaylist, uri);
        mImageViewPlayList.setImageResource(R.drawable.image_top_country);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }
}
