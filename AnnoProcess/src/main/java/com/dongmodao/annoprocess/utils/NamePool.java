package com.dongmodao.annoprocess.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.dongmodao.annoprocess.utils.RandomUtil.sRandom;

/**
 * @author : tangqihao
 * @date : 2019/4/22
 */
public class NamePool {

    private static Random random = sRandom;

    private String mPrefix = getPrefix();

    private int mCurIndex = random.nextInt(1 << 10) + 1;

    private List<String> mNameList = new ArrayList<>();

    private static NamePool mInstance;

    private NamePool() {}

    public static NamePool getInstance() {
        synchronized (NamePool.class) {
            if(mInstance == null) {
                mInstance = new NamePool();
            }
        }
        return mInstance;
    }

    public String getRandomName() {
        String name = getSimpleName();
        while (mNameList.contains(name)) {
            name = getSimpleName();
        }
        mNameList.add(name);
        return name;
    }

    private String getSimpleName() {
        String name = mPrefix + Integer.toBinaryString(mCurIndex);
        mCurIndex++;
        return name;
    }

    /**
     * should add param to fit more times(avoid same names in a block)
     * @return prefix of name
     */
    private static String getPrefix(){
        // start at 5
        int n = random.nextInt(5) + 5;
        StringBuilder rst = new StringBuilder();
        for (int i = 0; i < n; i++){
            rst.append(random.nextBoolean() ? "o" : "O");
        }
        return rst.toString();
    }


}
