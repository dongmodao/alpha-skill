package com.dongmodao.annoprocess;


import com.dongmodao.subs.annotation.ASubsClass;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class ASubsClassProcess extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtils = processingEnvironment.getTypeUtils();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }


    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(ASubsClass.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<TypeElement, List<FieldInfo>> targetMap = getTargetMap(roundEnvironment);

        createJavaFile(targetMap.entrySet());
        return false;
    }


    private Map<TypeElement, List<FieldInfo>> getTargetMap(RoundEnvironment roundEnvironment) {

        Map<TypeElement, List<FieldInfo>> targetMap = new HashMap<>();

        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(ASubsClass.class);
        for (Element element : annotatedElements) {
            String fieldName = element.getSimpleName().toString();
            String value = element.getAnnotation(ASubsClass.class).value();
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            List<FieldInfo> list = targetMap.get(typeElement);
            if (list == null) {
                list = new ArrayList<>();
                targetMap.put(typeElement, list);
            }

            list.add(new FieldInfo(fieldName, value));

        }

        return targetMap;
    }

    /**
     * 创建Java文件
     * @param entries
     */
    private void createJavaFile(Set<Map.Entry<TypeElement, List<FieldInfo>>> entries) {
        for (Map.Entry<TypeElement, List<FieldInfo>> entry : entries) {
            TypeElement typeElement = entry.getKey();
            List<FieldInfo> list = entry.getValue();
            if (list == null || list.size() == 0) {
                continue;
            }

            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            String className = typeElement.getQualifiedName().toString().substring(packageName.length() + 1);
            String newClassName = className + "_SubsClass";


            MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.bestGuess(className), "target");
            for (FieldInfo fieldInfo : list) {

                methodBuilder.addStatement
                        ("target.$L = $S", fieldInfo.getFieldName()
                                , fieldInfo.getVal());
            }


            TypeSpec typeBuilder = TypeSpec.classBuilder(newClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build())
                    .build();


            JavaFile javaFile = JavaFile.builder(packageName, typeBuilder)
                    .addFileComment("Generated code from Subs Processor. Do not modify!")
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
