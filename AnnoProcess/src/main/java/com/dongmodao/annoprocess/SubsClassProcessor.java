package com.dongmodao.annoprocess;

import com.dongmodao.annoprocess.visitors.MethodNamePrinter;
import com.dongmodao.subs.annotation.ASubsClass;
import com.dongmodao.subs.annotation.SubsClass;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * @author : tangqihao
 * @date : 2019/4/19
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.dongmodao.subs.annotation.SubsClass"})
public class SubsClassProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // get Annotated class list
        List<String> clazzList = getAnnotatedClazz(roundEnv);

        // parse class
        tryParseClasses(clazzList);
        return true;
    }

    private List<String> getAnnotatedClazz(RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(SubsClass.class);
        List<String> clazzList = new ArrayList<>();
        for (Element element : annotatedElements) {
            String clazzName = element.asType().toString();
            clazzName = element.getAnnotation(SubsClass.class).value() + "\\" + clazzName;
            clazzList.add(clazzName);
        }
        return clazzList;
    }


    private void tryParseClasses(List<String> clazzList) {
        for (String name : clazzList) {
//            String realClassPath = System.getProperty("user.dir") + "\\" + name.replace('.', '\\') + ".java";
            String realClassPath = name.replace('.', '\\') + ".java";
            parseClazz(realClassPath);
        }
    }

    private void parseClazz(String path) {
        log("path", path);
        File sourceFile = new File(path);
        JavaParser javaParser = new JavaParser();

        try {
            ParseResult<CompilationUnit> compilationUnits = javaParser.parse(sourceFile);
            Optional<CompilationUnit> unitOptional = compilationUnits.getResult();
            CompilationUnit compilationUnit = unitOptional.get();
            log("unit == " , compilationUnit.toString());
            log(compilationUnits.toString());
            VoidVisitor visitor = new MethodNamePrinter();
            visitor.visit(compilationUnit, null);
            compilationUnit.addClass("TestClaSS")
                    .setPublic(true)
                    .setStatic(true);
            log("filename", compilationUnit.getStorage().get().getPath().toString());
            compilationUnit.getStorage().get().save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void log(String msg) {
        System.out.println("-----> " + msg);
    }

    private static void log(String tag, String msg) {
        System.out.println("-----> " + tag + " : " + msg);
    }
}
