package com.dongmodao.annoprocess.utils;

import static com.dongmodao.annoprocess.utils.RandomUtil.sRandom;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class StringUtils {

    private static boolean USE_CHAR = true;

    public static String emptyStr(int n) {
        StringBuilder rst = new StringBuilder();
        rst.append("\"");
        for (int i = 0; i < n; i++) {
            if (USE_CHAR) {
                rst.append((char)((sRandom.nextBoolean() ? (int)'a' : (int)'A') + sRandom.nextInt(26)));
            } else {
                rst.append(" ");
            }

        }
        rst.append("\"");
        return rst.toString();
    }

}
