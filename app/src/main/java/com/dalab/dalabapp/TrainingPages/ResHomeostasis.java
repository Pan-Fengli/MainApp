package com.dalab.dalabapp.TrainingPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class ResHomeostasis extends AppCompatActivity {

    int overTime;
    int validTime;
    boolean loose;
    float lose;
    TextView tover, tvalid, tlose, tloose, score, tres;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._res_page);
        overTime = getIntent().getIntExtra("overTime",0);
        validTime = getIntent().getIntExtra("validTime",0);
        lose = getIntent().getFloatExtra("lose", 0f);
//
        tover = findViewById(R.id.overText);
        tvalid = findViewById(R.id.validText);
        tlose = findViewById(R.id.loseBloodText);
        tloose = findViewById(R.id.loose);

        tres = findViewById(R.id._res);
        score=findViewById(R.id.score);
        tover.setText("压力过大时间:"+ (float)overTime+"s");
//        tbelow.setText("压力过低时间"+belowTime);
        tlose.setText("失血量:"+lose+"ml");
        tvalid.setText("有效止血时间:"+(float)validTime+"s");// emmm之后可以考虑换成分和秒的组合
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
        tres.setText("你做得很好!");
        float D = 1;
        float T = 1;
        // 失血量
        float L = 1;
        if(lose > 1000)
        {
            L -= (lose - 1000) / 1000;
            L = Math.max(L, 0);
        }
        // 放松
        float R;
        if(loose)
            R = 1;
        else R = 0.6f;
        return (int)(D*L*T*R * 100);
    }
}
