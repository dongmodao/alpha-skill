package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.BoolUtils;
import com.dongmodao.annoprocess.utils.Mapper;
import com.dongmodao.annoprocess.utils.StmtUtils;
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
        log_e(TAG, "start IfStmt");
        Mapper mapper = ((Mapper) arg);
        // add true condition after origin condition, check condition for avoiding looping in the class judge method
        if (!n.getCondition().toString().contains(mapper.getJudgeParamName())) {
            n.setCondition(StaticJavaParser.parseExpression(mapper.getJudge() + "((" + n.getCondition().toString() + ") && " + BoolUtils.getTrueStmt() + ")"));
        }
        // add else branch(if not exists) with dead codes
        if (!n.hasElseBranch()) {
            n.setElseStmt(StmtUtils.getDeadCodeBlockStmt());
        }
        return super.visit(n, arg);
    }
}
