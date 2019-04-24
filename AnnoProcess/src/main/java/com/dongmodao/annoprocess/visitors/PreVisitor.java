package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class PreVisitor extends BaseVisitor {

    @Override
    public Visitable visit(CompilationUnit n, Object arg) {

        return super.visit(n, arg);
    }
}
