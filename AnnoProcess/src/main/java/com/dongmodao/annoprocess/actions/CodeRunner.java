package com.dongmodao.annoprocess.actions;

import com.dongmodao.annoprocess.visitors.CodeVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : tangqihao
 * @date : 2019/4/22
 */
public class CodeRunner {

    public void run(String path) {

        File sourceFile = new File(path);
        JavaParser javaParser = new JavaParser();

        try {
            ParseResult<CompilationUnit> compilationUnits = javaParser.parse(sourceFile);
            Optional<CompilationUnit> unitOptional = compilationUnits.getResult();
            CompilationUnit compilationUnit = unitOptional.get();
            log_t("flag 0. start: path = " + path + "finish");
            log_t("flag 1. run: start visit nodes and change code");

//            List<MethodDeclaration> voidMethods = new ArrayList<>();
//            compilationUnit.accept(new VoidVisitorAdapter<List<MethodDeclaration>>() {
//                @Override
//                public void visit(MethodDeclaration n, List<MethodDeclaration> arg) {
//                    super.visit(n, arg);
//                    arg.add(n);
//                }
//            }, voidMethods);
//
//            List<String> methods = getClassUsableMethods(voidMethods);

            // TODO: 2019/4/22 change code
            // with method
            CodeVisitor codeVisitor = new CodeVisitor();
            codeVisitor.visit(compilationUnit, null);

            compilationUnit.getStorage().get().save();
            log_t("flag 2. save : " + path + " to \n\t: " + compilationUnit.getStorage().get().getPath().toString() + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<String> getClassUsableMethods(List<MethodDeclaration> voidMethods) {
        List<String> rst = new ArrayList<>();
        for (MethodDeclaration method: voidMethods) {
            log_e("method", method.getNameAsString());
            log_e("run method = ", method.getParentNode().get()+ "");
//            if ("run".equals(method.getNameAsString())) {
//                log_e("run method = ", method.getParentNode().get().toString() + "");
//            }
        }
        return rst;
    }


    private static void log(String msg) {
        System.out.println(msg);
    }

    private static void log_e(String tag, String msg) {
        log("-----> " + tag + " : " + msg);
    }

    private static void log_t(String msg) {
        log("\n" + msg);
    }
}
