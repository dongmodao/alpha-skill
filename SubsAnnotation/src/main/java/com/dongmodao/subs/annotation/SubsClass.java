package com.dongmodao.subs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SubsClass {
    String value();
    // TODO: 2019/4/19 标记在类名上 表示该类需要进行注入
    // TODO: 2019/4/19 类名获取对应的变量，方法
    // TODO: 2019/4/19 对变量和方法进行存储，注意重名问题，建立命名池
    // TODO: 2019/4/19 对方法进行初步的注入 dead code，注入的方式可参考 https://www.sable.mcgill.ca/JBCO/examples.html 有点老，但是感觉还有挺高可行性
    // TODO: 2019/4/19 逐一对方法和变量进行行混淆 + dead code


    // TODO: 2019/4/19 准备
    // TODO: 2019/4/19 1. 根据标记的类名 获取类文件的所有内容 包括变量，方法，对应的信息
    // TODO: 2019/4/19 2. 获取方法的具体内容
    // TODO: 2019/4/19 3. JavaPoet 使用，写入与读取

    // TODO: 2019/4/19 准备之前的准备
    // TODO: 2019/4/19 javax.lang.model 库用于对源码进行操作？可能与 JavaPoet 结合起来好用
}
