package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {

        String str = Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data";

        Set<? extends Animal> allAnimals = getAllAnimals(str.substring(1, str.length()));
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set <Animal> result = new HashSet<>();
        File dir = new File(pathToAnimals);
        ClassLoader animalLoader = new AnimalLoader(pathToAnimals, Solution.class.getPackage().getName().replaceAll("[/]", ".") + ".");

        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                String fileName = file.toPath().getFileName().toString();
                String className = fileName.substring(0, fileName.lastIndexOf(".class"));
                try {
                    Class fromLoader = animalLoader.loadClass(className);
                    Constructor constructor = fromLoader.getConstructor();
//                    constructor.setAccessible(true); // с getDeclaredConstructor и setAccessible интереснее
                    Object obj = constructor.newInstance();
                    if (obj instanceof Animal) {
                        Animal animal = (Animal) obj;
                        result.add(animal);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}


