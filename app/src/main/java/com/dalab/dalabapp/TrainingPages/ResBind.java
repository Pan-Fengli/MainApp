package com.dalab.dalabapp.TrainingPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalab.dalabapp.R;
import com.dalab.dalabapp.constant.Global;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class ResBind extends AppCompatActivity {
    TextView delayText, validText, averageText, resText, scoreText, tmp1, tmp2;
    TextView levelText;
    ImageView image;
    boolean broken;
    ImageView broken_image;
    int delayTime, validTime;
    int max;
    double averageStress;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._res_page);
        //
        delayText = findViewById(R.id.delayText);
        validText = findViewById(R.id.validText);
        averageText = findViewById(R.id.overText);
        resText = findViewById(R.id._res);
        scoreText=findViewById(R.id.score);
        image = findViewById(R.id.ResImage);
        broken_image=findViewById(R.id.broken_image);//这个其实需要根据我们训练的类型来改变，这里以左上肢为例。
        broken_image.setVisibility(View.INVISIBLE);
        tmp1 = findViewById(R.id.loseBloodText);
        tmp2 = findViewById(R.id.loose);
        levelText = findViewById(R.id.Level);
        // 获取计分相关项
        validTime = getIntent().getIntExtra("validTime", 0);
        delayTime = getIntent().getIntExtra("delayTime", 900000);
        averageStress = getIntent().getDoubleExtra("average", 0);
        broken = getIntent().getBooleanExtra("broken", false);
        max = getIntent().getIntExtra("max", 35);
        //
        int score = getPoint();
        // 设置页面
        setUI(score);
    }
    // utils
    private int getPoint()
    {
        if(broken)
            return 20;
        if(averageStress > max)
            return 80;
        return 100;
    }
    @SuppressLint("SetTextI18n")
    private void setUI(int score)
    {
        delayText.setText("延迟" + getStringTime(delayTime));
        validText.setText("有效" + getStringTime(validTime));
        averageText.setText("平均压力" + String.format("%.2f", averageStress)+"mmHg");//保留两位小数
        if(score >= Global.global.bindVeryGood)
            levelText.setText("优秀");
        else if(score >= Global.global.bindGood)
            levelText.setText("良好");
        else if(score >= Global.global.bindNormal)
            levelText.setText("合格");
        else levelText.setText("不及格");
        if(score == 20)
        {
            resText.setText("压力过小，包扎失败");
            int id = this.getResources().getIdentifier("loose", "drawable", this.getPackageName());
            image.setImageResource(id);
        }
        else if(score == 80)
        {
            resText.setText("压力过大，产生不适");
            if(Global.global.currentType == 10)//10表示绷带-上肢，那么11就表示绷带，下肢之类的...
            {
                int id = this.getResources().getIdentifier("bind_up_tite", "drawable", this.getPackageName());
                image.setImageResource(id);
            }
//            else if(Global.global.currentType == 11)
            else
            {
                int id = this.getResources().getIdentifier("bind_down_tite", "drawable", this.getPackageName());
                image.setImageResource(id);
            }
        }
        else
        {
            resText.setText("包扎成功");
            if(Global.global.currentType == 10)//10表示绷带，上肢，那么11就表示绷带，下肢之类的...
            {
                int id = this.getResources().getIdentifier("bind_up_ok", "drawable", this.getPackageName());
                System.out.println("xjh" + this.getPackageName());
                image.setImageResource(id);
            }
//            else if(Global.global.currentType == 11)
            else
            {
                int id = this.getResources().getIdentifier("bind_down_ok", "drawable", this.getPackageName());
                image.setImageResource(id);
            }
        }
        scoreText.setText("得分：" + score);
        tmp1.setVisibility(View.INVISIBLE);
        tmp2.setVisibility(View.INVISIBLE);
    }
    private String getStringTime(int cnt) {
        int min = cnt / 60000;
        int second = cnt % 60000 / 1000;
        return String.format(Locale.CHINA, "%02d:%02d", min, second);
    }
}
