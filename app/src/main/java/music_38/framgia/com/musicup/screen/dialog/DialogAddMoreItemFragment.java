package music_38.framgia.com.musicup.screen.dialog;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.GenreSave;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.local.realm.RealmPlayList;

public class DialogAddMoreItemFragment extends DialogFragment implements View.OnClickListener,
        TextView.OnEditorActionListener, CreatePlaylistAdapter.OnItemClickPlayListListener {

    public static final String TAG = DialogAddMoreItemFragment.class.getName();
    private static final String BUNDLE_LIST_TRACK = "BUNDLE_LIST_TRACK";
    private LinearLayout mLinearLayout;
    private RecyclerView mRecyclerPlaylist;
    private List<Track> mTracks;

    public DialogAddMoreItemFragment() {
    }

    public static DialogAddMoreItemFragment newInstance(List<Track> tracks) {
        DialogAddMoreItemFragment fragment = new DialogAddMoreItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_LIST_TRACK, (ArrayList<? extends Parcelable>) tracks);
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
        mTracks = bundle.getParcelableArrayList(BUNDLE_LIST_TRACK);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_add_playlist, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    private void initListener() {
        mLinearLayout.setOnClickListener(this);
    }

    private void initView(View view) {
        mLinearLayout = view.findViewById(R.id.ln_new_playlist);
        mRecyclerPlaylist = view.findViewById(R.id.rv_playlist_new);
    }

    private void initData() {
        List<GenreSave> genreSaves = RealmPlayList.getPLayList();
        if (genreSaves == null) {
            return;
        }
        CreatePlaylistAdapter createPlaylistAdapter
                = new CreatePlaylistAdapter(genreSaves, this);
        mRecyclerPlaylist.setAdapter(createPlaylistAdapter);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        this.dismiss();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_new_playlist:
                alertNewPlayList();
                break;
        }
    }

    private void alertNewPlayList() {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        if (getActivity() == null) {
            return;
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.fragment_dialog_add_list, null);
        final EditText editText = dialogView.findViewById(R.id.et_new_playlist);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton(R.string.msg_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RealmList<Track> trackRealmList = new RealmList<>();
                        trackRealmList.addAll(mTracks);
                        GenreSave genre = new GenreSave(
                                editText.getText().toString(),
                                mTracks.get(0).getArtworkUrl(),
                                trackRealmList);
                        RealmPlayList.addPLayList(genre);
                        initData();
                        Toast.makeText(getContext(), R.string.msg_success, Toast.LENGTH_SHORT).show();
                    }
                });
        dialogBuilder.setNegativeButton(R.string.msg_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onItemClickPlayList(GenreSave genreSave) {
        List<Track> tracks = new ArrayList<>(genreSave.getTracks());
        for (Track track : genreSave.getTracks()
                ) {
            for (int i = 0; i < mTracks.size(); i++) {
                if (mTracks.get(0).getId() == track.getId()) {
                    mTracks.remove(i);
                }
            }
        }
        if (mTracks.size() > 0) {
            tracks.addAll(mTracks);
            RealmList<Track> realmList = new RealmList<>();
            realmList.addAll(tracks);
            GenreSave add = new GenreSave(
                    genreSave.getTitle(),
                    genreSave.getImage(),
                    realmList);
            RealmPlayList.addPLayList(add);
            Toast.makeText(getContext(),
                    getString(R.string.msg_save_success) + genreSave.getTitle(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),
                    getString(R.string.msg_song_already_exists)
                            + genreSave.getTitle() + " \"",
                    Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }
}
