package com.dalab.dalabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Script;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.SelfDefineViews.DrawLineChart;

import com.dalab.dalabapp.TrainingPages.TrainingHomeostasis;

import com.dalab.dalabapp.TrainingPages.ResHomeostasis;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HoeostasisDataPage extends AppCompatActivity {
    TimerTask timerTask;
    TextView timerText;
    TextView validTimeText;
    TextView forceText;
    ImageView heartImage;
    TextView percentText;
    TextView bleedText;
    TextView stateText;
    TextView infoText;
    Button jump;

    int percent;
    Timer timer1;
    ArrayList<Float> Values = new ArrayList<Float>();
    int sampleDistance = 10;//采样的间距，在demo里面体现为每点击n次按钮才会显示一次
    ArrayList<Float> storage = new ArrayList<Float>();
    int lowerValue = 200;
    int upperValue = 600;
    int Volumn = 2000;//满血是2000ml，即流血量大于这个就会休克
    float lose = 0;//流血量

    int overTime = 0;//这两个是用来记录小于bond和大于bond的时间的
    int lowerTime = 0;


    int mode = 0;//这是一个随机数，用来表示我生成的数据是模拟：0:压力过小，止血失败。1：压力在范围内，止血成功。2：压力过大，肢体失血坏死
    int max = 600;
    int min = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoeostasis_data_page);
