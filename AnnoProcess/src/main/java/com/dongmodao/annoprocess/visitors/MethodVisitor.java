package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class MethodVisitor extends BaseVisitor {

    private static final String TAG = "MethodVisitor";


    @Override
    public Visitable visit(MethodDeclaration n, Object arg) {
        log_e(TAG, "start MethodDeclaration");
        return super.visit(n, arg);
    }
}
