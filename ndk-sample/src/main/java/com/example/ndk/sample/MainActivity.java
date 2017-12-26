package com.example.ndk.sample;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "test";
    private SampleJni sampleJni;

    private TextView textView;

    static {
        System.loadLibrary("hello");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleJni = new SampleJni();

        /*卸载监听*/
        sampleJni.callUnInstallListener(Build.VERSION.SDK_INT, "data/data/com.example.ndk.sample");

        initView();
        addListener();
    }

    private void initView() {
        textView = findViewById(R.id.tv_desc);
    }

    private void addListener() {
        findViewById(R.id.btn_action_calc).setOnClickListener(this);
        findViewById(R.id.btn_action_edcode).setOnClickListener(this);
        findViewById(R.id.btn_action_videodecode).setOnClickListener(this);
        findViewById(R.id.btn_action_videoencode).setOnClickListener(this);
        findViewById(R.id.btn_action_piczip).setOnClickListener(this);
        findViewById(R.id.btn_action_filezip).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action_calc:
                String calcResult = "相加的结果: " + sampleJni.add(15, 12) + "";
                Log.e(LOG_TAG, calcResult);
                Toast.makeText(this, calcResult, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_edcode:
                String str = "13550110110";
                String encodeStr = sampleJni.encode(str);
                String decodeStr = sampleJni.decode(encodeStr);
                textView.setText("加密前：" + str + "\n加密后：" + encodeStr + "\n解密后：" + decodeStr);
                break;
            case R.id.btn_action_videodecode:
                break;
            case R.id.btn_action_videoencode:
                break;
            case R.id.btn_action_piczip:
                break;
            case R.id.btn_action_filezip:
                break;
        }
    }
}
