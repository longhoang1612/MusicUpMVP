package music_38.framgia.com.musicup.screen.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.Genre;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private ArrayList<Genre> mGenres;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mLayoutInflater;

    HomeAdapter(ArrayList<Genre> genres, OnItemClickListener onItemClickListener) {
        mGenres = genres;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_home, viewGroup, false);
        return new HomeViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder homeViewHolder, int i) {
        Genre genre = mGenres.get(i);
        homeViewHolder.bindData(genre);
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView mImageHome;
        private TextView mTitleGenre;
        private OnItemClickListener mListener;
        private Genre mGenre;

        HomeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageHome = itemView.findViewById(R.id.image_home);
            mTitleGenre = itemView.findViewById(R.id.text_home);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            if (view.getId() == R.id.constraint_item_home) {
                mListener.onItemClickHomeListener(mGenre);
            }
        }

        void bindData(Genre genre) {
            if (genre == null) {
                return;
            }
            mGenre = genre;
            mTitleGenre.setText(genre.getTitle());
            mImageHome.setImageResource(genre.getImage());
        }
    }

    public interface OnItemClickListener {
        void onItemClickHomeListener(Genre genre);
    }
}

