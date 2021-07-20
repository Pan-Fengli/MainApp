package com.dalab.dalabapp.Animation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dalab.dalabapp.R;
import com.dalab.dalabapp.TimerActivity;

public class Animation extends AppCompatActivity {
    Button alphaButton;
    TextView hello;
    TextView percentText;
    ImageView imageView;
    int percent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        alphaButton=findViewById(R.id.alpha_button);
//        timerButton=findViewById(R.id.timer);
        hello=findViewById(R.id.hello);
        imageView=findViewById(R.id.imageView);
        percentText=findViewById(R.id.percent);
        percent=Integer.valueOf(percentText.getText().toString());

        imageView.setImageResource(R.drawable.h0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setScaleX(0.5f);
        imageView.setScaleY(0.5f);
        changeImage(100);//

        heartBeat(1000);
    }

    public void Jump(View view)//点击跳转页面
    {
        Intent intent =new Intent();
        intent.setClass(Animation.this, TimerActivity.class);
        startActivity(intent);
    }

    public void startAnimationSet(View view)
    {
        imageView.setImageResource(R.drawable.anim_list);
        AnimationDrawable animationDrawable=(AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        //
        android.view.animation.Animation blur=new AlphaAnimation(1.0f,0.2f);
        blur.setDuration(2000);//2s
        blur.setFillAfter(true);
        blur.setFillBefore(true);
        blur.setRepeatCount(-1);//-1就是无穷次
        blur.setRepeatMode(android.view.animation.Animation.REVERSE);
        //只有image才能够调整alpha?并不！！
        alphaButton.startAnimation(blur);
        android.view.animation.Animation scale= AnimationUtils.loadAnimation(this,R.anim.scale);
        hello.startAnimation(scale);
        scale.setDuration(1000);//1s
        scale.setFillAfter(true);
        scale.setFillBefore(true);
    }
    private void heartBeat(int interval)
    {
        android.view.animation.Animation blur=new AlphaAnimation(1.0f,0.85f);
        blur.setDuration(interval);//1s
        blur.setFillAfter(true);
        blur.setFillBefore(true);
        blur.setRepeatCount(-1);//-1就是无穷次
        blur.setRepeatMode(android.view.animation.Animation.REVERSE);

        //跟着一起缩放
//        Animation scale= AnimationUtils.loadAnimation(this,R.anim.scale);
        android.view.animation.Animation scale=new ScaleAnimation(1.0f,0.85f,1.0f,0.85f, android.view.animation.Animation.RELATIVE_TO_SELF,0.5f, android.view.animation.Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(interval);
        scale.setFillAfter(true);
        scale.setFillBefore(true);
        scale.setRepeatCount(-1);//-1就是无穷次
        scale.setRepeatMode(android.view.animation.Animation.REVERSE);

        AnimationSet animationSet=new AnimationSet(true);
        animationSet.addAnimation(blur);
        animationSet.addAnimation(scale);
        imageView.startAnimation(animationSet);
    }
    public void plus(View view)//点击 + 按钮
    {
        if(percent>=100)//不能再变大了
        {
            percentText.setText("100");
            return;
        }
        int oldPercent=percent;
        percent+=1;
        percentText.setText(String.valueOf(percent));
        changeImage(oldPercent);
    }
    public void minus(View view)//点击 - 按钮
    {
        if(percent<=0)//不能再变小了
        {
            percentText.setText("0");
            return;
        }
        int oldPercent=percent;
        percent-=1;
        percentText.setText(String.valueOf(percent));
        changeImage(oldPercent);
    }
    private void changeImage(int oldPercent)
    {

        int number=(100-percent)*45/100;//得到图片的编号
        //甚至可以检查number有无变化来决定是否重载资源
        int oldNumber=(100-oldPercent)*45/100;
        if(oldNumber==number)
        {
            return;
        }
        String name="h"+String.valueOf(number);
        //根据文件名字来获得id号
        int id=this.getResources().getIdentifier(name,"drawable",this.getPackageName());
        imageView.setImageResource(id);

        //心跳频率也会变化
        int interval=1000-number*18;
        heartBeat(interval);
    }
    public void FadeAndResume(View view)
    {
        final android.view.animation.Animation fade=new AlphaAnimation(1.0f,0.2f);
        fade.setDuration(2000);//2s
        fade.setFillBefore(true);
//        fade.setRepeatCount(3);

        alphaButton.startAnimation(fade);
        final android.view.animation.Animation resume=new AlphaAnimation(0.2f,1.0f);
        resume.setDuration(2000);
//        resume.setRepeatCount(3);
        fade.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {

            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                alphaButton.startAnimation(resume);//在上一个结束了之后开始调用reusme这个动画
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {
//                alphaButton.startAnimation(resume);//如果是重复？
            }
        });
        resume.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {

            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                alphaButton.startAnimation(fade);//在上一个结束了之后开始调用reusme这个动画
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
    }

    public void AnimationSetToLoop(View view)
    {

        AnimationSet animationSet=(AnimationSet) AnimationUtils.loadAnimation(this,R.anim.set);
        android.view.animation.Animation animation=AnimationUtils.loadAnimation(this,R.anim.set);
        animation.setRepeatMode(android.view.animation.Animation.REVERSE);
        animation.setRepeatCount(3);
        alphaButton.startAnimation(animation);
    }
}
