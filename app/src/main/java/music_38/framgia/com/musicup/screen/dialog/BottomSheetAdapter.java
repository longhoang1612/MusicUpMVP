package music_38.framgia.com.musicup.screen.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.data.model.BottomSheet;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
    private List<BottomSheet> mSheets;
    private OnItemBottomClickListener onClickListener;
    private LayoutInflater mInflater;

     BottomSheetAdapter(List<BottomSheet> mSheets, OnItemBottomClickListener onClickListener) {
        this.mSheets = mSheets;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.item_bottom_sheet, parent, false);
        return new ViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BottomSheet bottomSheet = mSheets.get(position);
        holder.bindData(bottomSheet);
    }

    @Override
    public int getItemCount() {
        return mSheets != null ? mSheets.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextTitle;
        private ImageView mIconSheet;
        private OnItemBottomClickListener mListener;
        private BottomSheet mBottomSheet;

        ViewHolder(View itemView, OnItemBottomClickListener onItemBottomClickListener) {
            super(itemView);
            mListener = onItemBottomClickListener;
            mTextTitle = itemView.findViewById(R.id.tv_title_bottom_sheet);
            mIconSheet = itemView.findViewById(R.id.ic_bottom_sheet);

            itemView.setOnClickListener(this);
        }

        private void bindData(BottomSheet data) {
            mBottomSheet = data;
            mTextTitle.setText(data.getTitle());
            mIconSheet.setImageResource(data.getImage());
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            if (view.getId() == R.id.constraint_bottom_sheet) {
                mListener.onItemBottomSheetClick(mBottomSheet);
            }
        }
    }

    public interface OnItemBottomClickListener {
        void onItemBottomSheetClick(BottomSheet bottomSheet);
    }
}
