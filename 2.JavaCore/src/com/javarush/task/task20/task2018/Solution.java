package com.javarush.task.task20.task2018;

import java.io.*;

/* 
Найти ошибки
*/
public class Solution implements Serializable {
    public static class A {
        protected String name = "A";

        public A(String name) {
            this.name += name;
        }

        public A() {
        }

//        protected A() {
//            name = "newA";
//        }
    }

    public class B extends A implements Serializable {
//        private String name = "B1";

        public B(String name) {
            super(name);
            this.name += name;
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            ois.defaultReadObject();
            name = (String) ois.readObject();


        }

        private void writeObject(ObjectOutputStream ous) throws IOException {
            ous.defaultWriteObject();
            ous.writeObject(name);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(arrayOutputStream);

        Solution solution = new Solution();
        B b = solution.new B("B2");
        System.out.println(b.name);

        oos.writeObject(b);
        oos.flush();

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(arrayInputStream);

        B b1 = (B)ois.readObject();
        System.out.println(b1.name);
    }
}
