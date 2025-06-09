package com.yl.yllocalphone.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yl.yllocalphone.R;
import com.yl.yllocalphone.adapter.DialResultRecyAdapter;
import com.yl.yllocalphone.model.DialSearchResultModel;

import java.util.ArrayList;
import java.util.List;

public class DialFragment extends Fragment implements View.OnClickListener, DialResultRecyAdapter.OnItemClick {

    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0, numX, numJ;
    private ImageView delete, dial;
    private EditText editText;
    private TextView dialName;
    private RecyclerView dialResultRecy;
    private DialResultRecyAdapter dialResultRecyAdapter;
    private List<DialSearchResultModel> contactList;
    private List<DialSearchResultModel> showDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dial_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.display);
        num1 = view.findViewById(R.id.num1);
        num2 = view.findViewById(R.id.num2);
        num3 = view.findViewById(R.id.num3);
        num4 = view.findViewById(R.id.num4);
        num5 = view.findViewById(R.id.num5);
        num6 = view.findViewById(R.id.num6);
        num7 = view.findViewById(R.id.num7);
        num8 = view.findViewById(R.id.num8);
        num9 = view.findViewById(R.id.num9);
        num0 = view.findViewById(R.id.num0);
        numX = view.findViewById(R.id.numX);
        numJ = view.findViewById(R.id.numJ);
        delete = view.findViewById(R.id.delete);
        dial = view.findViewById(R.id.dial);
        dialName = view.findViewById(R.id.dial_name);
        dialResultRecy = view.findViewById(R.id.dial_result_recy);
        dialResultRecyAdapter = new DialResultRecyAdapter(getContext(), new ArrayList<>());
        dialResultRecyAdapter.setOnItemClick(this);
        dialResultRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        dialResultRecy.setAdapter(dialResultRecyAdapter);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        numX.setOnClickListener(this);
        numJ.setOnClickListener(this);
        delete.setOnClickListener(this);
        dial.setOnClickListener(this);
    }

    public void clickNum(@NonNull View view) {
        String str = editText.getText().toString();
        str += view.getTag().toString();
        editText.setText(str);
        changeRecyData();
    }

    private void changeRecyData() {
        dialName.setVisibility(GONE);
        showDataList.clear();
        if (TextUtils.isEmpty(editText.getText())) {
            dialResultRecyAdapter.setDataList(showDataList);
            return;
        }
        if (contactList != null) {
            for (DialSearchResultModel dialSearchResultModel : contactList) {
                if (dialSearchResultModel.getPhoneNum().startsWith(String.valueOf(editText.getText()))) {
                    showDataList.add(dialSearchResultModel);
                }
            }
        }
        if (dialResultRecyAdapter != null) {
            dialResultRecyAdapter.setDataList(showDataList);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete) {
            delete(v);
        } else if (v.getId() == R.id.dial) {
            callPhone(editText.getText().toString().trim().replaceAll(" ", ""));
        } else {
            clickNum(v);
        }
    }

    public void delete(View view) {

        String str = editText.getText().toString();
        if ((str != null) && (!str.trim().equals(""))) {
            str = str.substring(0, str.length() - 1);
            editText.setText(str);
        }
        changeRecyData();
    }

    public void setContactList(List<DialSearchResultModel> contactList) {
        this.contactList = contactList;
    }

    @Override
    public void onItemClick(View v, int position) {
        editText.setText(showDataList.get(position).getPhoneNum());
        dialName.setText(showDataList.get(position).getName());
        dialName.setVisibility(VISIBLE);
        showDataList.clear();
        if (dialResultRecyAdapter != null) {
            dialResultRecyAdapter.setDataList(showDataList);
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

}
