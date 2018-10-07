package music_38.framgia.com.musicup.screen.offline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.DetailViewHolder> {

    private List<Track> mTracks;
    private OnItemPlaylistClickListener mListener;
    private LayoutInflater mLayoutInflater;

    OfflineAdapter(List<Track> tracks, OnItemPlaylistClickListener listener) {
        mTracks = tracks;
        mListener = listener;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_playlist, viewGroup, false);
        return new DetailViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder detailViewHolder, int i) {
        Track track = mTracks.get(i);
        detailViewHolder.bindData(mTracks, track);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView mImageTrack;
        private TextView mTextTrack;
        private TextView mTextAuthor;
        private ImageView mImageMore;
        private OnItemPlaylistClickListener mListener;
        private List<Track> mTracks;

        DetailViewHolder(@NonNull View itemView, OnItemPlaylistClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageTrack = itemView.findViewById(R.id.image_song);
            mTextTrack = itemView.findViewById(R.id.text_title_song);
            mTextAuthor = itemView.findViewById(R.id.text_auth_song);
            mImageMore = itemView.findViewById(R.id.icon_more_song);

            itemView.setOnClickListener(this);
            mImageMore.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.constraint_item_playlist:
                    mListener.onItemPlaylistClick(mTracks, getAdapterPosition());
                    break;
                case R.id.icon_more_song:
                    break;
            }
        }

        private void bindData(List<Track> tracks, Track track) {
            if (track == null) {
                return;
            }
            mTracks = tracks;
            mTextAuthor.setText(track.getArtist());
            mTextTrack.setText(track.getTitle());
            mImageTrack.setImageResource(R.drawable.image_default);
        }
    }

    public interface OnItemPlaylistClickListener {
        void onItemPlaylistClick(List<Track> tracks, int position);
    }
}
