package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.xiaoxin.sdk.SOSActivity;
import com.xiaoxin.sdk.Xiaoxin;
import com.xiaoxin.sdk.utils.VoiceRecordManager;

import io.rong.imlib.model.Conversation;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ljman on 2018/4/13.
 */

public class UserActivity extends Activity implements View.OnClickListener {

    private EditText etText;
    private EditText etId;
    private Button btSendT;
    private Button btVocie;
    private Button btDevice;
    private Button btContacts;
    private GifImageView gifImageView;
    private int STATUS;
    private final int NORECORD = 0;//未开始录音
    private final int RECORDING = 1;//录音中
    private VoiceRecordManager voiceRecordManager;
    public static final String TAG = "UserActivity";
    private static String id = "5a686e0dbd008499131b1c24";//目标用户id
    private static String sosID = "5b069b5c7f2179d56f10e44e";//目标用户id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
    }

    private void init() {
        etText = (EditText) findViewById(R.id.et_text);
        etId = (EditText) findViewById(R.id.et_id);
        btDevice = (Button) findViewById(R.id.bt_device);
        btSendT = (Button) findViewById(R.id.bt_sendT);
        btVocie = (Button) findViewById(R.id.bt_voice);
        gifImageView = (GifImageView) findViewById(R.id.gf);
        btContacts = (Button) findViewById(R.id.bt_contacts);

        btSendT.setOnClickListener(this);
        btVocie.setOnClickListener(this);
        btDevice.setOnClickListener(this);
        btContacts.setOnClickListener(this);
        voiceRecordManager = VoiceRecordManager.getInstance();
    }


    @Override
    public void onClick(View v) {
        id = etId.getText().toString();
        switch (v.getId()) {
            //发送文本消息
            case R.id.bt_sendT:
                String sendT = etText.getText().toString();
                if (!TextUtils.isEmpty(id)) {
                    if (!TextUtils.isEmpty(sendT)) {
                        Xiaoxin.sendText(id, sendT);
                    } else {
                        Toast.makeText(this, "请输入文本", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入目标id", Toast.LENGTH_SHORT).show();
                }
                break;
            //录音
            case R.id.bt_voice:
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(this, "请输入目标id", Toast.LENGTH_SHORT).show();
                } else {
                    handleVoice();
                }
                break;
            case R.id.bt_device:
                Intent intent = new Intent(this, DeviceActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_contacts:
                startActivity(new Intent(this, ContactsActivity.class));
                break;
            default:
                break;

        }
    }

    private void handleVoice() {
        switch (STATUS) {
            //开始
            case NORECORD:
                voiceRecordManager.init(id, Conversation.ConversationType.PRIVATE);
                gifImageView.setVisibility(View.VISIBLE);
                voiceRecordManager.startRecordVoice();
                btVocie.setText("停止录音");
                STATUS = RECORDING;
                break;

            //结束并发送
            case RECORDING:
                gifImageView.setVisibility(View.INVISIBLE);
                voiceRecordManager.stopRecordVoice();
                btVocie.setText("开始录音");
                STATUS = NORECORD;
                break;
            default:
                break;
        }

    }
}
