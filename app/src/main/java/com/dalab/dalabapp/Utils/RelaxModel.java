package com.dalab.dalabapp.Utils;

public class RelaxModel {
    public int releaseTime = 0;
    public int min;
    public int max;
    public RelaxModel(int mi, int ma)
    {
        min = mi;
        max = ma;
    }
    public void update(int speed, float data)
    {
        if(data <= max && data > min)
        {
            releaseTime += speed;
        }
    }
}
