package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class SwitchVisitor extends BaseVisitor {

    private static final String TAG = "SwitchVisitor";

    @Override
    public Visitable visit(SwitchEntry n, Object arg) {
        log_e(TAG, "start SwitchEntry");
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(SwitchStmt n, Object arg) {
        log_e(TAG, "start SwitchStmt");
        return super.visit(n, arg);
    }
}
