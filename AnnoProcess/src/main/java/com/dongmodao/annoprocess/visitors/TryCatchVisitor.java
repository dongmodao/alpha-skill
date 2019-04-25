package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class TryCatchVisitor extends BaseVisitor {
    private static final String TAG = "TryCatchVisitor";

    @Override
    public Visitable visit(TryStmt n, Object arg) {
        log_e(TAG, "start TryStmt");
        return super.visit(n, arg);
    }
}
