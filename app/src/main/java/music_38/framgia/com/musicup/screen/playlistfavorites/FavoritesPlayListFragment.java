package music_38.framgia.com.musicup.screen.playlistfavorites;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.GenreSave;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.main.OnHideViewCallback;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.screen.playlist.PlayListAdapter;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.utils.Utils;

public class FavoritesPlayListFragment extends BaseFragment implements
        AppBarLayout.OnOffsetChangedListener, PlayListAdapter.OnItemPlaylistClickListener,
        View.OnClickListener {

    public static final String TAG = FavoritesPlayListFragment.class.getName();
    private static final String BUNDLE_GENRE_SAVE = "BUNDLE_GENRE_SAVE";
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mTextTitleToolbar;
    private TextView mTextTitlePlaylist;
    private TextView mTextNumberPlaylist;
    private SimpleDraweeView mImageViewPlayList;
    private SimpleDraweeView mImageBehindPlaylist;
    private RecyclerView mRecyclerPlayList;
    private OnHideViewCallback mOnHideViewCallback;
    private GenreSave mGenre;

    /**
     * Check CollapsingToolbar expanded or closed
     **/
    private boolean mIsShow = true;
    /**
     * Scroll Range by appbar
     **/
    private int mScrollRange = -1;

    public static FavoritesPlayListFragment newInstance(GenreSave genre) {
        FavoritesPlayListFragment fragment = new FavoritesPlayListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_GENRE_SAVE, genre);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHideViewCallback) {
            mOnHideViewCallback = (OnHideViewCallback) activity;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_play_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnHideViewCallback.onHideBottomBar(View.GONE);
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }
        mGenre = bundle.getParcelable(BUNDLE_GENRE_SAVE);
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
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() == null) {
            return;
        }
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mOnHideViewCallback.onHideBottomBar(View.VISIBLE);
    }

    @Override
    protected void initComponent(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mAppBar = view.findViewById(R.id.app_bar);
        mCollapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        mTextTitleToolbar = view.findViewById(R.id.tv_toolbar_playlist);
        mRecyclerPlayList = view.findViewById(R.id.recycler_play_list);
        mTextNumberPlaylist = view.findViewById(R.id.text_number_playlist);
        mTextTitlePlaylist = view.findViewById(R.id.title_playlist);
        mImageViewPlayList = view.findViewById(R.id.iv_playlist);
        mImageBehindPlaylist = view.findViewById(R.id.iv_behind_playlist);
        ImageView imageAddTrackToPlaylist = view.findViewById(R.id.add_music_to_playlist);
        imageAddTrackToPlaylist.setOnClickListener(this);
        setUpToolbar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        TrackRemoteDataSource trackRemoteDataSource = new TrackRemoteDataSource();
//        TrackRepository trackRepository = new TrackRepository(trackRemoteDataSource);
//        mDetailPresenter = new PlayListPresenter(trackRepository);
//        mDetailPresenter.setView(this);
        loadData(mGenre);
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

    private void setUpToolbar() {
        if (getActivity() == null) {
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mAppBar.setExpanded(false);
        mAppBar.addOnOffsetChangedListener(this);
    }

    private void loadData(GenreSave genreSave) {
        updateUI(genreSave);
        initRecyclerView(genreSave);
    }

    private void updateUI(GenreSave genreSave) {
        mTextTitlePlaylist.setText(genreSave.getTitle());
        mTextTitleToolbar.setText(genreSave.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(genreSave.getTracks().size())
                .append(getString(R.string.title_blank))
                .append(getString(R.string.title_song));
        mTextNumberPlaylist.setText(stringBuilder);
        Uri uri = Uri.parse(genreSave.getImage());
        Utils.blurImageWithFresco(mImageBehindPlaylist, uri);
        mImageViewPlayList.setImageURI(uri);
    }

    private void initRecyclerView(GenreSave genreSave) {
        List<Track> tracks = new ArrayList<>(genreSave.getTracks());
        PlayListAdapter playListAdapter = new PlayListAdapter(tracks, this);
        mRecyclerPlayList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerPlayList.setHasFixedSize(true);
        mRecyclerPlayList.setAdapter(playListAdapter);
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
    public void onItemBottomSheet(List<Track> tracks, Track track, int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_music_to_playlist:
//                SelectSongFragment fragment = SelectSongFragment.newInstance(mGenre);
//                FragmentTransactionUtils.addFragment(
//                        getFragmentManager(),
//                        fragment,
//                        R.id.container_full,
//                        SelectSongFragment.TAG, true,
//                        R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                break;
        }
    }
}
