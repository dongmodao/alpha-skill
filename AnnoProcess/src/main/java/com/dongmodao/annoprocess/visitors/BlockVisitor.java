package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.StmtUtils;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.Random;

/**
 * @author : tangqihao
 * @date : 2019/4/25
 * add dead code to the block randomly
 */
public class BlockVisitor extends BaseVisitor{

    private Random random = new Random();
    private static final String TAG = "BlockVisitor";

    @Override
    public Visitable visit(BlockStmt n, Object arg) {
        log_e(TAG, "start BlockStmt");
        NodeList<Statement> nodeList = n.getStatements();
        int count = random.nextInt(20);
        for (int i = 0; i < count; i++) {
            // can not add blockStmt in this code, it may cause loop!!
            nodeList.add(random.nextInt(nodeList.size() <= 1 ? 1 : nodeList.size() - 1), StmtUtils.getDeadCodeStmt());
        }
        return super.visit(n, arg);
    }

    // TODO: 2019/4/25 add out var judge for ifStmt
    // TODO: 2019/4/25 change var in the forStmt condition

}
