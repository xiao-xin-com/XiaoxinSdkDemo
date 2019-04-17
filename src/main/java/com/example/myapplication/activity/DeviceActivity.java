package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Device;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoxin.sdk.DataManager;
import com.xiaoxin.sdk.SOSActivity;
import com.xiaoxin.sdk.Xiaoxin;
import com.xiaoxin.sdk.data.Person;
import com.xiaoxin.sdk.call.OnCallListener;

import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    private EditText etSOSId;
    private TextView tvList;
    private View btSOS;
    private String TAG = "DeviceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        init();
    }

    private void init() {
        etSOSId = findViewById(R.id.et_sos);
        btSOS = findViewById(R.id.bt_sos);
        tvList = findViewById(R.id.tv_list);
        btSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sosId = etSOSId.getText().toString();
                if (!TextUtils.isEmpty(sosId)) {
                    SOSActivity.startSOSActivity(sosId);
                }
            }
        });

        findViewById(R.id.bt_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceList();
            }
        });
    }

    private void getDeviceList() {
        Xiaoxin.getDeviceList(Xiaoxin.getUniqueid(), new OnCallListener<String>() {
            @Override
            public void onSuccess(@NonNull String s) {
                if (!TextUtils.isEmpty(s)) {
                    List<Device> devices = new Gson().fromJson(s, new TypeToken<List<Device>>() {
                    }.getType());
                    if (devices != null && !devices.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < devices.size(); i++) {
                            Device device = devices.get(i);
                            stringBuilder.append(i + 1)
                                    .append(".")
                                    .append(device.getUnGuardian().getName())
                                    .append(".")
                                    .append(device.getUnGuardian().getImei())
                                    .append("\n");
                        }
                        tvList.setText(stringBuilder.toString());
                    } else {
                        tvList.setText("无绑定设备");
                    }
                } else {
                    tvList.setText("无绑定设备");
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
            }
        });
    }
}
