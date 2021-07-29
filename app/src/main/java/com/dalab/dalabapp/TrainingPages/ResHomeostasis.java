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
//        overTime = getIntent().getExtras().getInt("overTime");
//        belowTime = getIntent().getExtras().getInt("belowTime");
//        validTime = getIntent().getExtras().getInt("validTime");
//        loose = getIntent().getExtras().getBoolean("loose");
//
//        tover = findViewById(R.id.overText);
//        tbelow = findViewById(R.id.belowText);
//        tvalid = findViewById(R.id.validText);
//        tloose = findViewById(R.id.loose);
//        tover.setText("有效止血时间"+overTime);
//        tbelow.setText("压力过低时间"+belowTime);
//        tvalid.setText("有效止血时间"+validTime);
//        if(loose)
//            tloose.setText("适当放松止血带√");
//        else
//            tloose.setText("适当放松止血带×");
//
//        score.setText("您当前的评分是"+getPoint());
    }

    // 简易的计算模型
    int getPoint()
    {
        return 89;
    }
}
