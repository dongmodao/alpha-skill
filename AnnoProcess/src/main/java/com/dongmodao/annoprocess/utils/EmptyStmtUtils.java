package com.dongmodao.annoprocess.utils;

import com.github.javaparser.ast.stmt.BlockStmt;

import java.util.Random;

import static com.dongmodao.annoprocess.utils.StringUtils.emptyStr;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class EmptyStmtUtils {

    static Random mRandom = new Random();

    public static String getEmptyString() {

        return emptyStr(mRandom.nextInt(20) + 3) + ".length();";
    }


}
