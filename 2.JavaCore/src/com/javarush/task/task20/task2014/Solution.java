package com.javarush.task.task20.task2014;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 
Serializable Solution
*/
public class Solution implements Serializable {
    public static void main(String[] args) {
//        System.out.println(new Solution(4));
        try {
            File tmpFile = File.createTempFile("task2014", ".tmp");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tmpFile));

            Solution savedObject = new Solution(4);
            oos.writeObject(savedObject);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tmpFile));
            Solution loadedObject = new Solution(5);
            loadedObject = (Solution) ois.readObject();
            ois.close();
            System.out.println(loadedObject.string.equals(savedObject.string));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final transient String pattern = "dd MMMM yyyy, EEEE";
    private transient Date currentDate;
    private transient int temperature;
    String string;

    public Solution(int temperature) {
        this.currentDate = new Date();
        this.temperature = temperature;

        string = "Today is %s, and current temperature is %s C";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        this.string = String.format(string, format.format(currentDate), temperature);
    }

    @Override
    public String toString() {
        return this.string;
    }
}
