package music_38.framgia.com.musicup.screen.selectsong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.screen.base.BaseFragment;
import music_38.framgia.com.musicup.screen.dialog.DialogAddMoreItemFragment;

public class SelectSongFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = SelectSongFragment.class.getName();
    private static String BUNDLE_GENRE = "BUNDLE_GENRE_ADD";
    private RecyclerView mRecyclerSong;
    private ImageView mImageCheckAll;
    private ImageView mImageAddList;
    private ImageView mImageBack;
    private Genre mGenre;
    private boolean mIsCheckCb;
    private List<Track> mFavorites = new ArrayList<>();
    private SelectedSongAdapter selectedSongAdapter;

    public static SelectSongFragment newInstance(Genre genre) {
        SelectSongFragment fragment = new SelectSongFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_GENRE, genre);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle == null) {
            return;
        }
        mGenre = bundle.getParcelable(BUNDLE_GENRE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_select_song;
    }

    @Override
    protected void initComponent(View view) {
        mRecyclerSong = view.findViewById(R.id.rv_selected_song);
        mImageCheckAll = view.findViewById(R.id.cb_check_all);
        mImageAddList = view.findViewById(R.id.ic_add_list);
        mImageBack = view.findViewById(R.id.ic_back);
        initListener();
    }

    private void initListener() {
        mImageCheckAll.setOnClickListener(this);
        mImageAddList.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initRecyclerView(false);
    }

    private void initRecyclerView(boolean isCheckCb) {
        List<Track> tracks = new ArrayList<>(mGenre.getTracks());
        selectedSongAdapter = new SelectedSongAdapter(tracks, isCheckCb);
        mRecyclerSong.setAdapter(selectedSongAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_check_all:
                if (mIsCheckCb) {
                    mImageCheckAll.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    mIsCheckCb = false;
                } else {
                    mImageCheckAll.setImageResource(R.drawable.ic_check_circle);
                    mIsCheckCb = true;
                }
                initRecyclerView(mIsCheckCb);
                break;
            case R.id.ic_add_list:
                if (selectedSongAdapter == null) {
                    return;
                }

                List<Track> tracks = selectedSongAdapter.getDataList();
                int countItemTrack = 0;
                for (int i = 0; i < tracks.size(); i++) {
                    Track track = tracks.get(i);
                    if (track.isCheck()) {
                        countItemTrack = 1;
                        break;
                    }
                }

                if (countItemTrack != 1) {
                    Toast.makeText(getContext(), R.string.no_music_choose, Toast.LENGTH_SHORT).show();
                } else {
                    for (Track track : selectedSongAdapter.getDataList()
                            ) {
                        if (track.isCheck()) {
                            mFavorites.add(track);
                        }
                    }


                    DialogAddMoreItemFragment dialog = DialogAddMoreItemFragment.newInstance(mFavorites);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    dialog.show(fragmentManager, DialogAddMoreItemFragment.TAG);
                }
                break;
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }
}
