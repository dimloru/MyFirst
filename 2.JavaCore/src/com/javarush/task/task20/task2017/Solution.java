package com.javarush.task.task20.task2017;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* 
Десериализация
*/
public class Solution {
    public A getOriginalObject(ObjectInputStream objectStream) {
        A resultA;
        try {
            resultA = (A) objectStream.readObject();
            return resultA;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        try {
//            B resultB = (B) resultA;
//            return resultB;
//        } catch (ClassCastException e) {
//            return resultA;
//        }
    }

    public class A implements Serializable {
    }

    public class B extends A {
        public B() {
            System.out.println("inside B");
        }
    }

    public static void main(String[] args) {

    }
}
