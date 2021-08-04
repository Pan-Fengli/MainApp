package com.dalab.dalabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.SelfDefineViews.DrawLineChart;
import com.dalab.dalabapp.TrainingPages.ResHomeostasis;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BindDataPage extends AppCompatActivity {
    TimerTask timerTask;
    TextView timerText;
    TextView validTimeText;
    TextView forceText;

    TextView infoText;
    Button jump;


    Timer timer1;
    ArrayList<Float> Values = new ArrayList<Float>();
    int sampleDistance = 10;//采样的间距，在demo里面体现为每点击n次按钮才会显示一次
    ArrayList<Float> storage = new ArrayList<Float>();
    int lowerValue = 10;
    int upperValue = 35;


    int overTime = 0;//这两个是用来记录小于bond和大于bond的时间的
    int lowerTime = 0;


    int mode = 0;//这是一个随机数，用来表示我生成的数据是模拟：0:压力过小，止血失败。1：压力在范围内，止血成功。2：压力过大，肢体失血坏死
    int max = 35;//随机生成的上下限
    int min = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_data_page);

        infoText = findViewById(R.id.textView2);
        lowerValue = MainPage.up_low_value.value;
        upperValue = MainPage.up_high_value.value;
        Random random = new Random();
        mode = random.nextInt(3);//0,1,2
        if (mode == 0) {//过小
            max = 15;
            min = 5;
        } else if (mode == 1) {
            max = 35;
            min = 10;
        } else if (mode == 2) {
            max = 50;
            min = 30;
        }

        init();//初始化坐标数据

        Values.add(0.0f);
        timerText = findViewById(R.id.timerText);
//        validTimeText = findViewById(R.id.textView7);
        validTimeText = findViewById(R.id.timerText2);
        validTimeText.setTextColor(Color.rgb(255, 130, 71));
        timerText.setTextColor(Color.rgb(0, 238, 0));
        forceText = findViewById(R.id.forceText);
        timer1 = new Timer();
        startTimer();
        // jumpRelated
        jump = findViewById(R.id.JumpToRes);
        jump.setVisibility(View.INVISIBLE);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(BindDataPage.this, ResHomeostasis.class);
                //此外，这里还需要传递一些数据到下一个页面去，比如：
                intent.putExtra("overTime", overTime * 10);//超过上限的时间，单位s——(float) overTime * 0.01已经转化成s了，看是否需要保留ms？
