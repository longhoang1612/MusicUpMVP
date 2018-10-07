package music_38.framgia.com.musicup.screen.dialog;

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


public class CreatePlaylistAdapter extends RecyclerView.Adapter<CreatePlaylistAdapter.ViewHolder> {

    private List<GenreSave> dataList;
    private OnItemClickPlayListListener mOnItemClickPlayListListener;
    private LayoutInflater mInflater;

    CreatePlaylistAdapter(List<GenreSave> dataList, OnItemClickPlayListListener onItemClickPlayListListener) {
        this.dataList = dataList;
        mOnItemClickPlayListListener = onItemClickPlayListListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_playlist, parent, false);
        return new ViewHolder(view, mOnItemClickPlayListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenreSave videoInPrograms = dataList.get(position);
        holder.setData(videoInPrograms);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView;
        private TextView mNumberSong;
        private SimpleDraweeView mImagePlaylist;
        private OnItemClickPlayListListener mListener;
        private GenreSave mGenreSave;

        public ViewHolder(View itemView, OnItemClickPlayListListener onItemClickPlayListListener) {
            super(itemView);
            mListener = onItemClickPlayListListener;
            mImagePlaylist = itemView.findViewById(R.id.iv_playlist);
            mTextView = itemView.findViewById(R.id.name_playlist);
            mNumberSong = itemView.findViewById(R.id.number_song);
            itemView.setOnClickListener(this);
        }

        private void setData(GenreSave data) {
            mGenreSave = data;
            mTextView.setText(data.getTitle());
            mNumberSong.setText(String.valueOf(data.getTracks().size()));
            mImagePlaylist.setImageURI(Uri.parse(data.getImage()));
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.linear_playlist) {
                mListener.onItemClickPlayList(mGenreSave);
            }
        }
    }

    public interface OnItemClickPlayListListener {
        void onItemClickPlayList(GenreSave genreSave);
    }
}