//        findViewById(R.id.nextPage).setVisibility(View.INVISIBLE);
        heartImage = findViewById(R.id.imageView3);
        bleedText = findViewById(R.id.textView3);
        stateText = findViewById(R.id.textView6);
        infoText = findViewById(R.id.textView2);
        percentText = findViewById(R.id.percent);
        percent = Integer.valueOf(percentText.getText().toString());
        changeImage(100);//
        heartBeat(1000);

        lowerValue = MainPage.lowerValue;
        upperValue = MainPage.upperValue;
        lowerValue = MainPage.left_up_low_value.value;
        upperValue = MainPage.left_up_high_value.value;

        Random random = new Random();
        mode = random.nextInt(3);//0,1,2
        if (mode == 0) {
            max = 300;
            min = 100;
        } else if (mode == 1) {
            max = 600;
            min = 200;
        } else if (mode == 2) {
            max = 700;
            min = 500;
        }
        init();//初始化坐标数据
        Values.add(0.0f);
        timerText = findViewById(R.id.timerText);
        validTimeText = findViewById(R.id.timerText2);
        validTimeText.setTextColor(Color.rgb(255, 130, 71));//有效时间和训练时间用不同的颜色
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
                nextPage(view);
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
//        chart.setMaxVlaue(600);
        chart.setMaxVlaue(750);
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

                        if (count >= 5000) {//5s的时候
                            speed = 800;
                            //或许还可以加上其他的提示信息
                            infoText.setText("时间加速跳动到15min...");
                            acceleration = true;
                        }
                        if (count >= 900000) {//15min
                            speed = 213;//15min之后流速又减慢一点点
                            infoText.setText("请稍微放松止血带");
                            release = true;//提示松开，之后的数据生成就是松开的数据...
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

        //计算流血量
        if (data < lowerValue && !release && !acceleration) {//需要加上一个条件，如果小于范围并且还没有发出松开的指令（即15min的时候的指令。）
            //以及试了试，在我们加速的时候也不应该记这个——或者说速度应该和正常速度一样
//            lowerTime++;
            //调用流血的展示函数——那么既然压力过小会流血，那么就在这里计算一下流血量就ok了嘛——当然还是得在外面计算，因为speed会变化...
            minus();
        }

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
            //以及处理全局变量，lower&overvalue来为评分做准备
//            updateValue((int)average);
//            //调用流血的展示函数
//            minus();
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
                loseBlood(validTime);
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

    private void updateValue(int cnt) {
        if (cnt > upperValue && !release && !acceleration)//——这里的记录是全程记录，那么时间不如就算成现实的时间，也就是和interval 的0.01s。不像lower还需要计算出血量...——不过当然计算是放在下个页面了罢
        {
            overTime++;
        }
    }

    // UI相关
    private void checkColor(int cnt) {
        if (cnt < lowerValue) {
            forceText.setTextColor(Color.rgb(255, 160, 0));
        } else if (lowerValue < cnt && cnt < upperValue) {
            forceText.setTextColor(Color.rgb(0, 238, 0));
        } else {
            forceText.setTextColor(Color.RED);
        }
    }

    private void changeImage(int oldPercent) {

        int number = (100 - percent) * 45 / 100;//得到图片的编号
        //甚至可以检查number有无变化来决定是否重载资源——所以传入的是old的percent
        int oldNumber = (100 - oldPercent) * 45 / 100;
        if (oldNumber == number) {
            return;
        }
        String name = "h" + String.valueOf(number);
        //根据文件名字来获得id号
        int id = this.getResources().getIdentifier(name, "drawable", this.getPackageName());
        heartImage.setImageResource(id);

        //心跳频率也会变化
        int interval = 1000 - number * 18;//血越少频率越快
        heartBeat(interval);
    }

    private void heartBeat(int interval) {
        Animation blur = new AlphaAnimation(1.0f, 0.85f);
        blur.setDuration(interval);//1s
        blur.setFillAfter(true);
        blur.setFillBefore(true);
        blur.setRepeatCount(-1);//-1就是无穷次
        blur.setRepeatMode(Animation.REVERSE);

        //跟着一起缩放
        Animation scale = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(interval);
        scale.setFillAfter(true);
        scale.setFillBefore(true);
        scale.setRepeatCount(-1);// -1就是无穷次
        scale.setRepeatMode(Animation.REVERSE);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(blur);
        animationSet.addAnimation(scale);
        heartImage.startAnimation(animationSet);
    }

    float bleedspeed = 26.9f;//如果是下肢，那么就是171.1f

    private void minus()//流血的计算和更新
    {
        if (percent <= 0)//不能再变小了
        {
            String zero = "0";
            percentText.setText(zero);
            return;
        }
        int oldPercent = percent;
//        lose += 10;//流血量变多
        lose += speed * (bleedspeed / 1000.0f);//流血量变多//speed是时间流速，ms单位，所以需要转换一下——lose的类型改成float是为了防止因为过小而导致的流血量不能加上去。
        String bleed = "失血量：" + (int) lose + "ml";
        bleedText.setText(bleed);
        updateState();
        percent = (Volumn - (int) lose) * 100 / Volumn;//计算之后的比例
        String percentage = String.valueOf(percent);
        percentText.setText(percentage);
        changeImage(oldPercent);
    }
    private void loseBlood(int time)//流血的计算和更新
    {
        if (percent <= 0)//不能再变小了
        {
            String zero = "0";
            percentText.setText(zero);
            return;
        }
        int oldPercent = percent;
//        lose += 10;//流血量变多
        lose += time * (bleedspeed / 1000.0f);//流血量变多//time是，ms单位，所以需要转换一下——lose的类型改成float是为了防止因为过小而导致的流血量不能加上去。
        String bleed = "失血量：" + (int) lose + "ml";
        bleedText.setText(bleed);
        updateState();
        percent = (Volumn - (int) lose) * 100 / Volumn;//计算之后的比例
        String percentage = String.valueOf(percent);
        percentText.setText(percentage);
        changeImage(oldPercent);
    }

    private void updateState()//根据流血量来更新状态
    {
        if (lose < 500) {
            String state = "当前状态:" + "没有明显症状";
            stateText.setText(state);
        } else if (lose >= 500 && lose < 1000) {
            String state = "当前状态:" + "神经焦虑";
            stateText.setText(state);
        } else if (lose > 1000 && lose < 1500) {
            String state = "当前状态:" + "面色发白，出冷汗";
            stateText.setText(state);
        } else if (lose > 1500 && lose < 2000) {
            String state = "当前状态:" + "萎靡不振，手脚无力";
            stateText.setText(state);
        } else if (lose > 2000) {
            String state = "当前状态:" + "昏迷休克";
            stateText.setText(state);
        }
    }

    boolean release = false;
    int decline_speed = 1;//下降的速度——10好像有点太快了...
    int declineValue = decline_speed;
    int releaseTime = 0;

    private int generateData() {
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

    public void nextPage(View view) {
        //这个函数是设计给跳转按钮的，需要传递一些数据过去到评价页面。
        Intent intent = new Intent();
        intent.setClass(HoeostasisDataPage.this, ResHomeostasis.class);
        //然后把一些参数输入进去。
        intent.putExtra("overTime", overTime * interval);//ms
        intent.putExtra("belowTime", lose);//float类型
        intent.putExtra("validTime", validTime);//单位是ms
        intent.putExtra("hasReleased", hasReleased);
        intent.putExtra("lose", lose);

        startActivity(intent);
    }

}
