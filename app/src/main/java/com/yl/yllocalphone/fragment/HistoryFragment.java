package com.yl.yllocalphone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.yllocalphone.R;
import com.yl.yllocalphone.adapter.HistoryRecyAdapter;
import com.yl.yllocalphone.model.HistoryModel;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryRecyAdapter historyRecyAdapter;
    private List<HistoryModel> dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.history_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyRecyAdapter = new HistoryRecyAdapter(getContext(), dataList);
        recyclerView.setAdapter(historyRecyAdapter);
    }

    public void setDataList(List<HistoryModel> dataList) {
        this.dataList = dataList;
    }


}
