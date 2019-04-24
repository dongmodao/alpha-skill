package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.BoolUtils;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
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
                for (Statement statement: StaticJavaParser.parseBlock(code).getStatements()) {
                    list.add(statement);
                }

            }
        });
        if (list.size() != 0) {
            blockStmt.getStatements().clear();
            if (!n.getType().isVoidType() && !list.get(list.size() - 1).isReturnStmt()) {
                list.add(getReturnStmt(n.getType()));
            }
            blockStmt.getStatements().addAll(list);
        }
        return super.visit(n, arg);
    }

    private Statement getReturnStmt(Type type) {
        // 引用类型
        if (!type.isPrimitiveType()) {
            return StaticJavaParser.parseStatement("return null;");
        } else  /* bool 类型 */ if ("boolean".equals(type.toString())) {
            return StaticJavaParser.parseStatement("return false;");
        } else { /* 基本类型 */
            return StaticJavaParser.parseStatement("return 0;");
        }
    }
}
