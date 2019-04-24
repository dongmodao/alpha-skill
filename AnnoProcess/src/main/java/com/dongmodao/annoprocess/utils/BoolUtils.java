package com.dongmodao.annoprocess.utils;

import java.util.Random;

/**
 * @author : tangqihao
 * @date : 2019/4/22
 */
public class BoolUtils {
    static Random random = new Random(System.currentTimeMillis());

    private static String getBitStatement() {
        StringBuilder builder = new StringBuilder();
        int limit = random.nextInt(20) + 1;
        int limit1 = random.nextInt(limit) + 5;

        int a = random.nextInt((Integer.MAX_VALUE - 1) >> limit1) << limit1;;
        int b = random.nextInt(limit1) + 5;
        int limit2 = random.nextInt(limit) + 5;

        int c = random.nextInt((Integer.MAX_VALUE - 1) >> limit2) << limit2;
        int d = random.nextInt(limit2) + 5;

        if (a >> b > c >> d) {
            builder.append(a + " << " + b + " > " + c + " >> " + d);
        } else {
            builder.append(a + " << " + b + " <= " + c + " >> " + d);
        }

        return builder.toString();
    }

    public static String ifStatement(String then) {
        return "if(" + getBitStatement() + "){" + then + "}";
    }

    /**
     *     int name48 = 1885339648;
     *     int name49 = 19;
     *     int name50 = 1849688064;
     *     int name51 = 7;
     *     if(name48 >> name49 <= name50 >> int name51 = 7)
     */
    private static String getBitStatementWithVar() {
        StringBuilder builder = new StringBuilder();

        int limit = random.nextInt(28) + 1;
        int limit1 = random.nextInt(limit) + 1;

        int a = random.nextInt((Integer.MAX_VALUE - 1) >> limit1) << limit1;
        int b = random.nextInt(limit1);
        int limit2 = random.nextInt(limit) + 1;

        int c = random.nextInt((Integer.MAX_VALUE - 1) >> limit2) << limit2;
        int d = random.nextInt(limit2);

        String n1, n2, n3, n4;
        n1 = NamePool.getInstance().getRandomName();
        n2 = NamePool.getInstance().getRandomName();
        n3 = NamePool.getInstance().getRandomName();
        n4 = NamePool.getInstance().getRandomName();

        builder.append("int ").append(n1).append(" = ").append(a).append(";\n");
        builder.append("int ").append(n2).append(" = ").append(b).append(";\n");
        builder.append("int ").append(n3).append(" = ").append(c).append(";\n");
        builder.append("int ").append(n4).append(" = ").append(d).append(";\n");

        builder.append("if(");
        if (a >> b > c >> d) {
            builder.append(n1).append(" >> ").append(n2).append(" > ").append(n3).append(" >> ").append(n4);
        } else {
            builder.append(n1).append(" >> ").append(n2).append(" <= ").append(n3).append(" >> ").append(n4);
        }
        builder.append(")");
        return builder.toString();
    }

    public static String ifBlockStmtWithVar(String... args) {
        if (args.length == 1) {
            return "{" + getBitStatementWithVar() + "{" + args[0] + "}}";
        } else {
            return "{" + getBitStatementWithVar() + "{" + args[0] + "}else{" + args[1] + "}}";
        }
    }

    private String getStrTrueStmt() {
        int l = random.nextInt(20) + 3;
        int r = random.nextInt(20) + 3;

        if (l > r) {
            return emptyStr(l) + ".length() > " + emptyStr(r);
        } else {
            return emptyStr(l) + ".length() <= " + emptyStr(r);
        }

    }

    private String emptyStr(int n) {
        StringBuilder rst = new StringBuilder();
        for (int i = 0; i < n; i++) {
            rst.append(" ");
        }
        return rst.toString();
    }
}
