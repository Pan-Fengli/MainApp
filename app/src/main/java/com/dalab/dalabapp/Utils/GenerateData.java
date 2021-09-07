package com.dalab.dalabapp.Utils;

import java.util.Random;

public class GenerateData {
    int declineValue = 0;
    int max;
    int min;
    int decline_speed = 1;
    public GenerateData(int ma, int mi)
    {
        max = ma;
        min = mi;
    }
    public int generate(Boolean release) {
        Random random = new Random();
        int value, range;
        if (!release)//还没有松开
        {
            range = max - min;
            value = min + random.nextInt(range);
            return value;
        }
        declineValue += decline_speed;
        value = max - declineValue;
        if (value <= 0) {
            value = 0;
        }
        return value;
    }
}
