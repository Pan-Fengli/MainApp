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
    TextView tover, tbelow, tvalid, tloose, score;
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
//
        tover = findViewById(R.id.overText);
        tbelow = findViewById(R.id.belowText);
        tvalid = findViewById(R.id.validText);
        tloose = findViewById(R.id.loose);
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
        return 89;
    }
}
