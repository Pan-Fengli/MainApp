package com.dalab.dalabapp.TrainingPages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.HoeostasisDataPage;
import com.dalab.dalabapp.MainPage;
import com.dalab.dalabapp.R;
import com.dalab.dalabapp.SelfDefineViews.DrawLineChart;
import com.dalab.dalabapp.Trains.Hemostasis;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingHomeostasis extends AppCompatActivity {
    ImageView wound;
    ImageView bound;
    ImageView body;
    Button button;

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
        // 0,1,2,3 分别代表左上，右上，坐下，右下
        int type = getIntent().getExtras().getInt("TrainType");
        if(type == 0)
        {
            bound = findViewById(R.id.blood_bound_upper_left);
            wound = findViewById(R.id.blood_upper_left);
        }
        else if(type == 1)
        {
            bound = findViewById(R.id.blood_bound_upper_right);
            wound = findViewById(R.id.blood_upper_right);
        }
        else if(type == 2)
        {
            bound = findViewById(R.id.blood_bound_down_left);
            wound = findViewById(R.id.blood_down_left);
        }
        else
        {
            bound = findViewById(R.id.blood_bound_down_right);
            wound = findViewById(R.id.blood_down_right);
        }
    }
}
