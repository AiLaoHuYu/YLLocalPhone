package com.yl.yllocalphone.presenter;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.yl.basemvp.BasePresenter;
import com.yl.basemvp.utils.SortBean;
import com.yl.yllocalphone.R;
import com.yl.yllocalphone.activity.MainActivity;
import com.yl.yllocalphone.model.DialSearchResultModel;
import com.yl.yllocalphone.model.HistoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPresenter extends BasePresenter<MainActivity> {

    private Uri callUri = CallLog.Calls.CONTENT_URI;
    private String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
            , CallLog.Calls.NUMBER// 通话记录的电话号码
            , CallLog.Calls.DATE// 通话记录的日期
            , CallLog.Calls.DURATION// 通话时长
            , CallLog.Calls.TYPE};// 通话类型}
    private List<DialSearchResultModel> contantList;
    private List<HistoryModel> historyModelList;
    private List<SortBean> sortBeans;

    @Override
    protected void onItemClick(View view) {
//        if (view.getId() == R.id.dial_btn) {
//            mActivity.get().replaceFragment(0);
//        } else if (view.getId() == R.id.history_btn) {
//            mActivity.get().replaceFragment(1);
//        } else if (view.getId() == R.id.contact_btn) {
//            mActivity.get().replaceFragment(2);
//        }
    }

    //获取联系人
    public void getConnect() {
        contantList = new ArrayList<>();
        sortBeans = new ArrayList<>();
        Cursor cursor = mActivity.get().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{"display_name", "sort_key", "contact_id",
                        "data1"}, null, null, null);
        Log.i("TAG", "cursor connect count:" + cursor.getCount());
//        moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contantList.add(new DialSearchResultModel(name.replace(" ", ""), number.replaceAll(" ", "")));
            sortBeans.add(new SortBean(name.replace(" ", ""), number.replaceAll(" ", "")));
            Log.i("TAG", "获取的通讯录是： " + name + "\n"
                    + " number : " + number);
        }
        cursor.close();
    }


    //获取通话记录
    public void getContentCallLog() {
        historyModelList = new ArrayList<>();
        Cursor cursor = mActivity.get().getContentResolver().query(callUri, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        Log.i("TAG", "cursor count:" + cursor.getCount());
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));  //姓名
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));  //号码
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)); //获取通话日期
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));//获取通话时长，值为多少秒
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)); //获取通话类型：1.呼入2.呼出3.未接
            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));
            historyModelList.add(new HistoryModel(name, number, date));
            Log.i("TAG", "Call log: " + "\n"
                    + "name: " + name + "\n"
                    + "phone number: " + number + "\n"

            );
        }
    }

    public List<SortBean> getSortBeans() {
        return sortBeans;
    }

    public List<HistoryModel> getHistoryModelList() {
        return historyModelList;
    }

    public List<DialSearchResultModel> getContantList() {
        return contantList;
    }

}
