package com.dongmodao.subs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : tangqihao
 * @date : 2019/4/19
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ASubsClass {
    String value();
}
