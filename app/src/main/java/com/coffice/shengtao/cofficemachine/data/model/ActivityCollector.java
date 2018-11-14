package com.coffice.shengtao.cofficemachine.data.model;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityCollector {
    public static ArrayList<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void onKeyExit(){
        for(Activity activity:activities){
            if(activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

// 使用 方法-----
//    public class MainActivity extends AppCompatActivity {
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//            ActivityCollector.addActivity(this);//在所有 Activity 的 onCreate 方法中添加自身的实例
//        }
//
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            ActivityCollector.removeActivity(this);//在所有 Activity 的 onDestroy 方法中移除自身的实例
//        }
//
//        public void oneKeyExit(View view) {
//            ActivityCollector.onKeyExit();//在 list 中结束所有活动实例
//            killProcess(android.os.Process.myPid());//方法一、杀掉当前程序的进程
//            //System.exit(0);//方法二、 java 中的退出当前线程的方法
//        }
//    }

}
