package music_38.framgia.com.musicup.screen.play.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;

public class PlayNowAdapter extends RecyclerView.Adapter<PlayNowAdapter.DetailViewHolder> {

    private List<Track> mTracks;
    private OnItemPlaylistClickListener mListener;
    private LayoutInflater mLayoutInflater;
    private int mPosition;
    private Context mContext;

    public PlayNowAdapter(Context context, List<Track> tracks, OnItemPlaylistClickListener listener, int position) {
        mContext = context;
        mTracks = tracks;
        mListener = listener;
        mPosition = position;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_play_now, viewGroup, false);
        return new DetailViewHolder(view, mListener, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder detailViewHolder, int i) {
        Track track = mTracks.get(i);
        detailViewHolder.bindData(mTracks, track, mPosition);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView mImageTrack;
        private TextView mTextTrack;
        private TextView mTextAuthor;
        private ImageView mImageNow;
        private OnItemPlaylistClickListener mListener;
        private List<Track> mTracks;
        private ConstraintLayout mConstraintLayout;
        private int mPosition;
        private Context mContext;

        DetailViewHolder(@NonNull View itemView, OnItemPlaylistClickListener listener, Context context) {
            super(itemView);
            mListener = listener;
            mContext = context;
            mImageTrack = itemView.findViewById(R.id.image_song);
            mTextTrack = itemView.findViewById(R.id.text_title_song);
            mTextAuthor = itemView.findViewById(R.id.text_auth_song);
            mImageNow = itemView.findViewById(R.id.icon_more_song);
            mConstraintLayout = itemView.findViewById(R.id.constraint_item_playlist);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.constraint_item_playlist:
                    mListener.onItemPlayNowClick(mTracks, getAdapterPosition());
                    break;
            }
        }

        private void bindData(List<Track> tracks, Track track, int position) {
            if (track == null) {
                return;
            }
            mPosition = position;
            mTracks = tracks;
            if (track.getArtworkUrl() != null) {
                mImageTrack.setImageURI(Uri.parse(track.getArtworkUrl()));
            } else {
                mImageTrack.setImageResource(R.drawable.image_top_country);
            }
            mTextAuthor.setText(track.getArtist());
            mTextTrack.setText(track.getTitle());
            if (getAdapterPosition() == mPosition) {
                mImageNow.setVisibility(View.VISIBLE);
                Glide.with(mContext).asGif()
                        .load(R.raw.playnow)
                        .into(mImageNow);
                mConstraintLayout.setBackgroundColor(Color.CYAN);
            } else {
                mConstraintLayout.setBackgroundColor(Color.TRANSPARENT);
                mImageNow.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemPlaylistClickListener {
        void onItemPlayNowClick(List<Track> tracks, int position);
    }
}
