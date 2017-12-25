package com.example.ndk.sample;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SampleJni sampleJni;

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

        addListener();
    }

    private void addListener() {
        findViewById(R.id.btn_action_calc).setOnClickListener(this);
        findViewById(R.id.btn_action_decode).setOnClickListener(this);
        findViewById(R.id.btn_action_encode).setOnClickListener(this);
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
                Log.e("test", calcResult);
                Toast.makeText(this, calcResult, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_encode:
                break;
            case R.id.btn_action_decode:
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
