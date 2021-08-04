package com.dalab.dalabapp.Utils;

import java.util.Random;

public class GenerateData {
    int declineValue = 0;
    public int generate(int max, int min, int decline_speed, Boolean release) {
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
