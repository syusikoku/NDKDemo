#include <jni.h>
#include <string>
#include <android/log.h>
#include <sys/inotify.h>

#define LOG_TAG "zy"

#include <unistd.h>
#include <malloc.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

/*
 * 原有方法
 * extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_nd_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject *//* this *//*) {
    std::string hello = "Hello ndk demo 2017-12-22";
    return env->NewStringUTF(hello.c_str());
}*/

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_nd_JNI_getString(JNIEnv *env, jobject obj) {
    LOGD(LOG_TAG, "call Java_com_example_nd_JNI_getString");
    return env->NewStringUTF("你好...!!!");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_nd_JNI_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = " hello from c ,today 2017-12-22 ";
    LOGD(LOG_TAG, "call Java_com_example_nd_JNI_stringFromJNI");
    return env->NewStringUTF(hello.c_str());
}