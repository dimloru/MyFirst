package com.javarush.task.task20.task2022;

import java.io.*;

/* 
Переопределение сериализации в потоке
*/
public class Solution implements Serializable, AutoCloseable {
    private transient FileOutputStream stream;
    private String fileName;
    private static String fileForObjects = "c:/javax/b4.txt";

    public Solution(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.stream = new FileOutputStream(fileName);
    }

    public void writeObject(String string) throws IOException {
        stream.write(string.getBytes());
        stream.write("\n".getBytes());
        stream.flush();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
//        out.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        stream = new FileOutputStream(fileName, true);
//        in.close();
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing everything!");
        stream.close();
    }

    public static void main(String[] args) throws Exception {
        Solution solution1 = new Solution("c:/javax/b1.txt");
//        solution.stream = new FileOutputStream("c:/javax/b1.txt");
        solution1.writeObject("Test String.");
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileForObjects));
        outputStream.writeObject(solution1);
        outputStream.flush();

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileForObjects));
        Solution solution2 = (Solution) inputStream.readObject();
        solution2.writeObject("Second string");



    }
}
