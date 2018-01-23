package com.javarush.task.task20.task2003;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* 
Знакомство с properties
*/
public class Solution {
    public static Map<String, String> properties = new HashMap<>();

    public void fillInPropertiesMap() {
        //implement this method - реализуйте этот метод
        String file = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            file = reader.readLine();
        } catch (IOException e) {}
        try (FileInputStream inputStream = new FileInputStream(file)) {
            load(inputStream);
        } catch (IOException e) {
        } catch (Exception e) {}

    }

    public void save(OutputStream outputStream) throws Exception {
        //implement this method - реализуйте этот метод
        PrintWriter writer = new PrintWriter(outputStream);
        Properties prop = new Properties();
        properties.forEach((k, v) -> prop.setProperty(k, v));

//        for (Map.Entry<String, String> pair : properties.entrySet()) {
//           prop.setProperty(pair.getKey(), pair.getValue());
//        }
        prop.store(outputStream, null);


//        for (Map.Entry<String, String> pair : properties.entrySet()) {
//            writer.println(pair.getKey() + " : " + pair.getValue());
//        }

    }

    public void load(InputStream inputStream) throws Exception {
        //implement this method - реализуйте этот метод
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Properties prop = new Properties();
        prop.load(inputStream);
        prop.keySet().forEach(k -> {
            //System.out.println((String)k + prop.getProperty((String)k));
            properties.put((String)k, prop.getProperty((String)k));
            }
        );




//        while (reader.ready()) {
//            String line = reader.readLine();
//            String[] lineSplit = line.split(" ?[:=] ?");    //||( [:=] )");  //работало
//            //System.out.println(lineSplit[0].trim() + " -- " + lineSplit[1].trim());
//            properties.put(lineSplit[0], lineSplit[1]);
//        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {
        properties.put("Alpha", "Omega");
        properties.put("JavaRush", "C:/javax/");
        new Solution().save(new FileOutputStream(args[0]));


        new Solution().fillInPropertiesMap();
        for (Map.Entry<String, String> pair : properties.entrySet()) {
            System.out.println(pair.getKey() + " ::: " + pair.getValue());
        }

//        String str = "some key:its value";
//        String[] split = str.split(" ?: ?");
//        System.out.println(split[0] + "=" + split[1]);

    }
}
