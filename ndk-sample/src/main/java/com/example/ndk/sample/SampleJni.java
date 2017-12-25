package com.example.ndk.sample;

/**
 * Created by zzg on 2017/12/22.
 */

public class SampleJni {

    /*卸载监听*/
    public native void callUnInstallListener(int versionSdk, String path);

    public native int add(int v1, int v2);
}
