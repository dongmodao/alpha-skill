package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.BoolUtils;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class IfVisitor extends BaseVisitor {

    private static final String TAG = "IfVisitor";

    @Override
    public Visitable visit(IfStmt n, Object arg) {
        log_e(TAG, "(" + n.getCondition().toString() + ") && " + BoolUtils.getStrTrueStmt());
        n.setCondition(StaticJavaParser.parseExpression("(" + n.getCondition().toString() + ") && " + BoolUtils.getStrTrueStmt()));
        return super.visit(n, arg);
    }
}
