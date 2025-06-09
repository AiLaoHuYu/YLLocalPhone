package com.yl.yllocalphone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.basemvp.BaseRecyclerViewAdapter;
import com.yl.basemvp.utils.SortBean;
import com.yl.yllocalphone.R;

import java.util.List;

public class SortAdapter extends BaseRecyclerViewAdapter<SortAdapter.SortViewHolder, SortBean> {

    public SortAdapter(Context mContext, List<SortBean> dataList) {
        super(mContext, dataList);
    }

    @Override
    protected SortViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected int baseGetItemViewType(int position) {
        return 0;
    }

    @Override
    protected void baseItemClick(View v, int position) {

    }

    @Override
    protected void bindView(SortViewHolder viewHolder, int position) {
        //第一个字母显示
        if (viewHolder.getLayoutPosition() == 0) {
            viewHolder.tvKey.setVisibility(View.VISIBLE);
        } else {
            //然后判断当前姓名的首字母和上一个首字母是否相同,如果相同字母导航条就隐藏,否则就显示
//            if(mData.get(viewHolder.getLayoutPosition()).getWordAscii()==mData.get(viewHolder.getLayoutPosition()-1).getWordAscii()){
//                (viewHolder.getView(R.id.tv_key)).setVisibility(View.GONE);
//            }else {
//                (viewHolder.getView(R.id.tv_key)).setVisibility(View.VISIBLE);
//            }

            //首字母和上一个首字母是否相同,如果相同字母导航条就影藏,否则就显示
            int section = getSectionForPosition(viewHolder.getLayoutPosition());
            if (viewHolder.getLayoutPosition() == getPositionForSection(section)) {
                viewHolder.tvKey.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvKey.setVisibility(View.GONE);
            }
        }
        viewHolder.tvKey.setText(dataList.get(position).getWord());
        viewHolder.tvName.setText(dataList.get(position).getName());
        viewHolder.tvPhone.setText(dataList.get(position).getPhone());
    }

    class SortViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKey;
        private TextView tvName;
        private TextView tvPhone;

        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.tv_key);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
        }
    }

    /**
     * 根据View的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return getDataList().get(position).getWord().charAt(0);
    }

    /**
     * 获取第一次出现该首字母的List所在的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getDataList().size(); i++) {
            String sortStr = getDataList().get(i).getWord();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


}
