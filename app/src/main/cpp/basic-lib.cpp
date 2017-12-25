#include <jni.h>
#include <string>
#include <android/log.h>
#include <sys/inotify.h>

#define LOG_TAG "zy"

#include <unistd.h>
#include <malloc.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)


extern "C"
JNIEXPORT jint JNICALL
Java_com_example_nd_JNI_add(JNIEnv *env, jobject jobj) {
    /*获取实例对应的class*/
    jclass jclazz = env->GetObjectClass(jobj);
    /*通过class获取相应的变量的filed id*/
    jfieldID fid = env->GetFieldID(jclazz, "num", "I");
    // 通过filed id 获取相应的值，获取对象字段的值
    jint fnum = env->GetIntField(jobj, fid);
    fnum++;
    return fnum;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_nd_JNI_accessStaticField(JNIEnv *env, jobject jobj) {
    jclass jclazz = env->GetObjectClass(jobj);
    jfieldID fid = env->GetStaticFieldID(jclazz, "name",
                                         "Ljava/lang/String;"); //注意是用GetStaticFieldID，不是GetFieldID
    jstring jname = (jstring) env->GetStaticObjectField(jclazz, fid);
    const char *str = env->GetStringUTFChars(jname, JNI_FALSE);
    /*
     * 不要用 == 比较字符串
     * name == (jstring) "cfanr"
     * 或用 = 直接赋值
     * name = (jstring) "navy"
     * 警告：warning: result of comparison against a string literal is unspecified (use strncmp instead) [-Wstring-compare]
     */
    char ch[30] = "hello, ";
    strcat(ch, str);
    jstring new_str = env->NewStringUTF(ch);
    env->SetStaticObjectField(jclazz, fid, new_str);
    /*需要注意的是，获取 java 静态变量，都是调用 JNI 相应静态的函数，不能调用非静态的，同时留意传入的参数是 jclass，而不是 jobject*/
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_nd_JNI_accessPrivateField(JNIEnv *env, jobject jobj) {
    jclass jclazz = env->GetObjectClass(jobj);
    /*默认就是获取私有的*/
    jfieldID fid = env->GetFieldID(jclazz, "age", "I");
    jint age = env->GetIntField(jobj, fid);
    if (age > 18) {
        age = 18;
    } else {
        age--;
    }
    env->SetIntField(jobj, fid, age);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_nd_JNI_accessPubicMethod(JNIEnv *env, jobject jobj) {
    /*获取对应的class实体类*/
    jclass jclazz = env->GetObjectClass(jobj);
    /*获取方法的Id*/
    jmethodID mid = env->GetMethodID(jclazz, "setSex", "(Ljava/lang/String;)V");
    /*字符数组转换成字符串*/
    char c[10] = "male";
    jstring jsex = env->NewStringUTF(c);
    // 通过该class调用对应的public方法
    env->CallVoidMethod(jobj, mid, jsex);

    /*调用 java private 方法也是类似， Java 的访问域修饰符对 C++无效*/
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_nd_JNI_accessPrivateMethod(JNIEnv *env, jobject jobj) {
    /*获取对应的class实体类*/
    jclass jclazz = env->GetObjectClass(jobj);
    /*获取方法的Id*/
    jmethodID mid = env->GetMethodID(jclazz, "setHideSex", "(Ljava/lang/String;)V");
    /*字符数组转换成字符串*/
    char c[10] = "male";
    jstring jsex = env->NewStringUTF(c);
    // 通过该class调用对应的public方法
    env->CallVoidMethod(jobj, mid, jsex);

    /*调用 java private 方法也是类似， Java 的访问域修饰符对 C++无效*/
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_nd_JNI_accessSuperMethod(JNIEnv *env, jobject jobj) {
    /*通过反射获取class实体类*/
    jclass jclazz = env->FindClass("com/example/nd/SuperJni"); // //注意 FindClass 不要 L和;
    if (jclazz == NULL) {
        char c[10] = "error";
        return env->NewStringUTF(c);
    }

    // 通过class找到对应的方法的id
    jmethodID mid = env->GetMethodID(jclazz, "hello", "(Ljava/lang/String;)Ljava/lang/String;");
    char ch[10] = "cfanr";
    jstring jstr = env->NewStringUTF(ch);
    return (jstring) env->CallNonvirtualObjectMethod(jobj, jclazz, mid, jstr);

    /*
     注意两点不同的地方，
        获取的是父类的方法，所以不能通过GetObjectClass获取，需要通过反射 FindClass 获取；
        调用父类的方法是 CallNonvirtual{type}Method 函数。Nonvirtual是非虚拟函数
    */
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_nd_JNI_intArrayMethod(JNIEnv *env, jobject jobj, jintArray arr_) {
    jint len = 0, sum = 0;
    jint *arr = env->GetIntArrayElements(arr_, 0);
    len = env->GetArrayLength(arr_);
    //由于一些版本不兼容，i不定义在for循环中
    jint i = 0;
    for (; i < len; i++) {
        sum += arr[i];
    }
    env->ReleaseIntArrayElements(arr_, arr, 0); // 释放内存
    return sum;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_nd_JNI_objectMethod(JNIEnv *env, jobject jobj, jobject person) {
    jclass clazz = env->GetObjectClass(person);  //注意是用 person，不是 jobj
//    jclass jclazz = env->FindClass("com/example/nd/Person;");  //或者通过反射获取
    if (clazz == NULL) {
        return env->NewStringUTF("cannot find class");
    }
    //获取方法 id
    jmethodID constructorMid = env->GetMethodID(clazz, "<init>", "(ILjava/lang/String;)V");
    if (constructorMid == NULL) {
        return env->NewStringUTF("not find constructor method");
    }
    jstring name = env->NewStringUTF("syusikoku");
    return env->NewObject(clazz, constructorMid, 21, name);

    /*传递对象时，获取的 jclass 是获取该参数对象的 jobject 获取，而不是第二个参数（定义该 native 方法的对象）取；*/
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_nd_JNI_objectListMethod(JNIEnv *env, jobject instance, jobject persons) {
    jclass clazz = env->GetObjectClass(persons);  //注意是用 person，不是 jobj
    if (clazz == NULL) {
        return env->NewStringUTF("cannot find class");
    }
    //获取 ArrayList 无参数的构造函数
    jmethodID constructorMid = env->GetMethodID(clazz, "<init>", "()V");
    if (constructorMid == NULL) {
        return env->NewStringUTF("not find constructor method");
    }
    //new一个 ArrayList 对象
    jobject arrayList = env->NewObject(clazz, constructorMid);
    /*获取ArrayList的add方法的id*/
    jmethodID addMid = env->GetMethodID(clazz, "add", "(Ljava/lang/Object;)Z");


    /*获取Person的class*/
    jclass personCls = env->FindClass("com/example/nd/Person");
    /*获取Person的构造函数的id*/
    jmethodID personMid = env->GetMethodID(personCls, "<init>", "(ILjava/lang/String;)V");
    jint i = 0;
    for (; i < 3; ++i) {
        jstring name = env->NewStringUTF("Native");
        jobject person = env->NewObject(personCls, personMid, 18 + i, name);
        env->CallBooleanMethod(arrayList, addMid, person);
    }
    return arrayList;
}