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
import com.yl.yllocalphone.model.DialSearchResultModel;

import java.util.List;

public class DialResultRecyAdapter extends BaseRecyclerViewAdapter<DialResultRecyAdapter.DialResultViewHolder, DialSearchResultModel> {

    private OnItemClick onItemClick;

    public DialResultRecyAdapter(Context mContext, List<DialSearchResultModel> dataList) {
        super(mContext, dataList);
    }

    @Override
    protected DialResultViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dial_search_result_item, parent, false);
        return new DialResultViewHolder(view);
    }

    @Override
    protected int baseGetItemViewType(int position) {
        return 0;
    }

    @Override
    protected void baseItemClick(View v, int position) {
        if (onItemClick != null) {
            onItemClick.onItemClick(v, position);
        }
    }

    @Override
    protected void bindView(DialResultViewHolder holder, int position) {
        StringBuilder sb = new StringBuilder(dataList.get(position).getName());
        sb.append("，").append(dataList.get(position).getPhoneNum());
        holder.text.setText(sb.toString());
    }

    class DialResultViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public DialResultViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.phone_name_num);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(View v, int position);
    }

}
