package com.dalab.dalabapp.Utils;

public class RelaxModel {
    public int releaseTime = 0;
    public int min;
    public RelaxModel(int mi)
    {
        min = mi;
    }
    public void update(int speed, float data)
    {
        if(data <= min)
        {
            releaseTime += speed;
        }
    }
}
