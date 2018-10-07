package music_38.framgia.com.musicup.screen.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.repository.TrackRepository;
import music_38.framgia.com.musicup.data.source.local.GenreLocalDataSource;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.playlist.PlayListFragment;
import music_38.framgia.com.musicup.utils.FragmentTransactionUtils;

public class HomeFragment extends BaseFragment implements HomeContract.View, HomeAdapter.OnItemClickListener {

    public static final String TAG = HomeFragment.class.getName();

    private RecyclerView mRecyclerGenre;
    private ProgressBar mProgressBar;
    private HomePresenter mHomePresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponent(View view) {
        mRecyclerGenre = view.findViewById(R.id.recycler_genre);
        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        GenreLocalDataSource genreLocalDataSource = new GenreLocalDataSource();
        mHomePresenter = new HomePresenter(new TrackRepository(genreLocalDataSource));
        mHomePresenter.setView(this);
        loadData();
    }

    @Override
    public void onGetGenreSuccess(ArrayList<Genre> data) {
        initRecyclerView(data);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGetGenreError(Exception e) {
        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickHomeListener(Genre genre) {
        PlayListFragment fragment = PlayListFragment.newInstance(genre);
        FragmentTransactionUtils.addFragment(
                getFragmentManager(),
                fragment,
                R.id.container_full,
                PlayListFragment.TAG, true,
                R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
    }

    private void loadData() {
        mHomePresenter.getGenre();
    }

    private void initRecyclerView(ArrayList<Genre> data) {
        HomeAdapter homeAdapter = new HomeAdapter(data, this);
        mRecyclerGenre.setHasFixedSize(true);
        mRecyclerGenre.setAdapter(homeAdapter);
    }
}
