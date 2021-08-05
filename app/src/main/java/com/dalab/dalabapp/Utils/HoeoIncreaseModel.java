package com.dalab.dalabapp.Utils;
//此工具类用来保存、计算开始的时间，计算有效时间与失血量
public class HoeoIncreaseModel {
    public int validTime = 0;
    public int percent = 100;
    int max;
    int min;
    int volumn;
    float bloodSpeed;
    // lose
    public float lose = 0;
    // 用来判断是否是噪声的gap
    int gap = 3000;
    public HoeoIncreaseModel(int ma, int mi, int vol, float bs)
    {
        max = ma;
        min = mi;
        volumn = vol;
        bloodSpeed = bs;
    }
    public void update(int speed, float data)
    {
        if(data < min)
        {
            lose += speed;
            percent = (volumn - (int)lose) * 100 / volumn;
        }
        else
        {
            validTime += speed;
        }
    }
}
