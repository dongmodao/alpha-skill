package com.dongmodao.annoprocess.utils;

import java.util.HashMap;

/**
 * @author : tangqihao
 * @date : 2019/4/25
 * save some value for passing, not single instance(files)
 */
public class Mapper {

    private static final String KEY_JUDGE = "key_judge";
    private static final String KEY_JUDGE_PARAM_NAME = "key_judge_param_name";

    private HashMap<String, String> map = new HashMap<>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public void setJudge(String name) {
        put(KEY_JUDGE, name);
    }

    public String getJudge() {
        return get(KEY_JUDGE);
    }

    public void setJudgeParamName(String name) {
        put(KEY_JUDGE_PARAM_NAME, name);
    }

    public String getJudgeParamName() {
        return get(KEY_JUDGE_PARAM_NAME);
    }
}
