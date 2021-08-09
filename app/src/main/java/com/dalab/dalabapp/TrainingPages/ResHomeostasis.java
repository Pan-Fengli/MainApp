package com.dalab.dalabapp.TrainingPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.MainPage;
import com.dalab.dalabapp.R;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class ResHomeostasis extends AppCompatActivity {
    int validTime = 0, delayTime = 900000, releaseTime = 0, overTime = 0;
    float lose = 2000;
    TextView delayText, overText, validText, loseText, releaseText, scoreText, resText;
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

        resText = findViewById(R.id._res);
        scoreText=findViewById(R.id.score);
        predictImage = findViewById(R.id.ResImage);
        broken=findViewById(R.id.left_up_broken);//这个其实需要根据我们训练的类型来改变，这里以左上肢为例。
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

    // 简易的计算模型
    int getPoint()
    {
        // D延迟系数T有效时间系数L失血量系数R释放系数
        float D = Math.max(0, 1 - (float)delayTime/30000);
        float T = 1;
        if(validTime < 900000)
            T = 0;
        float L = 1;
        if(lose > 1000)
        {
            L = Math.max(0, 1 - (float)(lose - 1000) / 1000);
            predictType = 1;
        }
        float R = 1;
        if(releaseTime < 180000)
        {
            R = 0.6f;
            if(predictType == 0)
                predictType = 2;
        }
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
        releaseText.setText("放松" + getStringTime(releaseTime));

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
//            int id = this.getResources().getIdentifier("predict_shock", "drawable", this.getPackageName());
//            predictImage.setImageResource(id);
            resText.setText("肢端坏死");
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
