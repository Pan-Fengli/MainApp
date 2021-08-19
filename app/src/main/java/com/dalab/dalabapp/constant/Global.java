package com.dalab.dalabapp.constant;

import android.app.Application;

public class Global extends Application {
    public float pressure=0;
    // 止血
    public int left_up_low_value;
    public int left_up_high_value;
    public int right_up_low_value;
    public int right_up_high_value;
    public int left_down_low_value;
    public int left_down_high_value;
    public int right_down_low_value;
    public int right_down_high_value;
    // 包扎
    public int up_low_value;
    public int up_high_value;
    public int down_low_value;
    public int down_high_value;
    public int height_value;
    public int weight_value;
    // 文字
    public static Global global;
    // 当前模式
    public int currentType = 0;
    // 时间设置
    public int hoeoTime = 1200; //
    public int bindTime = 30; //
    public int hoeoValidTime = 900; //
    // 分数区间设置
    public int hoeoVeryGood = 90;
    public int hoeoGood = 75;
    public int hoeoNormal = 60;
    public int bindVeryGood = 90;
    public int bindGood = 75;
    public int bindNormal = 60;
    // 计算公式
    public int hoeoDelayTime = 30;
    public int hoeoPressPunish = 90;
    public int hoeoReleasePunish = 60;
    public int bindPressPunish = 80;
    public int bindDelayTime = 30;
    @Override
    public void onCreate()
    {
        super.onCreate();
        global = this;
    }
}
