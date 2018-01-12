package com.example.waleedazhar.ass3_sharedpreferrence;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";
    static Switch wifiSwitch;
    static Switch airplaneswitch;
    static TextView batteryStatus;
    static EditText etName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch = findViewById(R.id.wifiswitch);
        final SharedPreferences sp = getSharedPreferences("wifi", MODE_PRIVATE);
        wifiSwitch.setChecked(sp.getBoolean("wifi",false));
        wifiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifiSwitch.isChecked()) {
                    wifiManager.setWifiEnabled(true);

                    String CHANNEL_ID = "my_channel_01";
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle("Wifi")
                                    .setContentText("Wifi is On");
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());


                } else {
                    wifiManager.setWifiEnabled(false);

                    String CHANNEL_ID = "my_channel_02";
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle("Wifi")
                                    .setContentText("Wifi is OFF");
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder.build());

                }
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("wifi", wifiSwitch.isChecked());
                editor.commit();



            }
        });



        etName = findViewById(R.id.etName);
        final  SharedPreferences sp1 = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(sp1.getString("editText", ""));
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("editText", etName.getText().toString());
                editor.commit();

                String CHANNEL_ID = "my_channel_03";
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Name Changed")
                                .setContentText("Your Name is " + etName.getText().toString());
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo status = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            wifiSwitch = findViewById(R.id.wifiswitch);
            final SharedPreferences spwifi2 = getSharedPreferences("wifi", MODE_PRIVATE);
            wifiSwitch.setChecked(spwifi2.getBoolean("wifi", false));
            if (status.isConnected()) {
                wifiSwitch.setChecked(true);
                String CHANNEL_ID = "my_channel_04";
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Wifi")
                                .setContentText("Wifi is ON");
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            } else {
                wifiSwitch.setChecked(false);
                String CHANNEL_ID = "my_channel_05";
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Wifi")
                                .setContentText("Wifi is OFF");
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            }

            SharedPreferences.Editor editor = spwifi2.edit();
            editor.putBoolean("wifi", wifiSwitch.isChecked());
            editor.commit();
        }
    };


    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryStatus = findViewById(R.id.batteryStatus);
            final SharedPreferences batterysp = getSharedPreferences("battery", MODE_PRIVATE);
            batterysp.getString("battery", "");
            if (level >= 15) {
                batteryStatus.setText("Battery status is Okay !");
                String CHANNEL_ID = "my_channel_06";
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Battery")
                                .setContentText("Battery status is Okay !");
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            } else {
                batteryStatus.setText("Battery status is Low !");
                String CHANNEL_ID = "my_channel_07";
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Wifi")
                                .setContentText("Battery status is Low !");
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            }
            SharedPreferences.Editor editor = batterysp.edit();
            editor.putString("battery",batteryStatus.getText().toString());
            editor.commit();
        }
    };

    BroadcastReceiver airplaneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            airplaneswitch = findViewById(R.id.airplaneswitch);
            final SharedPreferences airplanesp = getSharedPreferences("airplane", MODE_PRIVATE);
            airplaneswitch.setChecked(airplanesp.getBoolean("airplane", false));
            int state = Settings.System.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0);
            if(state == 1) {
                airplaneswitch.setChecked(true);
            }
            else
            {
                airplaneswitch.setChecked(false);
            }
            SharedPreferences.Editor editor = airplanesp.edit();
            editor.putBoolean("state", airplaneswitch.isChecked());
            editor.apply();



            /*SharedPreferences.Editor editor = airplanesp.edit();
            editor.putBoolean("airplane", isAirplaneModeOn(context));
            boolean bool = editor.commit();*/

        }

    };


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(receiver, filter);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneReceiver, intentFilter);

        IntentFilter batteryIF = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, batteryIF);

    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        unregisterReceiver(airplaneReceiver);
        unregisterReceiver(batteryReceiver);
        super.onPause();
    }


}