//                intent.putExtra("belowTime", (int)lose);//流血量，float类型 单位ml
                intent.putExtra("validTime", validTime);//有效止血时间，单位是ms
                intent.putExtra("loose", hasReleased);//最后是否已经松开，布尔值
                startActivity(intent);
            }
        });
    }

    private void init() {
        DrawLineChart chart = findViewById(R.id.chart);
        chart.setBrokenLineLTRB(50, 15, 10, 5);
        chart.setRadius(2.5f);
        chart.setCircleWidth(1f);
        chart.setBorderTextSize(15);//修改边框文字大小
        chart.setBrokenLineTextSize(10);//修改这线上文字大小
        chart.setMaxVlaue(60);
        chart.setMinValue(0);
        chart.setNumberLine(4);//5根线
        chart.setBorderWidth(1f);
        chart.setBrokenLineWidth(1.5f);
        chart.setBorderTransverseLineWidth(1.0f);//中间横线的宽度
        chart.setUpper((float) upperValue);//这两个就是上下限范围...
        chart.setLower((float) lowerValue);
    }
    int speed = 10;//时间流速——一次interval中流过了多少毫秒。
    int interval = 10;//interval和speed一样的话就是现实中的流速。
    boolean acceleration = false;
    boolean hasReleased = false;//到达20min的时候是否已经松开

    private void startTimer() {
        timerTask = new TimerTask() {
            int count;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        count += 10;

                        if (count >= 5000) {//5s的时候
//                            count += 1000;
                            speed = 1800;

                            //颜色变化作为提示
//                            timerText.setTextColor(Color.rgb(255, 120, 71));
                            //或许还可以加上其他的提示信息
                            infoText.setText("时间加速跳动到15min...");
                            acceleration = true;
                        }
                        if (count >= 900000) {//15min
                            speed = 213;//15min之后流速又减慢一点点
//                            timerText.setText("止血成功！！");
                            infoText.setText("15min！！");
                            release = true;//提示松开，之后的数据生成就是松开的数据...
                            //然后此刻开始计时，时间用于判断是主动松开还是被动松开——实际上这个计时还是在generateData函数里面去进行
                        }
                        count += speed;
                        changeWithSample();
                        timerText.setText(getStringTime(count));
                        if (count >= 20 * 60 * 1000)//20min
                        {

                            timerText.setText("训练完成");
                            //这里还应该检查一下压力是否已经小于了200，也就是是否已经松掉。可以作为一个布尔值传递过去。
                            hasReleased = true;//因为根据我们生成的数据，肯定是松掉了的
                            System.out.println("lowerTime:" + lowerTime);
                            System.out.println("overTime:" + (float) overTime * 0.01);//转化成s的单位——或者乘10变成毫秒？
                            System.out.println("releaseTime:" + releaseTime);//我们需要用这个来判断主动松开还是被动松开——这个本身的含义是我们发出松开指令之后到压力降低到范围之下的时间
                            speed = 10;//速度变回去（有必要吗？）

//                             timerText.setText("20min！！训练完成，进入评价页面");
//                             System.out.println("lowerTime:"+lowerTime);
//                             System.out.println("overTime:"+overTime);
//                             System.out.println("releaseTime:"+releaseTime);//我们需要用这个来判断主动松开还是被动松开——这个本身的含义是我们发出松开指令之后到压力降低到范围之下的时间
//                             speed=10;//速度变回去（有必要吗？）

//                            findViewById(R.id.nextPage).setVisibility(View.VISIBLE);
                            timer1.cancel();
                            jump.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
        };
        timer1.schedule(timerTask, 0, interval);//每0.01s调用一次//period实际上也就是interval
    }
    private String getStringTime(int cnt) {
        int min = cnt / 60000;
        int second = cnt % 60000 / 1000;
        int minisecond = cnt % 1000 / 10;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", min, second, minisecond);
    }

    private String getMinStringTime(int cnt) {//有效时间可以不用那么精细，可以不用精确到毫秒...
        int min = cnt / 60000;
        int second = cnt % 60000 / 1000;
//        int minisecond = cnt % 1000 / 10;
        return String.format(Locale.CHINA, "%02d:%02d", min, second);
    }
    private void changeWithSample() {//每次时间数据更新都调用这个函数，在这个函数里面模拟数据的生成和更新
        DrawLineChart chart = findViewById(R.id.chart);

        Random random = new Random();
        float f = random.nextFloat();
        float data = f * 600;//这个是生成的data，之后当然是用传递过来的数据
        //可以写成一个函数来生成这里的data
        data = generateData();

        storage.add(data);
        //更新有效止血时间——这个只能放在外面每次都更新，不能够存满了才更新一次，否则会因为speed发生了变化而产生问题
        updateValidTime(data);

        updateValue((int) data);//计算超过最大范围的时间


        if (storage.size() == sampleDistance) {//存满了之后才计算一次。
            float average = 0;
            for (final Float value : storage) {
                average += value;
            }
            average /= sampleDistance;
            Values.add(average);
            float[] floats = new float[Values.size()];
            int index = 0;
            for (final Float value : Values) {
                floats[index++] = value;
            }
            chart.setValue(floats);
            chart.invalidate();//重绘
            storage.clear();

            //最后再修改text
            String newForce = String.valueOf((int) average) + "mmHg";
            forceText.setText(newForce);
            //还要检查一下颜色
            checkColor((int) average);
        }
    }
    private void updateValue(int cnt) {
        if (cnt > upperValue && !release && !acceleration)//——这里的记录是全程记录，那么时间不如就算成现实的时间，也就是和interval 的0.01s。不像lower还需要计算出血量...——不过当然计算是放在下个页面了罢
        {
            overTime++;
        }
    }
    int threshold=2000;//2s?
    int validTime = 0;//这次的有效时间
    int validLastTime=0;//上次的有效时间
    boolean valid=false;
    private void updateValidTime(float data) {
        if (lowerValue < data && data < upperValue) {
            valid=true;//第一次进去之后设置为true
            validTime += speed;//单位仍然是毫秒
            //然后更新text
//            String text = "有效止血时间：" + getMinStringTime(validTime);
            String text = getMinStringTime(validTime+validLastTime);
            validTimeText.setText(text);
        }
        else if(data <lowerValue &&valid )
        {
            //从有效区间变成了无效区间
            if(validTime<threshold)
            {
                //用上次的validtime计算流血量
//                loseBlood(validTime);
//                float bleed=validTime*bleedspeed;
            }
            else{
                validLastTime=validLastTime+validTime;
            }

            valid=false;
            validTime=0;//
            String text = getMinStringTime(validTime+validLastTime);
            validTimeText.setText(text);
        }
    }
    private void checkColor(int cnt) {
        if (cnt < lowerValue) {
            forceText.setTextColor(Color.rgb(255, 160, 0));
        } else if (lowerValue < cnt && cnt < upperValue) {
            forceText.setTextColor(Color.rgb(0, 238, 0));
        } else {
            forceText.setTextColor(Color.RED);
        }
    }

    boolean release = false;
    int decline_speed = 1;//下降的速度——10好像有点太快了...
    int declineValue = decline_speed;
    int releaseTime = 0;

    private int generateData() {//生成数据
        //判断是否已经发出了release的指令decline？
        Random random = new Random();
        int value, range;
        if (!release)//还没有松开
        {
            range = max - min;
            value = min + random.nextInt(range);
        } else {//开始松开
            declineValue += decline_speed;

            value = max - declineValue;
            if (value <= 0) {
                value = 0;
            }
            if (value > lowerValue) {
                releaseTime++;
            }
        }
        return value;
    }
}
