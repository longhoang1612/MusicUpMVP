package music_38.framgia.com.musicup.screen.playlist;

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
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.remote.TrackRemoteDataSource;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.dialog.BottomSheetDialog;
import music_38.framgia.com.musicup.screen.main.OnHideViewCallback;
import music_38.framgia.com.musicup.screen.play.PlayActivity;
import music_38.framgia.com.musicup.screen.selectsong.SelectSongFragment;
import music_38.framgia.com.musicup.service.SongService;
import music_38.framgia.com.musicup.utils.EndlessRecyclerViewScrollListener;
import music_38.framgia.com.musicup.utils.FragmentTransactionUtils;
import music_38.framgia.com.musicup.utils.Utils;

public class PlayListFragment extends BaseFragment implements PlayListContract.View,
        AppBarLayout.OnOffsetChangedListener, PlayListAdapter.OnItemPlaylistClickListener,
        View.OnClickListener {

    public static final String TAG = PlayListFragment.class.getName();
    public static final String BUNDLE_GENRE = "BUNDLE_GENRE";
    private static final int LIMIT = 20;
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mTextTitleToolbar;
    private PlayListPresenter mDetailPresenter;
    private TextView mTextTitlePlaylist;
    private TextView mTextNumberPlaylist;
    private SimpleDraweeView mImageViewPlayList;
    private SimpleDraweeView mImageBehindPlaylist;
    private RecyclerView mRecyclerPlayList;
    private OnHideViewCallback mOnHideViewCallback;
    private Genre mGenre;
    /**
     * Check CollapsingToolbar expanded or closed
     **/
    private boolean mIsShow = true;
    /**
     * Scroll Range by appbar
     **/
    private int mScrollRange = -1;
    private int mPage = LIMIT;
    private boolean mIsLoading;
    private List<Track> mTracks;
    private PlayListAdapter mPlayListAdapter;

    public static PlayListFragment newInstance(Genre genre) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_GENRE, genre);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PlayListFragment() {
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
        mGenre = bundle.getParcelable(BUNDLE_GENRE);
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
        ImageView imageBack = view.findViewById(R.id.ic_back);
        imageAddTrackToPlaylist.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        setUpToolbar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        TrackRemoteDataSource trackRemoteDataSource = new TrackRemoteDataSource();
        TrackRepository trackRepository = new TrackRepository(trackRemoteDataSource);
        mDetailPresenter = new PlayListPresenter(trackRepository);
        mDetailPresenter.setView(this);
        initRecyclerView();
        loadData();
    }

    @Override
    public void getDataTrackSuccess(Genre genre) {
        if (genre == null) {
            return;
        }
        mPlayListAdapter.removeLoadingIndicator();
        mPlayListAdapter.clearData();
        mPlayListAdapter.addData((ArrayList<Track>) genre.getTracks());
        mIsLoading = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mTracks.size())
                .append(getString(R.string.title_blank))
                .append(getString(R.string.title_song));
        mTextNumberPlaylist.setText(stringBuilder);
        mTextTitleToolbar.setText(genre.getTitle());
        mTextTitlePlaylist.setText(genre.getTitle());
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void displayPlaylistBanner(String urlImage) {
        Utils.blurImageWithFresco(mImageBehindPlaylist, Uri.parse(urlImage));
        mImageViewPlayList.setImageURI(Uri.parse(urlImage));
    }

    @Override
    public void getDataError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void loadData() {
        if (mIsLoading) {
            return;
        }
        mPlayListAdapter.addLoadingIndicator();
        mDetailPresenter.getTrackByGenre(mGenre, mPage);
        mIsLoading = true;
    }

    private void initRecyclerView() {
        mTracks = new ArrayList<>();
        mPlayListAdapter = new PlayListAdapter(mTracks, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerPlayList.setLayoutManager(linearLayoutManager);
        mRecyclerPlayList.setAdapter(mPlayListAdapter);

        mRecyclerPlayList.addOnScrollListener(new EndlessRecyclerViewScrollListener(
                linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (mIsLoading) {
                    return;
                }
                mPage += LIMIT;
                loadData();
            }
        });
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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(track, tracks, position);
        if (getFragmentManager() != null) {
            bottomSheetDialog.show(getFragmentManager(), BottomSheetDialog.TAG);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_music_to_playlist:
                SelectSongFragment fragment = SelectSongFragment.newInstance(mGenre);
                FragmentTransactionUtils.addFragment(
                        getFragmentManager(),
                        fragment,
                        R.id.container_full,
                        SelectSongFragment.TAG, true,
                        R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                break;
            case R.id.ic_back:
                if (getFragmentManager() == null) {
                    return;
                }
                getFragmentManager().popBackStack();
                break;
        }
    }
}
