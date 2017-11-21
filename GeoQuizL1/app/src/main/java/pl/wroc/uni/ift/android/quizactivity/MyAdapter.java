package pl.wroc.uni.ift.android.quizactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by neo on 07/11/17.
 */

public class MyAdapter extends RecyclerView.Adapter {

    private ArrayList<Question> mDataset = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.question);
        }
    }

    public MyAdapter(ArrayList<Question> pDataset, RecyclerView pRecyclerView) {
        mDataset = pDataset;
        mRecyclerView = pRecyclerView;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        (( ViewHolder) holder).mTextView.setText(mDataset.get(position).getTextResId());
    }
}
