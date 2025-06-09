package com.yl.yllocalphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.basemvp.BaseRecyclerViewAdapter;
import com.yl.yllocalphone.R;
import com.yl.yllocalphone.model.HistoryModel;

import java.util.List;

public class HistoryRecyAdapter extends BaseRecyclerViewAdapter<HistoryRecyAdapter.HistoryViewHolder, HistoryModel> {

    public HistoryRecyAdapter(Context mContext, List<HistoryModel> dataList) {
        super(mContext, dataList);
    }

    @Override
    protected HistoryViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_recy_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    protected int baseGetItemViewType(int position) {
        return 0;
    }

    @Override
    protected void baseItemClick(View v, int position) {

    }

    @Override
    protected void bindView(HistoryViewHolder holder, int position) {
        holder.historyName.setText(dataList.get(position).getHistoryName());
        holder.historyTime.setText(dataList.get(position).getHistoryTime());
        holder.historyNum.setText(dataList.get(position).getNum());
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyName;
        TextView historyTime;
        TextView historyNum;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyName = itemView.findViewById(R.id.history_name);
            historyTime = itemView.findViewById(R.id.history_time);
            historyNum = itemView.findViewById(R.id.history_num);
        }
    }
}
