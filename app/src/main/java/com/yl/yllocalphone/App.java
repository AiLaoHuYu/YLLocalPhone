package com.yl.yllocalphone;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.yl.yllocalphone.activity.MainActivity;
import com.yl.yllocalphone.activity.NoSimActivity;

public class App extends Application {

    private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

    @Override
    public void onCreate() {
        super.onCreate();
        registerBroadcast();
    }

    private void registerBroadcast() {
        SimCarReceiver simCarReceiver = new SimCarReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SIM_STATE_CHANGED);
        registerReceiver(simCarReceiver, intentFilter);
    }

    public class SimCarReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_SIM_STATE_CHANGED)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                int state = tm.getSimState();
                Log.e("TAG", "state: " + state);
                switch (state) {
                    case TelephonyManager.SIM_STATE_READY:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                        break;
                    case TelephonyManager.SIM_STATE_UNKNOWN:
                    case TelephonyManager.SIM_STATE_ABSENT:
                    case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    default:
                        Intent intent2 = new Intent(context, NoSimActivity.class);
                        intent2.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);
                        break;
                }
            }
        }
    }

}
