package music_38.framgia.com.musicup.screen.selectsong;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;


public class SelectedSongAdapter extends RecyclerView.Adapter<SelectedSongAdapter.ViewHolder> {
    private List<Track> mTracks;
    private boolean mIsCheck;
    private LayoutInflater mInflater;

    SelectedSongAdapter(List<Track> tracks, boolean isCheck) {
        mTracks = tracks;
        mIsCheck = isCheck;
        loadItems(tracks);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.item_selected_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindData(mTracks.get(position));
        holder.setIsRecyclable(false);
    }

    private void loadItems(List<Track> tracks) {
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle, mTextAuth;
        private SimpleDraweeView mImageList;
        private AppCompatCheckBox mCheckBox;

        ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.title_most_popular);
            mTextAuth = itemView.findViewById(R.id.auth_most_popular);
            mImageList = itemView.findViewById(R.id.iv_popular);
            mCheckBox = itemView.findViewById(R.id.radio_btn);
        }

        void bindData(Track track) {
            mTextTitle.setText(track.getTitle());
            mTextAuth.setText(track.getArtist());
            if (track.getArtworkUrl() != null) {
                Uri uri = Uri.parse(track.getArtworkUrl());
                mImageList.setImageURI(uri);
            }

            mCheckBox.setChecked(mTracks.get(getAdapterPosition()).isCheck());

            mCheckBox.setTag(mTracks.get(getAdapterPosition()));


            mCheckBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AppCompatCheckBox cb = (AppCompatCheckBox) v;
                    Track contact = (Track) cb.getTag();

                    contact.setCheck(cb.isChecked());
                    mTracks.get(getAdapterPosition()).setCheck(cb.isChecked());
                }
            });

            for (Track updateTrack : mTracks
                    ) {
                updateTrack.setCheck(mIsCheck);
                mCheckBox.setChecked(mIsCheck);
            }
        }
    }

    public List<Track> getDataList() {
        return mTracks;
    }
}
