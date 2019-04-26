package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.Mapper;
import com.dongmodao.annoprocess.utils.StmtUtils;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.Random;

import static com.dongmodao.annoprocess.utils.RandomUtil.sRandom;

/**
 * @author : tangqihao
 * @date : 2019/4/25
 * add dead code to the block randomly
 */
public class BlockVisitor extends BaseVisitor{

    private Random random = sRandom;
    private static final String TAG = "BlockVisitor";

    @Override
    public Visitable visit(BlockStmt n, Object arg) {
        log_e(TAG, "start BlockStmt");
        NodeList<Statement> nodeList = n.getStatements();
        int count = random.nextInt(1 << 3);
        for (int i = 0; i < count; i++) {
            // can not add blockStmt in this code, it may cause loop!!'
            int index = random.nextInt(nodeList.size() <= 1 ? 1 : nodeList.size() - 1);
            Statement decode = StmtUtils.getDeadCodeStmt();
            nodeList.add(index, decode);
            if (decode.isExpressionStmt() && decode.asExpressionStmt().getExpression().isVariableDeclarationExpr()) {
                String expr = ((Mapper) arg).getUse() + "("
                        + decode.asExpressionStmt().getExpression().asVariableDeclarationExpr().getVariable(0).getNameAsString() + ");";
                nodeList.add(index + 1, StmtUtils.parseStmt(expr));
            }
        }
        return super.visit(n, arg);
    }

    // TODO: 2019/4/25 add out var judge for ifStmt
    // TODO: 2019/4/25 change var in the forStmt condition

}
