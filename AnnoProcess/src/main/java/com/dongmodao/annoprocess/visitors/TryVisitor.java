package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author: dongmodao
 * @date: 2019/4/20
 * @time: 14:01
 */
public class TryVisitor extends ModifierVisitor {

    @Override
    public Visitable visit(TryStmt n, Object arg) {
        System.out.println("-----change" + n.toString());
        n.getTryBlock().addStatement("int a = 1101;");
        n.getTryBlock().addStatement(0, StaticJavaParser.parseStatement("String addStr = \"gold\";"));
        n.getTryBlock().accept(new MethodNamePrinter(), null);
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(EmptyStmt n, Object arg) {
        return super.visit(n, arg);
    }
}
