package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
//        getExpectedClass();
    }

    public static Class getExpectedClass() {
        Class[] classes = Collections.class.getDeclaredClasses();
        for (Class cl : classes) {
            if (Modifier.isStatic(cl.getModifiers()) && Modifier.isPrivate(cl.getModifiers()) &&
                    List.class.isAssignableFrom(cl)) {
                Constructor[] constructors = cl.getDeclaredConstructors();
                for (Constructor constructor : constructors) {
                    if (constructor.getParameterCount() == 0) {
                        constructor.setAccessible(true);
                        try {
                            List entity = (List) constructor.newInstance();
                            Object something = entity.get(0);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IndexOutOfBoundsException e) {
                            return cl;
                        }
                    }
                }
            }
        }

        return null;
    }
}
