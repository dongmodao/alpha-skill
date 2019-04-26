package com.dongmodao.annoprocess.utils;

import java.util.Random;

import static com.dongmodao.annoprocess.utils.RandomUtil.sRandom;
import static com.dongmodao.annoprocess.utils.StringUtils.emptyStr;

/**
 * @author : tangqihao
 * @date : 2019/4/25
 */
public class DeadCodeUtils {

    private static Random random = sRandom;

    public static String getStmtStr() {

        // char, boolean, byte, short, int, long, float, double, String
        switch (random.nextInt() % 9) {
            case 0:
                return "char " + NamePool.getInstance().getRandomName() + " = '" + (char)(random.nextInt(26) + 65) + "';";
            case 1:
                return "boolean " + NamePool.getInstance().getRandomName() + " = " + random.nextBoolean() + ";";
            case 2:
                return "byte " + NamePool.getInstance().getRandomName() + " = " + random.nextInt(Byte.MAX_VALUE / 2) + ";";
            case 3:
                return "short " + NamePool.getInstance().getRandomName() + " = " + random.nextInt(Short.MAX_VALUE / 2) + ";";
            case 4:
                return "int " + NamePool.getInstance().getRandomName() + " = " + random.nextInt(Integer.MAX_VALUE / 2) + ";";
            case 5:
                return "long " + NamePool.getInstance().getRandomName() + " = " + random.nextLong() + "L;";
            case 6:
                return "float " + NamePool.getInstance().getRandomName() + " = " + random.nextFloat() + "F;";
            case 7:
                return "double " + NamePool.getInstance().getRandomName() + " = " + random.nextDouble() + "D;";
            case 8:
                return "String " + NamePool.getInstance().getRandomName() + " = " + emptyStr(random.nextInt(20)) + ";";
            default:
                return "String " + NamePool.getInstance().getRandomName() + " = " + emptyStr(random.nextInt(20)) + ";";

        }

    }

}
