package music_38.framgia.com.musicup.screen.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import music_38.framgia.com.musicup.R;


public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.SearchViewHolder> {

    private ArrayList<String> mListSuggest;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mLayoutInflater;

    public SuggestAdapter(ArrayList<String> listSuggest, OnItemClickListener onItemClickListener) {
        mListSuggest = listSuggest;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SuggestAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_suggest_search
                , viewGroup, false);
        return new SearchViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestAdapter.SearchViewHolder detailViewHolder, int i) {
        String suggestKey = mListSuggest.get(i);
        detailViewHolder.bindData(suggestKey);
    }

    @Override
    public int getItemCount() {
        return mListSuggest.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextSuggest;
        private OnItemClickListener mListener;
        private String mKeySuggest;

        SearchViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mListener = onItemClickListener;
            mTextSuggest = itemView.findViewById(R.id.text_suggest);
            itemView.setOnClickListener(this);
        }

        private void bindData(String keySuggest) {
            if (keySuggest == null) {
                return;
            }
            mKeySuggest = keySuggest;
            mTextSuggest.setText(keySuggest);
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            if (view.getId() == R.id.constraint_suggest) {
                mListener.onItemClickSuggestListener(mKeySuggest);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickSuggestListener(String keySuggest);
    }
}
