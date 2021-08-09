package com.dalab.dalabapp.constant;

import android.app.Application;

public class Global extends Application {
    // 止血
    public int left_up_Low;
    public int left_up_High;
    public int right_up_Low;
    public int right_up_High;
    public int left_down_Low;
    public int left_down_High;
    public int right_down_Low;
    public int right_down_High;
    // 包扎
    public int up_Low;
    public int up_High;
    public int down_Low;
    public int down_High;
    // 文字
    public static Global global;
    // 当前模式
    public int currentType = 0;
    @Override
    public void onCreate()
    {
        super.onCreate();
        global = this;
    }
}
