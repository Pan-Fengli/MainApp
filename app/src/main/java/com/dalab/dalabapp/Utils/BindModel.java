package com.dalab.dalabapp.Utils;

public class BindModel {
    int max;
    int min;
    double allStress;
    int count;
    boolean first;
    public int delayTime;
    public int validTime;
    public BindModel(int ma, int mi)
    {
        max = ma;
        min = mi;
        first = true;
        delayTime = 0;
        allStress = 0;
        count = 0;
        validTime = 0;
    }
    // 返回0，1分别代表打断，继续
    public boolean update(float data, float speed)
    {
        // 判断是否应该打断
        if(first)
        {
            if(data < min)
                delayTime += speed;
            else
            {
                delayTime = 0;
                validTime += speed;
                first = false;
            }
            if(delayTime > 10000)//10s
            {
                return false;
            }
        }
        else
        {
            if(data < min)
            {
                delayTime += speed;
            }
            else
            {
                delayTime = 0;
                validTime += speed;
            }
            if(delayTime > 2000)
                return false;
        }
        // 不用打断的情况，给总压力值做贡献
//        allStress += data;
        allStress += data*speed;
        count += speed;
        return true;
    }
    public double getAverage()
    {
        if(count == 0)
            return 0;
        return allStress / count;
    }
    public int getValidTime()
    {
        return validTime;
    }
    public int getTime()
    {
        return count;
    }
}
