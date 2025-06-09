package com.yl.yllocalphone.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.basemvp.utils.SortBean;
import com.yl.basemvp.utils.SortComparator;
import com.yl.yllocalphone.R;
import com.yl.yllocalphone.adapter.SortAdapter;
import com.yl.yllocalphone.view.SideBarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFragment extends Fragment implements TextWatcher {

    private RecyclerView recyclerView;
    private SideBarLayout sideBarView;
    private EditText edtSearch;
    private List<SortBean> mList;
    private SortAdapter mSortAdaper;
    private int mScrollState = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.contact_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollState = -1;
        edtSearch = view.findViewById(R.id.edt_search);
        recyclerView = view.findViewById(R.id.recyclerView);
        sideBarView = view.findViewById(R.id.sidebar);
        edtSearch.addTextChangedListener(this);
        initData();
        connectData();
    }

    private void initData() {
        mList = new ArrayList<>();
        //进行排序
        Collections.sort(mList, new SortComparator());
        mSortAdaper = new SortAdapter(getContext(), mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //设置LayoutManager为LinearLayoutManager
        recyclerView.setAdapter(mSortAdaper);
        recyclerView.setNestedScrollingEnabled(false);//解决滑动不流畅

    }

    private void connectData() {
        //侧边栏滑动 --> item
        sideBarView.setSideBarLayout(new SideBarLayout.OnSideBarLayoutListener() {
            @Override
            public void onSideBarScrollUpdateItem(String word) {
                //循环判断点击的拼音导航栏和集合中姓名的首字母,如果相同recyclerView就跳转指定位置
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getWord().equals(word)) {
                        recyclerView.smoothScrollToPosition(i);
                        break;
                    }
                }
            }
        });
        //item滑动 --> 侧边栏
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                mScrollState = scrollState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mScrollState != -1) {
                    //第一个可见的位置
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    int firstItemPosition = 0;
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取第一个可见view的位置
                        firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    }

                    sideBarView.onItemScrollUpdateSideBarText(mList.get(firstItemPosition).getWord());
                    if (mScrollState == RecyclerView.SCROLL_STATE_IDLE) {
                        mScrollState = -1;
                    }
                }
            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mList == null || mList.size() <= 0) {
            return;
        }
        String keyWord = s.toString().trim();
        Log.i("test", "------------key=" + keyWord);
        if (!TextUtils.isEmpty(keyWord)) {
            List<SortBean> searchList = matcherSearch(keyWord, mList);
            if (searchList.size() > 0) {
                sideBarView.onItemScrollUpdateSideBarText(searchList.get(0).getWord());
            }
            mSortAdaper.setDataList(searchList);
        } else {
            sideBarView.onItemScrollUpdateSideBarText(mList.get(0).getWord());
            mSortAdaper.setDataList(mList);
        }
        mSortAdaper.notifyDataSetChanged();
    }

    /**
     * 匹配输入数据
     *
     * @param keyword
     * @param list
     * @return
     */
    public List<SortBean> matcherSearch(String keyword, List<SortBean> list) {
        List<SortBean> results = new ArrayList<>();
        String patten = Pattern.quote(keyword);
        Pattern pattern = Pattern.compile(patten, Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < list.size(); i++) {
            //根据首字母
            Matcher matcherWord = pattern.matcher((list.get(i)).getWord());
            //根据拼音
            Matcher matcherPin = pattern.matcher((list.get(i)).getPinyin());
            //根据简拼
            Matcher matcherJianPin = pattern.matcher((list.get(i)).getJianpin());
            //根据名字
            Matcher matcherName = pattern.matcher((list.get(i)).getName());
            if (matcherWord.find() || matcherPin.find() || matcherName.find() || matcherJianPin.find()) {
                results.add(list.get(i));
            }
        }

        return results;
    }

    public void setDataList(List<SortBean> historyModelList) {
        this.mList = historyModelList;
    }
}
