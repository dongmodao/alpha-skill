package com.dongmodao.annoprocess;

import com.dongmodao.annoprocess.actions.CodeRunner;
import com.dongmodao.subs.annotation.SubsClass;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.List;
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
        new CodeRunner().run(path);
    }

    private static void log(String msg) {
        System.out.println("-----> " + msg);
    }

    private static void log(String tag, String msg) {
        System.out.println("-----> " + tag + " : " + msg);
    }
}
