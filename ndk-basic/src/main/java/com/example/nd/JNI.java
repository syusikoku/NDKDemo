package com.example.nd;

import java.util.ArrayList;

/**
 * Created by zzg on 2017/12/22.
 */

public class JNI extends SuperJni {

    public native String stringFromJNI();

    public native String getString();

    public native int plus(int a, int b);

    /*卸载监听*/
    public native void callUnInstallListener(int versionSdk, String path);

    /********************************* JNI 函数访问 Java 对象的变量 *********************************/
    /*
     * 1）通过env->GetObjectClass(jobject)获取Java 对象的 class 类，返回一个 jclass；
       2）调用env->GetFieldID(jclazz, fieldName, signature)得到该实例域（变量）的 id，即 jfieldID；如果变量是静态 static 的，则调用的方法为 GetStaticFieldID
       3）最后通过调用env->Get{type}Field(jobject, fieldId) 得到该变量的值。其中{type} 是变量的类型；如果变量是静态 static 的，则调用的方法是GetStatic{type}Field(jclass, fieldId)，注意 static 的话， 是使用 jclass 作为参数；
     */
    /* ---------- 变量访问 --->> 访问某个变量，并通过某个方法对其处理后返回 ----------- */
    public int num = 10;

    /**
     * 在num基础上+1返回显示
     */
    public native int add();

    /* ----------  变量访问 --->>  访问某个变量，并通过某个方法对其处理后返回 ----------- */

    /* ----------  变量访问 --->>  访问一个 static 变量，并对其修改 ----------- */
    public static String name = "szzynt";

    public native void accessStaticField();

    /* ----------  变量访问 --->>  访问一个 static 变量，并对其修改 ----------- */

    /* ---------- 变量访问 --->> 访问一个 private 变量，并对其修改 ----------- */
    private int age = 21;

    public native void accessPrivateField();

    public int getAge() {
        return age;
    }
    /* ---------- 变量访问 --->> 访问一个 private 变量，并对其修改 ----------- */
    /********************************* JNI 函数访问 Java 对象的变量 *********************************/

    /********************************* JNI 函数调用 Java 对象的方法 *********************************/
    /*
     * 1）通过env->GetObjectClass(jobject)获取Java 对象的 class 类，返回一个 jclass；
       2）通过env->GetMethodID(jclass, methodName, sign)获取到 Java 对象的方法 Id，即 jmethodID，当获取的方法是 static 的时，使用GetStaticMethodID;
       3）通过 JNI 函数env->Call{type}Method(jobject, jmethod, param...)实现调用 Java的方法；若调用的是 static 方法，则使用CallStatic{type}Method(jclass, jmethod, param...)，使用的是 jclass
     */
    private String sex = "female";

    public void setSex(String sex) {
        this.sex = sex;
    }

    private void setHideSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    /*调用公有方法*/
    public native void accessPubicMethod();

    /*调用私有方法*/
    public native void accessPrivateMethod();

    /*调用父类方法*/
    public native String accessSuperMethod();
    /********************************* JNI 函数调用 Java 对象的方法 *********************************/

    /********************************* Java 方法传递参数给 JNI 函数 *********************************/
    /**
     * native 方法既可以传递基本类型参数给 JNI（可以不经过转换直接使用），也可以传递复杂的类型（需要转换为 C/C++ 的数据结构才能使用），如数组，String 或自定义的类等。
     * 基础类型，这里就不举例子了，详细可以看 GitHub 上的源码： AndroidTrainingDemo/JNISample
     * 要用到的 JNI 函数：
     *
     * 获取数组长度：GetArrayLength(j{type}Array)，type 为基础类型；
     * 数组转换为对应类型的指针：Get{type}ArrayElements(jarr, 0)
     * 获取构造函数的 jmethodID 时，仍然是用env->GetMethodID(jclass, methodName, sign)获取，方法 name 是<init>；
     * 通过构造函数 new 一个 jobject，env->NewObject(jclass, constructorMethodID, param...)，无参构造函数 param 则为空
     */
    public native int intArrayMethod(int[] arr);

    //传递复杂对象person，再jni函数中新构造一个person传回java层输出
    public native Person objectMethod(Person person);

    public native ArrayList<Person> objectListMethod(ArrayList<Person> persons);
    /********************************* Java 方法传递参数给 JNI 函数 *********************************/

}
