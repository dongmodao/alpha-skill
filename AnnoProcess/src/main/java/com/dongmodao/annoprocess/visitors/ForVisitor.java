package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.NamePool;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.List;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class ForVisitor extends BaseVisitor {

    private static final String TAG = "ForVisitor";

    @Override
    public Visitable visit(ForStmt n, Object arg) {
        log_e(TAG, "start ForStmt");
        // change variant declaration in forStmt initialization, like: int i = 0;
        for (Expression expression : n.getInitialization()) {
            if (expression.isVariableDeclarationExpr()) {
                for (VariableDeclarator variableDeclarator : expression.asVariableDeclarationExpr().getVariables()) {
                    String oldName = variableDeclarator.getNameAsString();
                    String newName = NamePool.getInstance().getRandomName();
                    variableDeclarator.setName(newName);
                    changeVarName(n.getBody(), oldName, newName);
                }
            }
        }

        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(ForEachStmt n, Object arg) {
        log_e(TAG, "start ForEachStmt");
        // change var name in the condition
        VariableDeclarator variableDeclarator = n.getVariableDeclarator();
        String oldName = variableDeclarator.getNameAsString();
        String newName = NamePool.getInstance().getRandomName();
        variableDeclarator.setName(newName);
        changeVarName(n, oldName, newName);
        return super.visit(n, arg);
    }

    /**
     * find NameExpr in statement
     * @param stmt stmt
     * @param oldName old name
     * @param newName replace name
     */
    private void changeVarName(Statement stmt, String oldName, String newName) {
        List<NameExpr> names = stmt.findAll(NameExpr.class);
        if (names.size() > 0) {
            log_e(TAG, names.toString());
            for (NameExpr nameExpr: names) {
                if (nameExpr.getNameAsString().equals(oldName)) {
                    nameExpr.setName(newName);
                }
            }
        }
    }
}
