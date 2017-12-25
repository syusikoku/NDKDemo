package com.example.nd;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("hello");
    }

    private JNI jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jni = new JNI();

        /*卸载监听*/
        jni.callUnInstallListener(Build.VERSION.SDK_INT, "data/data/com.example.nd");
        addListener();
    }

    private void addListener() {
        findViewById(R.id.btn_action_getdata_from_c).setOnClickListener(this);
        findViewById(R.id.btn_action_reflect_jfield).setOnClickListener(this);
        findViewById(R.id.btn_action_reflect_static_jfield).setOnClickListener(this);
        findViewById(R.id.btn_action_reflect_private_jfield).setOnClickListener(this);
        findViewById(R.id.btn_action_public_method).setOnClickListener(this);
        findViewById(R.id.btn_action_private_method).setOnClickListener(this);
        findViewById(R.id.btn_action_parent_method).setOnClickListener(this);
        findViewById(R.id.btn_action_intarr_param).setOnClickListener(this);
        findViewById(R.id.btn_action_obj_param).setOnClickListener(this);
        findViewById(R.id.btn_action_objlist_param).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action_getdata_from_c:
                String resultStr = jni.getString();
                String msg = "C返回的数据: " + resultStr;
                Log.e("test", msg);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_reflect_jfield:
                int numValue = jni.add();
                String result = "结果: " + numValue;
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_reflect_static_jfield:
                Log.e("test", "调用前: " + JNI.name);
                Toast.makeText(this, "调用前: " + JNI.name, Toast.LENGTH_SHORT).show();
                jni.accessStaticField();
                Log.e("test", "调用后: " + JNI.name);
                Toast.makeText(this, "调用后: " + JNI.name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_reflect_private_jfield:
                String ageStr1 = "调用前: " + jni.getAge();
                Log.e("test", ageStr1);
                Toast.makeText(this, ageStr1, Toast.LENGTH_SHORT).show();
                jni.accessPrivateField();
                String ageStr2 = "调用后: " + jni.getAge();
                Log.e("test", ageStr2);
                Toast.makeText(this, ageStr2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_public_method:
                String sexStr1 = "调用前: " + jni.getSex();
                Log.e("test", sexStr1);
                Toast.makeText(this, sexStr1, Toast.LENGTH_SHORT).show();
                jni.accessPubicMethod();
                String sexStr2 = "调用后: " + jni.getSex();
                Log.e("test", sexStr2);
                Toast.makeText(this, sexStr2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_private_method:
                String sexPStr1 = "调用前: " + jni.getSex();
                Log.e("test", sexPStr1);
                Toast.makeText(this, sexPStr1, Toast.LENGTH_SHORT).show();
                jni.accessPrivateMethod();
                String sexPStr2 = "调用后: " + jni.getSex();
                Log.e("test", sexPStr2);
                Toast.makeText(this, sexPStr2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_parent_method:
                Toast.makeText(this, jni.accessSuperMethod(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_intarr_param:
                Toast.makeText(this, jni.intArrayMethod(new int[]{1, 2, 3, 4, 5, 6, 7}) + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_obj_param:
                Person person = new Person();
                Toast.makeText(this, jni.objectMethod(person).toString() + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_action_objlist_param:
                //调用
                ArrayList<Person> personList = new ArrayList<>();
                Person person1 = null;
                for (int i = 0; i < 3; i++) {
                    person1 = new Person();
                    person1.setName("cfanr");
                    person1.setAge(10 + i);
                    personList.add(person1);
                }
                Toast.makeText(this, "调用前： " + personList.toString() + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "调用后: " + jni.objectListMethod(personList).toString() + "", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
