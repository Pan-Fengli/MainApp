package com.dalab.dalabapp.Utils;

import com.dalab.dalabapp.constant.Global;

//此工具类用来保存、计算开始的时间，计算有效时间与失血量
public class HoeoIncreaseModel {
    public int percent = 100;
    final int _maxLose = 4000;
    int max;
    int min;
    int volumn;
    float bloodSpeed;
    // 有效时间达到之后，认为血管已经愈合了
    boolean recover = false;
    // 计分相关项
    public float lose = 0;
    public int validTime = 0;
    public int delayTime = 0;
    public int overTime = 0;
    // 用来判断是否是噪声的gap
    int gap = 2000;
    int judgeGap = 0;
    public HoeoIncreaseModel(int ma, int mi, int vol, float bs)
    {
        max = ma;
        min = mi;
        volumn = vol;
        bloodSpeed = bs;
    }
    public void update(int speed, float data)
    {
        if(data < min && validTime == 0)
            delayTime += speed;
        // 主要判断逻辑：先判断是否是噪声，确认不是噪声后且压力大于最小值后，开始算入有效止血时间
        if(judgeGap >= gap)
        {
            if(data < min)
            {
                if(!recover)
                {
                    lose += bloodSpeed*0.001f*speed;//这里的bleedspeed是按照s为单位的，每次update之间的时间间隔是speed，单位是ms，
                    if(lose > _maxLose)
                        lose = _maxLose;
                    percent = Math.max((volumn - (int)lose) * 100 / volumn, 0);
                }
                judgeGap = 0;
            }
            else if(data <= max && data >= min)
            {
                validTime += speed;
                if(validTime >= Global.global.hoeoValidTime*60*1000)
                    recover = true;
            }
            else
            {
                validTime += speed;
                if(validTime >= Global.global.hoeoValidTime*60*1000)
                    recover = true;
                overTime += speed;
            }
        }
        else
        {
            if(data >= min)
            {
                judgeGap += speed;
            }
            else
            {
                judgeGap = 0;
                if(!recover)
                {
                    lose += bloodSpeed*0.001f*speed;
                    if(lose > _maxLose)
                        lose = _maxLose;
                    percent = Math.max((volumn - (int)lose) * 100 / volumn, 0);
                }
            }
        }
    }
}
