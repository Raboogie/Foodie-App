package com.example.foodieapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class FoodieReceiver extends BroadcastReceiver implements OnIntegerChangeListener {
    private Context context;
    protected static final String ACTION_CUSTOM_BROADCAST = "I_AM_HOME";
    private static final String TAG = "FoodieReceiver";

    private int keyFoodieRec;
    private OnIntegerChangeListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Inside FoodieReceiver, onReceive method");
        keyFoodieRec = 0;
        this.context = context;
        String intentAction = intent.getAction();
        getReceiverAction(intentAction);
    }

    private void getReceiverAction(String intentAction) {
        Log.d(TAG, "Inside FoodieReceiver, getReceiverAction method ");
        switch (intentAction) {
            case ACTION_CUSTOM_BROADCAST:
                Log.d("Broadcast Received", "CUSTOM BROADCAST FROM I AM HOME APP");
                setKeyFoodieRec(1);
                //Toast.makeText(context, "CUSTOM BROADCAST FROM I AM HOME APP", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.d("Broadcast Received", "Power Connected");
                Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Log.d("Broadcast Received", "Power Disconnected");
                Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_BATTERY_LOW:
                Log.d("Broadcast Received", "Battery Low");
                Toast.makeText(context, "Battery Low", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_BATTERY_OKAY:
                Log.d("Broadcast Received", "Battery OK");
                Toast.makeText(context, "Battery OK", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Log.d("Broadcast Received", "Airplane mode changed");
                Toast.makeText(context, "Airplane mode change", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.d("Broadcast Received", "DUNNO");
                Toast.makeText(context, "DUNNO", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public int getKeyFoodieRec() {
        return keyFoodieRec;
    }

    public void setKeyFoodieRec(int keyFoodieRec) {
        this.keyFoodieRec = keyFoodieRec;
        if (listener != null) {
            listener.onIntegerChanged(keyFoodieRec);
        }
    }

    public void setOnIntegerChangeListener(OnIntegerChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onIntegerChanged(int newValue) {

    }
}
