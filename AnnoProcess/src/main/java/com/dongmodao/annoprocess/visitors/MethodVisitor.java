package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.Mapper;
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

        IfVisitor ifVisitor = new IfVisitor();
        ForVisitor forVisitor = new ForVisitor();
        SwitchVisitor switchVisitor = new SwitchVisitor();
        TryCatchVisitor tryCatchVisitor = new TryCatchVisitor();
        IfNullVisitor ifNullVisitor = new IfNullVisitor();
        BlockVisitor blockVisitor = new BlockVisitor();

        // avoid some visit in use/bool method
        if (!n.getNameAsString().contains(((Mapper) arg).getJudge())) {
            ifVisitor.visit(n, arg);
        }

        forVisitor.visit(n, arg);
//        switchVisitor.visit(n, arg);
//        tryCatchVisitor.visit(n, arg);
//        ifNullVisitor.visit(n, arg);

        // avoid some visit in use/bool method
        if (!n.getNameAsString().contains(((Mapper) arg).getUse())) {
            blockVisitor.visit(n, arg);
        }



        return super.visit(n, arg);
    }
}
