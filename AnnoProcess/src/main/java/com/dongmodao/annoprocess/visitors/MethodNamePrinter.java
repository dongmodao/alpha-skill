package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author : tangqihao
 * @date : 2019/4/19
 */
public class MethodNamePrinter extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);
        System.out.println("---------" + "name = " + n.getNameAsString() + "; body = " + n.getBody());
    }
}
