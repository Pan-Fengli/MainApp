package com.dalab.dalabapp.TrainingPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.MainPage;
import com.dalab.dalabapp.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingHomeostasis extends AppCompatActivity {
    ImageView wound;
    ImageView bound;
    ImageView body;
    TimerTask timerTask;
    TextView timerText;
    Timer timer1;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        body = findViewById(R.id.body);
        bound = findViewById(R.id.blood_bound_upper_left);
        wound = findViewById(R.id.blood_upper_left);
        timerText = findViewById(R.id.timerText);
        button = findViewById(R.id.button2);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TrainingHomeostasis.this, MainPage.class);
                startActivity(intent);
            }
        });
        timer1 = new Timer();
        startTimer();
    }
    private void startTimer()
    {
        timerTask = new TimerTask() {
            int count;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count += 10;
//                        String tmp = getStringTime(count);
                        timerText.setText(getStringTime(count));
                        if(count >= 5000)
                        {
                            count += 600;
                        }
                        if(count >= 900000)
                        {
                            timer1.cancel();
                            wound.setVisibility(View.INVISIBLE);
                            bound.setVisibility(View.INVISIBLE);
                            timerText.setText("止血成功！！");
                            button.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        };
        timer1.schedule(timerTask,0,10);//每0.01s调用一次
    }
    private String getStringTime(int cnt)
    {
        int min =cnt/60000;
        int second=cnt%60000/1000;
        int minisecond=cnt%1000/10;
        return String.format(Locale.CHINA,"%02d:%02d:%02d",min,second,minisecond);
    }
}
