package com.dongmodao.annoprocess.visitors;

import com.dongmodao.subs.annotation.SubsClass;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/26
 */
public class AfterVisitor extends BaseVisitor {

    private static final String TAG = "AfterVisitor---";

    @Override
    public Visitable visit(MarkerAnnotationExpr n, Object arg) {
        log_e(TAG, "start MarkerAnnotationExpr");
        log_e(TAG, "name = " + n.getNameAsString());
        log_e(TAG, "body = " + n.toString());
        if ("SubsClass".equals(n.getNameAsString())) {
            return null;
        }
        return super.visit(n, arg);
    }


    @Override
    public Node visit(ImportDeclaration n, Object arg) {
        log_e(TAG, "import name = " + n.getNameAsString());
        log_e(TAG, "subs name = " + SubsClass.class.getPackage().getName());
        if ("com.dongmodao.subs.annotation.SubsClass".equals(n.getNameAsString())) {
            return null;
        }
        return super.visit(n, arg);
    }
}
