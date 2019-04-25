package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class ForVisitor extends BaseVisitor {

    private static final String TAG = "ForVisitor";

    @Override
    public Visitable visit(ForStmt n, Object arg) {
        log_e(TAG, "start ForStmt");

        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(ForEachStmt n, Object arg) {
        log_e(TAG, "start ForEachStmt");
        // change var name in the condition

        return super.visit(n, arg);
    }
}
