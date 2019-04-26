package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.BoolUtils;
import com.dongmodao.annoprocess.utils.Mapper;
import com.dongmodao.annoprocess.utils.StmtUtils;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.Visitable;

import static com.dongmodao.annoprocess.utils.RandomUtil.sRandom;

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

        /* add true condition after origin condition, check condition for avoiding looping in the class judge method
         * a == b, not contain "()"
        */
        String newCondition = sRandom.nextBoolean() ? "(" + n.getCondition().toString() + ") && " + BoolUtils.getTrueStmt() :
                BoolUtils.getTrueStmt() + " && (" + n.getCondition().toString() + ")" ;

        if (!n.getCondition().toString().contains(mapper.getJudgeParamName())) {
            if (sRandom.nextBoolean()) {
                n.setCondition(StaticJavaParser.parseExpression(mapper.getJudge() + "(" + newCondition + ")"));
            } else {
                n.setCondition(StaticJavaParser.parseExpression(newCondition));
            }
        } else {
            n.setCondition(StaticJavaParser.parseExpression(newCondition));
        }


        // add else branch(if not exists) with dead codes; don't add dead code in "use method"
        if (!n.hasElseBranch() && !n.toString().contains(((Mapper) arg).getUseParamName())) {
            n.setElseStmt(StmtUtils.getDeadCodeBlockStmt(arg));
        }
        return super.visit(n, arg);
    }
}
