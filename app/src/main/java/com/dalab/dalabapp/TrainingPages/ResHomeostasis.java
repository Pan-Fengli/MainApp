package com.dalab.dalabapp.TrainingPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.R;
import com.dalab.dalabapp.constant.Global;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class ResHomeostasis extends AppCompatActivity {
    int validTime = 0, delayTime = 900000, releaseTime = 0, overTime = 0;
    float lose = 2000;
    int shouldReleaseTime = 1000;
    TextView delayText, overText, validText, loseText, releaseText, scoreText, resText;
    TextView levelText;
    ImageView predictImage;
    ImageView broken;
    // 正常0 休克1 肢端坏死2
    int predictType = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._res_page);
//
        delayText = findViewById(R.id.delayText);
        overText = findViewById(R.id.overText);
        validText = findViewById(R.id.validText);
        loseText = findViewById(R.id.loseBloodText);
        releaseText = findViewById(R.id.loose);

        levelText = findViewById(R.id.Level);
        resText = findViewById(R.id._res);
        scoreText=findViewById(R.id.score);
        predictImage = findViewById(R.id.ResImage);
        broken=findViewById(R.id.broken_image);//这个其实需要根据我们训练的类型来改变,在下面会写到
        broken.setVisibility(View.INVISIBLE);
        // 获取计分相关项
        validTime = getIntent().getIntExtra("validTime", 0);
        lose = getIntent().getFloatExtra("lose", 2000);
        delayTime = getIntent().getIntExtra("delayTime", 900000);
        releaseTime = getIntent().getIntExtra("releaseTime", 0);
        overTime = getIntent().getIntExtra("overTime", 0);
        //
        int score = getPoint();
        // 设置页面
        setUI(score);
    }

    int getPoint()
    {
        // D延迟系数T有效时间系数L失血量系数R释放系数
        float D = Math.max(0, 1 - (float)delayTime/(Global.global.hoeoDelayTime * 1000));
        float T = 1;
        if(validTime < Global.global.hoeoValidTime)
            T = 0;
        float L = 1;
        if(lose > 1000)
        {
            L = Math.max(0, 1 - (float)(lose - 1000) / 1000);

        }
        if(lose > 2000)
            predictType = 1;
        float R = 1;
        if(releaseTime < shouldReleaseTime)
        {
            R = (float)Global.global.hoeoReleasePunish / 100;
            if(predictType == 0)
                predictType = 2;
        }
        if(overTime > 180000)
            R *= (float)Global.global.hoeoPressPunish / 100;
        System.out.println("D:"+D);
        System.out.println("T:"+T);
        System.out.println("L:"+L);
        System.out.println("R:"+R);
        return (int)(D * T * L * R * 100);
    }
    // 设置页面
    @SuppressLint("SetTextI18n")
    void setUI(int score)
    {
        delayText.setText("延迟" + getStringTime(delayTime));
        overText.setText("压力过大" + getStringTime(overTime));
        validText.setText("有效止血" + getStringTime(validTime));
        loseText.setText("失血量" + (int)lose);
        if(releaseTime >= shouldReleaseTime)
            releaseText.setText("适当放松√");
        else releaseText.setText("适当放松×");
        if(score >= Global.global.hoeoVeryGood)
            levelText.setText("优秀");
        else if(score >= Global.global.hoeoGood)
            levelText.setText("良好");
        else if(score >= Global.global.hoeoNormal)
            levelText.setText("合格");
        else levelText.setText("不及格");
        scoreText.setText("得分：" + score);
        // 结果预测
        if(predictType == 0)
        {
            int id = this.getResources().getIdentifier("predict_strong", "drawable", this.getPackageName());
            predictImage.setImageResource(id);
            resText.setText("止血成功");
        }
        else if(predictType == 1)
        {
            int id = this.getResources().getIdentifier("predict_shock", "drawable", this.getPackageName());
            predictImage.setImageResource(id);
            resText.setText("休克");
        }
        else
        {
            resText.setText("肢端坏死");
            //可以动态修改图片的源,比如这里改成了left_up_broken
            int id;
            if(Global.global.currentType==20)//左上
            {
                id= this.getResources().getIdentifier("left_up_broken", "drawable", this.getPackageName());
            }
            else if(Global.global.currentType==21)//右上
            {
                id= this.getResources().getIdentifier("right_up_broken", "drawable", this.getPackageName());
            }
            else if(Global.global.currentType==22)//左下
            {
                id= this.getResources().getIdentifier("left_down_broken", "drawable", this.getPackageName());
            }
            else{
                id= this.getResources().getIdentifier("right_down_broken", "drawable", this.getPackageName());
            }

            broken.setImageResource(id);
//            然后展示坏死的动画
            broken.setVisibility(View.VISIBLE);
            Animation merge=new AlphaAnimation(0.4f,0.9f);
            merge.setDuration(1000);//1s
            merge.setFillAfter(true);
            merge.setFillBefore(true);
            merge.setRepeatCount(-1);
            merge.setRepeatMode(Animation.REVERSE);
            broken.startAnimation(merge);
        }
    }

    private String getStringTime(int cnt) {
        int min = cnt / 60000;
        int second = cnt % 60000 / 1000;
        return String.format(Locale.CHINA, "%02d:%02d", min, second);
    }
}
