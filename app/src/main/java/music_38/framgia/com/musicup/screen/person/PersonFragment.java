package music_38.framgia.com.musicup.screen.person;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.GenreSave;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.download.DownloadFragment;
import music_38.framgia.com.musicup.screen.favorites.FavoritesFragment;
import music_38.framgia.com.musicup.screen.offline.OfflineFragment;
import music_38.framgia.com.musicup.screen.playlist.PlayListFragment;
import music_38.framgia.com.musicup.screen.playlistfavorites.FavoritesPlayListFragment;
import music_38.framgia.com.musicup.utils.FragmentTransactionUtils;

public class PersonFragment extends BaseFragment implements PersonContract.View, View.OnClickListener, FavoritesPlayListAdapter.OnItemPlaylistClickListener {

    public static final String TAG = PersonFragment.class.getName();
    private RecyclerView mRecyclerFavoritesPlaylist;
    private TextView mTextOffline;
    private TextView mTextFavorites;
    private TextView mTextDownload;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initComponent(View view) {
        mRecyclerFavoritesPlaylist = view.findViewById(R.id.recycler_playlist_favorites);
        mTextOffline = view.findViewById(R.id.text_offline);
        mTextFavorites = view.findViewById(R.id.text_fav);
        mTextDownload = view.findViewById(R.id.text_download);
        initListener();
    }

    private void initListener() {
        mTextOffline.setOnClickListener(this);
        mTextFavorites.setOnClickListener(this);
        mTextDownload.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        PersonPresenter presenter = new PersonPresenter();
        presenter.setView(this);
        presenter.getPlayListFavorites();
    }

    @Override
    public void getDataSuccess(List<GenreSave> genreSaves) {
        initRecyclerView(genreSaves);
    }

    private void initRecyclerView(List<GenreSave> genreSaves) {
        FavoritesPlayListAdapter favoritesPlayListAdapter = new FavoritesPlayListAdapter(genreSaves, this);
        mRecyclerFavoritesPlaylist.setAdapter(favoritesPlayListAdapter);
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void getDataError() {
        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_offline:
                FragmentTransactionUtils.addFragment(
                        getFragmentManager(),
                        new OfflineFragment(),
                        R.id.container_full,
                        PlayListFragment.TAG, true,
                        R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                break;
            case R.id.text_fav:
                FragmentTransactionUtils.addFragment(
                        getFragmentManager(),
                        new FavoritesFragment(),
                        R.id.container_full,
                        PlayListFragment.TAG, true,
                        R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                break;
            case R.id.text_download:
                FragmentTransactionUtils.addFragment(
                        getFragmentManager(),
                        new DownloadFragment(),
                        R.id.container_full,
                        DownloadFragment.TAG, true,
                        R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                break;
        }
    }

    @Override
    public void onItemPlaylistClick(GenreSave genreSave) {
        FavoritesPlayListFragment fragment = FavoritesPlayListFragment.newInstance(genreSave);
        FragmentTransactionUtils.addFragment(
                getFragmentManager(),
                fragment,
                R.id.container_full,
                FavoritesPlayListFragment.TAG, true,
                R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
    }
}
