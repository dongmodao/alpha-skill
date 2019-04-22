package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.BoolUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : tangqihao
 * @date : 2019/4/22
 */
public class CodeVisitor extends ModifierVisitor {

    private static void log(String msg) {
        System.out.println(msg);
    }

    private static void log_(String msg) {
        log("-----> " + msg);
    }

    private static void log_e(String tag, String msg) {
        log("-----> " + tag + " : " + msg);
    }

    private static void log_t(String msg) {
        log("\n" + msg);
    }

    @Override
    public Visitable visit(MethodDeclaration n, Object arg) {
        BlockStmt blockStmt = n.getBody().get();
        NodeList<Statement> nodeList = blockStmt.getStatements();

        List<Statement> list = new ArrayList<>();
        nodeList.stream().forEach(action -> {
            if (action.isReturnStmt()) {
                list.add(action);
            } else {
                String code = BoolUtils.ifBlockStmtWithVar(action.toString());
//                log_e("code", code);
                for (Statement statement: StaticJavaParser.parseBlock(code).findAll(Statement.class)) {
                    log_e("stat", statement.toString());
                    list.add(statement);
                }

            }
        });
//        blockStmt.getStatements().clear();
//        log_("end");

//        log_e("code list", nodeList.toString());
        blockStmt.getStatements().addAll(list);
        return super.visit(n, arg);
    }
}
