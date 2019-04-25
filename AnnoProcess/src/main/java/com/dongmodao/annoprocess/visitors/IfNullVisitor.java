package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class IfNullVisitor extends BaseVisitor {

    private static final String TAG = "IfNullVisitor";

    @Override
    public Visitable visit(NullLiteralExpr n, Object arg) {
        log_e(TAG, "start NullLiteralExpr");
        return super.visit(n, arg);
    }
}
