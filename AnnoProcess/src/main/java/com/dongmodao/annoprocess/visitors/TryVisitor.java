package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.NamePool;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

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
        n.getTryBlock().addStatement(0, StaticJavaParser.parseStatement("String " + NamePool.getInstance().getRandomName() + " = \"gold\";"));
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(ExpressionStmt n, Object arg) {
        System.out.println("-----ExpressionStmt: " + n.toString());
        n.remove(n.getChildNodes().get(0));
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(VariableDeclarator n, Object arg) {
        System.out.println("-----VariableDeclarator: " + n.toString());
//        n.remove(n.getChildNodes().get(0));
        return super.visit(n, arg);
    }
}
