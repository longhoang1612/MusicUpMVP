package music_38.framgia.com.musicup.screen.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.BottomSheet;
import music_38.framgia.com.musicup.data.model.Track;


@SuppressLint("ValidFragment")
public class BottomSheetDialog extends BottomSheetDialogFragment
        implements BottomSheetAdapter.OnItemBottomClickListener {

    public static final String TAG = BottomSheetDialog.class.getName();
    private RecyclerView mRecyclerView;
    private List<BottomSheet> mSheets = new ArrayList<>();
    private Track mTrack;

    public BottomSheetDialog(Track track, List<Track> tracks, int position) {
        mTrack = track;
        List<Track> tracks1 = tracks;
        int position1 = position;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_playlist, null);
        initView(contentView);
        initData();
        dialog.setContentView(contentView);
    }

    private void initData() {
        BottomSheet bottomSheet1 = new BottomSheet(R.drawable.ic_close_black_24dp, "Add to Favorites");
        BottomSheet bottomSheet3 = new BottomSheet(R.drawable.ic_close_black_24dp, "Add to Playlist");
        BottomSheet bottomSheet4 = new BottomSheet(R.drawable.ic_close_black_24dp, "Play next");
        BottomSheet bottomSheet5 = new BottomSheet(R.drawable.ic_close_black_24dp, "Play previous");
        BottomSheet bottomSheet6 = new BottomSheet(R.drawable.ic_close_black_24dp, "Share");

        mSheets.add(bottomSheet1);
        mSheets.add(bottomSheet3);
        mSheets.add(bottomSheet4);
        mSheets.add(bottomSheet5);
        mSheets.add(bottomSheet6);

        BottomSheetAdapter sheetAdapter = new BottomSheetAdapter(mSheets, this);
        mRecyclerView.setAdapter(sheetAdapter);
    }

    private void initView(View view) {
        SimpleDraweeView imageBottomSheet = view.findViewById(R.id.iv_bottom_sheet);
        TextView textTitleBottomSheet = view.findViewById(R.id.tv_title_bottom_sheet);
        TextView textAuthBottomSheet = view.findViewById(R.id.tv_auth_bottom_sheet);
        mRecyclerView = view.findViewById(R.id.rv_bottom_sheet);

        Uri uri = Uri.parse(mTrack.getArtworkUrl());
        imageBottomSheet.setImageURI(uri);
        textTitleBottomSheet.setText(mTrack.getTitle());
        textAuthBottomSheet.setText(mTrack.getArtist());
    }

    @Override
    public void onItemBottomSheetClick(BottomSheet bottomSheet) {

    }
}
