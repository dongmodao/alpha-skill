package com.dongmodao.annoprocess.actions;

import com.dongmodao.annoprocess.utils.Mapper;
import com.dongmodao.annoprocess.visitors.BlockVisitor;
import com.dongmodao.annoprocess.visitors.ForVisitor;
import com.dongmodao.annoprocess.visitors.IfNullVisitor;
import com.dongmodao.annoprocess.visitors.IfVisitor;
import com.dongmodao.annoprocess.visitors.MethodVisitor;
import com.dongmodao.annoprocess.visitors.PreVisitor;
import com.dongmodao.annoprocess.visitors.SwitchVisitor;
import com.dongmodao.annoprocess.visitors.TryCatchVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
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

            Mapper mapper = new Mapper();

            /*
            preVisitor -> if -> for -> switch -> try -> if(null) -> method block -> all block
             */
            PreVisitor preVisitor = new PreVisitor();
            IfVisitor ifVisitor = new IfVisitor();
            ForVisitor forVisitor = new ForVisitor();
            SwitchVisitor switchVisitor = new SwitchVisitor();
            TryCatchVisitor tryCatchVisitor = new TryCatchVisitor();
            IfNullVisitor ifNullVisitor = new IfNullVisitor();
            MethodVisitor methodVisitor = new MethodVisitor();
            BlockVisitor blockVisitor = new BlockVisitor();

            compilationUnit.accept(preVisitor, mapper);
            compilationUnit.accept(ifVisitor, mapper);
            compilationUnit.accept(forVisitor, mapper);
            compilationUnit.accept(switchVisitor, mapper);
            compilationUnit.accept(tryCatchVisitor, mapper);
            compilationUnit.accept(ifNullVisitor, mapper);
            compilationUnit.accept(methodVisitor, mapper);
            compilationUnit.accept(blockVisitor, mapper);

            compilationUnit.getStorage().get().save();
            log_t("flag 2. save : " + path + " to \n\t: " + compilationUnit.getStorage().get().getPath().toString() + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
