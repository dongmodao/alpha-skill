package com.dongmodao.annoprocess.visitors;

import com.dongmodao.annoprocess.utils.Mapper;
import com.dongmodao.annoprocess.utils.NamePool;
import com.dongmodao.annoprocess.utils.StmtUtils;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.Visitable;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 * before obfuscate AST, add some methods to this class
 */
public class PreVisitor extends BaseVisitor {
    private static final String TAG = "PreVisitor";

    @Override
    public Visitable visit(CompilationUnit n, Object arg) {
        log_e(TAG, "start CompilationUnit");

        // class types in first level
        NodeList<TypeDeclaration<?>> types = n.getTypes();
        if (types.size() == 0) {
            return super.visit(n, arg);
        }
        // main type
        TypeDeclaration mainType = types.get(0).asTypeDeclaration();
        log_e(TAG, "add methods/variants");

        // add methods / variants
        MethodDeclaration boolMethod = mainType.addMethod(NamePool.getInstance().getRandomName());
        // judge method
        boolMethod.setPublic(true)
                .setStatic(true)
                .setType(boolean.class)
                .addParameter(new Parameter(PrimitiveType.booleanType(), NamePool.getInstance().getRandomName()))
                .setBody(StmtUtils.getBlockStmt(
                        StmtUtils.getIfStmtStr("(" + boolMethod.getParameter(0).getNameAsString() + " ? 1 : 0) == \" \".length()",
                                "return true;", "return false;"))
                );

        // add methods / variants
        MethodDeclaration useVarMethod = mainType.addMethod(NamePool.getInstance().getRandomName());
        // use variants method
        useVarMethod.setPublic(true)
                .setStatic(true)
                .setType(Void.class)
                .addParameter(new Parameter(new ClassOrInterfaceType("Object"), NamePool.getInstance().getRandomName()))
                .setBody(StmtUtils.getBlockStmt(
                        StmtUtils.getIfStmtStr( useVarMethod.getParameter(0).getNameAsString() + " instanceof String",
                                "System.out.println();"))
                );

        // save bool method to key judge
        ((Mapper) arg).setJudge(boolMethod.getNameAsString());
        // save for avoiding ifStmt change this method
        ((Mapper) arg).setJudgeParamName(boolMethod.getParameter(0).getNameAsString());

        ((Mapper) arg).setUse(useVarMethod.getNameAsString());
        ((Mapper) arg).setUseParamName(useVarMethod.getParameter(0).getNameAsString());
        return super.visit(n, arg);
    }
}
