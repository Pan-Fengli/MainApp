package com.dalab.dalabapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    Chronometer timer;
    boolean hang=false;
    Button Pause;
    TextView textView;
    long pauseTime;
    Timer timer1;
    TimerTask timerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer=findViewById(R.id.timer);
//        Pause=findViewById(R.id.pause);
//        textView=findViewById(R.id.textView);
        timer1=new Timer();
    }
    public void Exit(View view)
    {
        finish();
    }

    public void BtnStart(View view)
    {
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        //不用显示小时
//        int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
//        timer.setFormat("0"+String.valueOf(hour)+":%s");
//        timer.setFormat("00:%s");
        timer.start();

    }
    public void BtnStop(View view){
//        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        //如果我不清零？
        timer.stop();
    }
    public void PauseAndStart(View view){
        if(hang)//现在是暂停状态 文本是continue
        {
//            timer.setBase(pauseTime);
//            timer.start();
            Pause.setText("Pause");
            hang=!hang;
        }
        else{//点击之后暂停
//            timer.stop();
//            pauseTime=timer.getBase();
            Pause.setText("Continue");
            hang=!hang;
        }

    }

    public void startClick(View view)
    {
        timerTask=new TimerTask() {
            int cnt=0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if(!hang)
//                        {
//                            //如果暂停了，那么cnt就不变化；
//                            cnt++;
//                        }
                        cnt+=10;
                        textView.setText(getStringTime(cnt));
                        //还要检查一下颜色
                        checkColor(cnt);
                    }
                });
            }
        };
        timer1.schedule(timerTask,0,10);//每0.01s调用一次
    }

    public void checkColor(int cnt)
    {
        if(cnt<2000)
        {
//            textView.setTextColor(Color.YELLOW);//颜色调一下，不要太淡
            textView.setTextColor(Color.rgb(255,160,0));
        }
        else if(2000 < cnt && cnt <5000)
        {
            textView.setTextColor(Color.rgb(0,238,0));
        }
        else{
            textView.setTextColor(Color.RED);
        }
    }


    public void stopClick(View view)
    {
        if(!timerTask.cancel())
        {
            timerTask.cancel();
            timer1.cancel();
        }
    }
    private String getStringTime(int cnt)
    {
        int min =cnt/60000;
        int second=cnt%60000/1000;
        int minisecond=cnt%1000/10;
        return String.format(Locale.CHINA,"%02d:%02d:%02d",min,second,minisecond);
    }
}

