#include <jni.h>
#include <string>
#include <android/log.h>
#include <sys/inotify.h>

#define LOG_TAG "zy"

#include <unistd.h>
#include <malloc.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_example_ndk_sample_SampleJni_callUnInstallListener(JNIEnv *env, jobject jobj,
                                                            jint versionSdk, jstring path) {
    /*
      fork()子进程
      创建监听文件
      初始化inotify实例
      注册监听事件
      调用read函数开始监听
      卸载反馈统计
     */
    LOGE("------------------------");
    LOGE("------------------------");
    const char *path_str = env->GetStringUTFChars(path, 0);
    pid_t pid = fork();
    if (pid < 0) {
        LOGE("克隆失败");
    } else if (pid > 0) {
        LOGE("父进程");
    } else {
        LOGE("子进程！");
        //*******************在这里进程操作*****************
        LOGE("你好，终端研发部");
        int fuileDescript = inotify_init();
        int watch = inotify_add_watch(fuileDescript, path_str, IN_DELETE_SELF);
        void *p = malloc(sizeof(struct inotify_event));
        read(fuileDescript, p, sizeof(struct inotify_event));
        inotify_rm_watch(fuileDescript, watch);
        LOGE(LOG_TAG, "接下来进行操作，来条状网页!!!");
        if (versionSdk < 17) {
            //am start -a android.intent.action.VIEW -d
            execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d",
                   "https://github.com/syusikoku", NULL);
        } else {
            execlp("am", "am", "start", "--user", "0", "-a", "android.intent.action.VIEW", "-d",
                   "https://github.com/syusikoku", NULL);
        }
    }
    env->ReleaseStringUTFChars(path, path_str);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_ndk_sample_SampleJni_add(JNIEnv *env, jobject instance, jint v1, jint v2) {
    LOGD(LOG_TAG, "call Java_com_example_nd_JNI_plus");
    return v1 + v2;
}