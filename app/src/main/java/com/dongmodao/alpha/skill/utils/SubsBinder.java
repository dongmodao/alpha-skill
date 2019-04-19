package com.dongmodao.alpha.skill.utils;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: dongmodao
 * @date: 2019/4/19
 * @time: 2:35
 */
public class SubsBinder {

    public static void bind(Activity activity) {
        //1、获取全限定类名
        String name = activity.getClass().getName();
        try {
            //2、 根据全限定类名获取通过注解解释器生成的Java类，
            Class<?> clazz = Class.forName(name + "_SubsClass");
            //3、 通过反射获取构造方法并创建实例完成依赖注入
            clazz.getConstructor(activity.getClass()).newInstance(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
