package com.example.drfunc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static String LOGTAG = "JNI_LOG";

    static {
        Log.e(LOGTAG, "Main--->> static 准备加载");
        System.loadLibrary("hello");
        Log.e(LOGTAG, "Main--->> static 准备registerNatives");
        DynamicRegistrJni.registerNatives();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(LOGTAG, "Main--->> onCreate");
        DynamicRegistrJni jni = new DynamicRegistrJni();
        Log.e(LOGTAG, "Main--->> onCreate getStringFromCpp");
        String jniStringFromCpp = jni.getStringFromCpp();
        Log.e(LOGTAG, jniStringFromCpp);
    }
}
