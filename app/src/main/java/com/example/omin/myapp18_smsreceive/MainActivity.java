package com.example.omin.myapp18_smsreceive;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                //String smsSender = "";
                String smsBody = "";
                for(SmsMessage message: Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsBody += message.getMessageBody();
                }
                Toast.makeText(context, smsBody, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.RECEIVE_SMS},
                PERMISSIONS_REQUEST_SMS_RECEIVE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
