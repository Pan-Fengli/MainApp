package com.dalab.dalabapp.TrainingPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.DataPage.HoeostasisDataPage;
import com.dalab.dalabapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingHomeostasis extends AppCompatActivity {
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
                intent.setClass(TrainingHomeostasis.this, HoeostasisDataPage.class);
                startActivity(intent);
            }
        });
    }
    void init()
    {
        hintInfo=findViewById(R.id.hintInfo);
        // 0,1,2,3 分别代表左上，右上，左下，右下
        type = getIntent().getIntExtra("TrainType",0);
        if(type == 0)
        {
            hintInfo.setText("左上肢肱动脉贯穿伤。\n急速、搏动性、鲜红色出血。");//\n换行符
            bound = findViewById(R.id.blood_bound_upper_right);
            wound = findViewById(R.id.blood_upper_right);
        }
        else if(type == 1)
        {
            hintInfo.setText("右上肢肱动脉贯穿伤。\n急速、搏动性、鲜红色出血。");
            bound = findViewById(R.id.blood_bound_upper_left);
            wound = findViewById(R.id.blood_upper_left);
        }
        else if(type == 2)
        {
            hintInfo.setText("左下肢肱动脉贯穿伤。\n急速、搏动性、鲜红色出血。");
            bound = findViewById(R.id.blood_bound_down_right);
            wound = findViewById(R.id.blood_down_right);
        }
        else
        {
            hintInfo.setText("右下肢肱动脉贯穿伤。\n急速、搏动性、鲜红色出血。");
            bound = findViewById(R.id.blood_bound_down_left);
            wound = findViewById(R.id.blood_down_left);
        }
    }
}
