package com.dalab.dalabapp.TrainingPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class ResHomeostasis extends AppCompatActivity {

    int overTime;
    int belowTime;
    int validTime;
    boolean loose;
    float lose;
    TextView tover, tbelow, tvalid, tloose, score, tres;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._res_page);
        overTime = getIntent().getIntExtra("overTime",0);
        belowTime = getIntent().getIntExtra("belowTime",0);
        validTime = getIntent().getIntExtra("validTime",0);
        loose = getIntent().getBooleanExtra("loose",false);
        lose = getIntent().getFloatExtra("lose", 0f);
//
        tover = findViewById(R.id.overText);
        tbelow = findViewById(R.id.belowText);
        tvalid = findViewById(R.id.validText);
        tloose = findViewById(R.id.loose);
        tres = findViewById(R.id._res);
        score=findViewById(R.id.score);
        tover.setText("压力过大时间:"+(float) overTime * 0.01+"s");
//        tbelow.setText("压力过低时间"+belowTime);
        tbelow.setText("失血量:"+belowTime+"ml");
        tvalid.setText("有效止血时间:"+(float) validTime * 0.01+"s");//emmm之后可以考虑换成分和秒的组合
        if(loose)
            tloose.setText("适当放松止血带√");
        else
            tloose.setText("适当放松止血带×");

        String grade="您当前的评分是"+getPoint();
        score.setText(grade);
    }

    // 简易的计算模型
    int getPoint()
    {
        String res = "";
        int score = 100;
        if(lose <= 1000)
        {
            res += "止血成功";
        }
        else if(lose > 1000 && lose <= 1500)
        {
            res += "面色发白，出冷汗";
            score -= 10;
        }
        else if(lose > 1500 && lose <= 2000) {
            res += "萎靡不振，手脚无力";
            score -= 20;
        }
        else {
            res += "昏迷休克";
            score -= 100;
        }
        if(!loose) {
            score -= 20;
            res += "。肢端坏死";
        }
        score = Math.max(score, 0);
        tres.setText(res);
        return score;
    }
}
