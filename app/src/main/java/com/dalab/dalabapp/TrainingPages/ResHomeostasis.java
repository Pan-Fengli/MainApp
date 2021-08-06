package com.dalab.dalabapp.TrainingPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class ResHomeostasis extends AppCompatActivity {
    int validTime, delayTime, releaseTime, overTime;
    float lose;
    TextView delayText, overText, validText, loseText, releaseText, scoreText, resText;
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
        // 获取计分相关项
//        validTime = getIntent().getIntExtra("validTime", 0);
//        lose = getIntent().getFloatExtra("lose", 2000);
//        delayTime = getIntent().getIntExtra("delayTime", 900000);
//        releaseTime = getIntent().getIntExtra("releaseTime", 0);
//        overTime = getIntent().getIntExtra("overTime", 0);
        //
        int score = getPoint();
        // 设置页面
        setUI(score);
    }

    // 简易的计算模型
    int getPoint()
    {
        return 100;
    }
    // 设置页面
    void setUI(int score)
    {
        delayText.setText(delayTime);
        overText.setText(overTime);
        validText.setText(validTime);
        loseText.setText((int)lose);
        releaseText.setText(releaseTime);
    }
}
