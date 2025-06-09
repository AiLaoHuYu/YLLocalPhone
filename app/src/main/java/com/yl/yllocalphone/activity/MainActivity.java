package com.yl.yllocalphone.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationView;
import com.yl.basemvp.BaseActivity;
import com.yl.yllocalphone.R;
import com.yl.yllocalphone.fragment.ContactFragment;
import com.yl.yllocalphone.fragment.DialFragment;
import com.yl.yllocalphone.fragment.HistoryFragment;
import com.yl.yllocalphone.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> {

    private DialFragment dialFragment;
    private HistoryFragment historyFragment;
    private ContactFragment contactFragment;
    private NavigationView navigationView;
    private int lastPosition = -1;
    private String[] permissionList = new String[]{    //申请的权限列表
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.CALL_PHONE
    };
    private List<String> unPermissionList = new ArrayList<String>(); //申请未得到授权的权限列表


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableImmersiveMode();
    }

    @Override
    protected int getLayoutId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telephonyManager.getSimState();
        Log.e("TAG", "simState: " + simState);
        if (telephonyManager != null && telephonyManager.getPhoneCount() > 0) {
            return R.layout.activity_main;
        } else {
            return R.layout.no_sim_activity;
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
        mPresenter.attach(this);
    }

    @Override
    protected void initData() {
        checkPermission();
        mPresenter.getContentCallLog();
        mPresenter.getConnect();
    }

    @Override
    protected void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.phone_dial) {
                hideFragment();
                replaceFragment(0);
                setNavigationViewPressed(0);
                return true;
            } else if (itemId == R.id.phone_history) {
                hideFragment();
                replaceFragment(1);
                setNavigationViewPressed(1);
                return true;
            } else if (itemId == R.id.phone_contact) {
                hideFragment();
                replaceFragment(2);
                setNavigationViewPressed(2);
                return true;
            }
            return false;
        });
        replaceFragment(0);
        setNavigationViewPressed(0);
    }

    private void setNavigationViewPressed(int position){
        if (lastPosition != -1) {
            navigationView.getMenu().getItem(lastPosition).setChecked(false);
        }
        navigationView.getMenu().getItem(position).setChecked(true);
        lastPosition = position;
    }


    //权限判断和申请
    public void checkPermission() {
        unPermissionList.clear();//清空申请的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0; i < permissionList.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissionList[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                unPermissionList.add(permissionList[i]);//添加还未授予的权限到unPermissionList中
            }
        }

        //有权限没有通过，需要申请
        if (unPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionList, 100);
            Log.i("TAG", "check 有权限未通过");
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Log.i("TAG", "check 权限都已经申请通过");
        }
    }

    /**
     * 5.请求权限后回调的方法
     *
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限
     *                     名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("TAG", "申请结果反馈");
        boolean hasPermissionDismiss = false;
        if (100 == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true; //有权限没有通过
                    Log.i("TAG", "有权限没有被通过");
                    break;
                }
            }
        }
        if (hasPermissionDismiss) {//如果有没有被允许的权限
//            showPermissionDialog();
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Log.i("TAG", "onRequestPermissionsResult 权限都已经申请通过");
        }
    }

    public void replaceFragment(int id) {
        hideFragment();
        if (id == 0) {
            if (dialFragment == null) {
                dialFragment = new DialFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, dialFragment).commit();
                dialFragment.setContactList(mPresenter.getContantList());
            } else {
                getSupportFragmentManager().beginTransaction().show(dialFragment).commit();
                dialFragment.setContactList(mPresenter.getContantList());
            }
        } else if (id == 1) {
            if (historyFragment == null) {
                historyFragment = new HistoryFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, historyFragment).commit();
                historyFragment.setDataList(mPresenter.getHistoryModelList());
            } else {
                getSupportFragmentManager().beginTransaction().show(historyFragment).commit();
                historyFragment.setDataList(mPresenter.getHistoryModelList());
            }
        } else if (id == 2) {
            if (contactFragment == null) {
                contactFragment = new ContactFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, contactFragment).commit();
                contactFragment.setDataList(mPresenter.getSortBeans());
            } else {
                getSupportFragmentManager().beginTransaction().show(contactFragment).commit();
                contactFragment.setDataList(mPresenter.getSortBeans());
            }
        }
    }

    private void hideFragment() {
        if (dialFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(dialFragment).commit();
        }
        if (historyFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(historyFragment).commit();
        }
        if (contactFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(contactFragment).commit();
        }
    }

    /**
     * 沉浸式模式
     */
    private void enableImmersiveMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.detach();
        finish();
    }
}