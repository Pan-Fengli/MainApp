package com.dalab.dalabapp.TrainingPages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.MainPage;
import com.dalab.dalabapp.R;
import com.dalab.dalabapp.SelfDefineViews.DrawLineChart;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingHomeostasis extends AppCompatActivity {
    ImageView wound;
    ImageView bound;
    ImageView body;
    TimerTask timerTask;
    TextView timerText;
    Timer timer1;
    Button button;

    ArrayList<Float> Values = new ArrayList<Float>();
    int sampleDistance = 5;//采样的间距，在demo里面体现为每点击n次按钮才会显示一次
    ArrayList<Float> storage = new ArrayList<Float>();
//    EditText lowerBound;
//    EditText upperBound;
//    int lowerValue = 200;
//    int upperValue = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);

        init();
        Values.add(0.0f);

        body = findViewById(R.id.body);
        bound = findViewById(R.id.blood_bound_upper_left);
        wound = findViewById(R.id.blood_upper_left);
        timerText = findViewById(R.id.timerText);
        button = findViewById(R.id.button2);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TrainingHomeostasis.this, MainPage.class);
                startActivity(intent);
            }
        });
        timer1 = new Timer();
        startTimer();
    }
    private void startTimer()
    {
        timerTask = new TimerTask() {
            int count;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count += 10;
                        changeWithSample();
//                        String tmp = getStringTime(count);
                        timerText.setText(getStringTime(count));
                        if(count >= 5000)
                        {
                            count += 600;
                        }
                        if(count >= 900000)
                        {
                            timer1.cancel();
                            wound.setVisibility(View.INVISIBLE);
                            bound.setVisibility(View.INVISIBLE);
                            timerText.setText("止血成功！！");
                            button.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        };
        timer1.schedule(timerTask,0,10);//每0.01s调用一次
    }
    private String getStringTime(int cnt)
    {
        int min =cnt/60000;
        int second=cnt%60000/1000;
        int minisecond=cnt%1000/10;
        return String.format(Locale.CHINA,"%02d:%02d:%02d",min,second,minisecond);
    }
    private void init() {
//        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.layout);//获取帧布局管理器，自己新建一个就可以了


        DrawLineChart chart = findViewById(R.id.chart);
        chart.setBrokenLineLTRB(50, 15, 10, 5);
        chart.setRadius(2.5f);
        chart.setCircleWidth(1f);
        chart.setBorderTextSize(15);//修改边框文字大小
        chart.setBrokenLineTextSize(10);//修改这线上文字大小
        chart.setMaxVlaue(600);
        chart.setMinValue(0);
        chart.setNumberLine(5);//5根线
        chart.setBorderWidth(1f);
        chart.setBrokenLineWidth(1.5f);
        chart.setBorderTransverseLineWidth(1.0f);//中间横线的宽度
        chart.setUpper(600.0f);
        chart.setLower(200.0f);

        Random random = new Random();
        float[] floats = new float[24];
        for (int i = 0; i < floats.length; i++) {
            float f = random.nextFloat();
            floats[i] = f * 60 - 10;
            Log.i("onCreate", "onCreate: f" + f);
        }
        chart.setValue(floats);
    }

    private void changeWithSample() {
        DrawLineChart chart = findViewById(R.id.chart);
        Random random = new Random();
        float f = random.nextFloat();
        float data = f * 600;//这个是生成的data，之后当然是用传递过来的数据
        storage.add(data);
        if (storage.size() == sampleDistance) {
            float average = 0;
            for (final Float value : storage) {
                average += value;
            }
            average /= sampleDistance;
            System.out.println("average：" + average);
            Values.add(average);
            float[] floats = new float[Values.size()];
            int index = 0;
            for (final Float value : Values) {
                floats[index++] = value;
            }
            chart.setValue(floats);
            chart.invalidate();//重绘
            storage.clear();
        }
    }
}
