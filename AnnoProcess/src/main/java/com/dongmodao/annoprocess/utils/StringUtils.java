package com.dongmodao.annoprocess.utils;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class StringUtils {

    public static String emptyStr(int n) {
        StringBuilder rst = new StringBuilder();
        rst.append("\"");
        for (int i = 0; i < n; i++) {
            rst.append(" ");
        }
        rst.append("\"");
        return rst.toString();
    }

}
