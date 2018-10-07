package music_38.framgia.com.musicup.screen.favorites;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Track;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private List<Track> mTracks;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    FavoritesAdapter(List<Track> tracks, OnItemClickListener onClickListener) {
        mTracks = tracks;
        this.mOnItemClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mInflater.inflate(R.layout.item_favorites_song, viewGroup, false);
        return new FavoritesViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int i) {
        Track mTrack = mTracks.get(i);
        favoritesViewHolder.bindData(mTrack, mTracks);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageFavorites;
        private TextView mTextFavorites;
        private TextView mAuthSong;
        private ImageView mImageMore;
        private OnItemClickListener mOnItemClickListener;

        FavoritesViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mOnItemClickListener = onItemClickListener;
            mImageFavorites = itemView.findViewById(R.id.image_favorites);
            mAuthSong = itemView.findViewById(R.id.text_auth_favorites);
            mTextFavorites = itemView.findViewById(R.id.text_name_favorites);
            mImageMore = itemView.findViewById(R.id.icon_more_song);
            itemView.setOnClickListener(this);
            mImageMore.setOnClickListener(this);
        }

        private void bindData(Track track, List<Track> tracks) {
            if (tracks == null) {
                return;
            }
            if (track == null) {
                return;
            }
            if(track.getArtworkUrl()!=null) {
                mImageFavorites.setImageURI(Uri.parse(track.getArtworkUrl()));
            }
            mTextFavorites.setText(track.getTitle());
            mAuthSong.setText(track.getArtist());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.icon_more_song:
                    break;
                case R.id.constraint_favorites_song:
                    if (mOnItemClickListener == null) {
                        return;
                    }
                    mOnItemClickListener.onItemClickFavoritesTrackListener(mTracks,
                            getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickFavoritesTrackListener(List<Track> tracks, int position);
    }

}
