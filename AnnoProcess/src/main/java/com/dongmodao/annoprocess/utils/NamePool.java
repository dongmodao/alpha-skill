package com.dongmodao.annoprocess.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : tangqihao
 * @date : 2019/4/22
 */
public class NamePool {

    private int mCurIndex = 0;

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
        String name = "name" + mCurIndex;
        mCurIndex++;
        return name;
    }


}
