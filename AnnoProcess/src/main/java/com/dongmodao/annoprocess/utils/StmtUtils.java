package com.dongmodao.annoprocess.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.Random;

/**
 * @author : tangqihao
 * @date : 2019/4/25
 */
public class StmtUtils {

    private static Random random = new Random();
    // get stmt str

    public static String getIfStmtStr(String condition, String then, String...elseStmtStr) {
        String rst = "if(" + condition +"){" + then + "}";
        if (elseStmtStr.length > 0) {
            rst = rst + "else{" + elseStmtStr[0] + "}";
        }
        return rst;
    }


    // get stmt

    public static BlockStmt getBlockStmt(String body) {
        return StaticJavaParser.parseBlock("{" + body + "}");
    }

    public static BlockStmt getDeadCodeBlockStmt() {
        BlockStmt blockStmt = new BlockStmt();
        int n = random.nextInt(1 << 4);
        for (int i = 0; i < n; i++) {
            blockStmt.addStatement(DeadCodeUtils.getStmtStr());
        }
        return blockStmt;
    }

    public static IfStmt getIfStmt(String condition, String then, String...elseStmtStr) {
        IfStmt ifStmt = new IfStmt();
        ifStmt.setCondition(StaticJavaParser.parseExpression(condition))
                .setThenStmt(StaticJavaParser.parseStatement(then));

        if (elseStmtStr.length > 0) {
            ifStmt.setElseStmt(StaticJavaParser.parseStatement(elseStmtStr[0]));
        }
        return ifStmt;
    }

    public static IfStmt getIfStmt(String condition, BlockStmt then, String...elseStmtStr) {
        IfStmt ifStmt = new IfStmt();
        ifStmt.setCondition(StaticJavaParser.parseExpression(condition))
                .setThenStmt(then);

        if (elseStmtStr.length > 0) {
            ifStmt.setElseStmt(StaticJavaParser.parseStatement(elseStmtStr[0]));
        }
        return ifStmt;
    }

    public static IfStmt getIfStmtDeadCodeBlockStmt() {
        return getIfStmt("!(" + BoolUtils.getTrueStmt() + ")",getDeadCodeBlockStmt());
    }

    public static Statement getDeadCodeStmt() {
        return StaticJavaParser.parseStatement(DeadCodeUtils.getStmtStr());
    }
}
