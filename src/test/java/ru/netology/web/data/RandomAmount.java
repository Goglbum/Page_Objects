package ru.netology.web.data;

import java.util.Random;

public class RandomAmount {
    public static String randomAmount(int min, int max) {
        Random random = new Random();
        int dif = max - min;
        int randomAmount = random.nextInt(dif + 1);
        randomAmount += min;
        return String.valueOf(randomAmount);
    }

    public static String randomAmount() {
        return randomAmount(0, 10000);
    }

}
