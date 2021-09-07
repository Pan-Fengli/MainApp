package com.dalab.dalabapp.TrainingPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.DataPage.BindDataPage;
import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TrainingBind extends AppCompatActivity {
    ImageView wound;
    ImageView bound;
    ImageView body;
    Button button;
    TextView hintInfo;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        init();
        wound.setVisibility(View.VISIBLE);
        bound.setVisibility(View.VISIBLE);
        body = findViewById(R.id.body);
        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TrainingBind.this, BindDataPage.class);
                startActivity(intent);
            }
        });
    }
    void init()
    {
        hintInfo=findViewById(R.id.hintInfo);
        // 0,1,2,3 分别代表左上，右上，坐下，右下
        type = getIntent().getIntExtra("TrainType",0);
        //生成随机数来决定是毛细血管还是静脉出血
        Random rand = new Random();
        int i;
        i=rand.nextInt(2);
        if(type == 0)
        {
            if(i==0)
            {
                hintInfo.setText("上肢毛细血管出血。\n呈小点状的红色血液，从伤口表面渗出，看不见明显的血管出血。");//\n换行符
            }
            else{
                hintInfo.setText("上肢静脉血管出血。\n暗红色的血液，迅速而持续不断地从伤口流出。");//\n换行符

            }

            bound = findViewById(R.id.blood_bound_upper_left);
            wound = findViewById(R.id.blood_upper_left);
        }
        else
        {
            if(i==0)
            {
                hintInfo.setText("下肢毛细血管出血。\n呈小点状的红色血液，从伤口表面渗出，看不见明显的血管出血。");//\n换行符
            }
            else{
                hintInfo.setText("下肢静脉血管出血。\n暗红色的血液，迅速而持续不断地从伤口流出。");//\n换行符

            }
            bound = findViewById(R.id.blood_bound_down_left);
            wound = findViewById(R.id.blood_down_left);
        }

    }
}
