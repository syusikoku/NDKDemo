package com.example.drfunc;

/**
 * Created by zzg on 2017/12/24.
 */

public class DynamicRegistrJni {

    public native String getStringFromCpp();

    public static native void registerNatives();
}
