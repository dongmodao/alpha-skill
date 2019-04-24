package com.dongmodao.annoprocess.visitors;

import com.github.javaparser.ast.visitor.ModifierVisitor;

/**
 * @author : tangqihao
 * @date : 2019/4/24
 */
public class BaseVisitor extends ModifierVisitor {

    static void log(String msg) {
        System.out.println(msg);
    }

    static void log_(String msg) {
        log("-----> : " + msg);
    }

    static void log_e(String tag, String msg) {
        log("-----> " + tag + " : " + msg);
    }

    static void log_t(String msg) {
        log("\n" + msg);
    }
}
