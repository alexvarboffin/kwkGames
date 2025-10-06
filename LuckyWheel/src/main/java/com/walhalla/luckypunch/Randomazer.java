package com.walhalla.luckypunch;

import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Randomazer {

    private static final Random RANDOM = new Random();

    static Randomazer instance;

    public static Randomazer newInstance() {
        if (instance == null) {
            instance = new Randomazer();
        }
        return instance;
    }

    public int randInt(boolean unique, int min, int max) {
        if (max >= min) {
//            if (unique) {
//                int tmp = RANDOM.nextInt((max - min) + 1) + min;
//            }
            return RANDOM.nextInt((max - min) + 1) + min;
        }
        return 1;
    }


    public List<Integer> pickUniqRandom(int uniq_count, int min, int max) {

        List<Integer> aaa = new ArrayList<>();

        final Set<Integer> picked = new HashSet<>();
        int j = (max - min) + 1; //Количество, которое можем получить
        if (uniq_count > j) {
            //DLog.d("Будут повторы...");
            while (aaa.size() < uniq_count) {
                aaa.add(RANDOM.nextInt((max - min) + 1) + min);
            }
            return aaa;
        }
        else {
            //DLog.d("Без повторов...");
            while (picked.size() < uniq_count) {
                picked.add(RANDOM.nextInt((max - min) + 1) + min);
            }
            aaa.addAll(picked);
        }
        return aaa;
    }
}
