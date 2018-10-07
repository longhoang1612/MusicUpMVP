package music_38.framgia.com.musicup.screen.person;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.GenreSave;


public class FavoritesPlayListAdapter extends RecyclerView.Adapter<FavoritesPlayListAdapter.ViewHolder> {
    private List<GenreSave> mGenreSaves;
    private OnItemPlaylistClickListener mOnItemPlaylistClickListener;

    FavoritesPlayListAdapter(List<GenreSave> mGenreSaves, OnItemPlaylistClickListener onItemPlaylistClickListener) {
        this.mGenreSaves = mGenreSaves;
        this.mOnItemPlaylistClickListener = onItemPlaylistClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorites_playlist, parent, false);
        return new ViewHolder(view, mOnItemPlaylistClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenreSave genreSave = mGenreSaves.get(position);
        holder.setData(genreSave);
    }

    @Override
    public int getItemCount() {
        return mGenreSaves != null ? mGenreSaves.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextName;
        private SimpleDraweeView mImageAll;
        private TextView mTextNumberSong;
        private OnItemPlaylistClickListener mOnItemPlaylistClickListener;
        private GenreSave mGenreSave;

        public ViewHolder(View itemView, OnItemPlaylistClickListener onItemPlaylistClickListener) {
            super(itemView);
            mOnItemPlaylistClickListener = onItemPlaylistClickListener;
            mImageAll = itemView.findViewById(R.id.iv_item_home);
            mTextName = itemView.findViewById(R.id.name_new_pl);
            mTextNumberSong = itemView.findViewById(R.id.number_playlist);
            itemView.setOnClickListener(this);
        }

        private void setData(GenreSave data) {
            mGenreSave = data;
            mTextName.setText(data.getTitle());
            mTextNumberSong.setText(String.valueOf(data.getTracks().size()));
            if (data.getImage() != null) {
                Uri uri = Uri.parse(data.getImage());
                mImageAll.setImageURI(uri);
            } else {
                mImageAll.setImageResource(R.drawable.image_top_country);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.constraint_save_playlist:
                    mOnItemPlaylistClickListener.onItemPlaylistClick(mGenreSave);
                    break;
            }
        }
    }

    public interface OnItemPlaylistClickListener {
        void onItemPlaylistClick(GenreSave genreSave);
    }
}
