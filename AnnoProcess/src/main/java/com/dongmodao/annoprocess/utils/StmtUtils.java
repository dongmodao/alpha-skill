package com.dongmodao.annoprocess.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.Random;

import static com.dongmodao.annoprocess.utils.RandomUtil.sRandom;

/**
 * @author : tangqihao
 * @date : 2019/4/25
 */
public class StmtUtils {

    private static Random random = sRandom;

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

    /**
     * add two statement each time in for loop. variant declaration and call method to use this variant.
     * @param arg Mapper
     * @return blockStmt
     */
    public static BlockStmt getDeadCodeBlockStmt(Object arg) {
        BlockStmt blockStmt = new BlockStmt();
        int n = random.nextInt(1 << 3) + 2;
        for (int i = 0; i < n; i++) {
            Statement deadCode = StaticJavaParser.parseStatement(DeadCodeUtils.getStmtStr());
            blockStmt.addStatement(deadCode);
            // if statement is expression and a variable declaration expr, get variant name and call a method to use it as param
            if (deadCode.isExpressionStmt() && deadCode.asExpressionStmt().getExpression().isVariableDeclarationExpr()) {
                System.out.println("StmtUtils -------- var name = " +  deadCode.asExpressionStmt().getExpression().asVariableDeclarationExpr().getVariable(0).getNameAsString());
                blockStmt.addStatement(((Mapper) arg).getUse() + "(" + deadCode.asExpressionStmt().getExpression().asVariableDeclarationExpr().getVariable(0).getNameAsString() + ");");
            }
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

    public static IfStmt getIfStmtDeadCodeBlockStmt(Object arg) {
        return getIfStmt("!(" + BoolUtils.getTrueStmt() + ")",getDeadCodeBlockStmt(arg));
    }

    public static Statement getDeadCodeStmt() {
        return StaticJavaParser.parseStatement(DeadCodeUtils.getStmtStr());
    }

    public static Statement parseStmt(String statement) {
        return StaticJavaParser.parseStatement(statement);
    }

    public static <T extends Expression> Expression parseExpr(String expression) {
        return StaticJavaParser.parseExpression(expression);
    }
}
