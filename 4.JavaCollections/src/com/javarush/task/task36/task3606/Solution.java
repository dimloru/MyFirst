package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File folder = new File(packageName);
        for (File file : folder.listFiles()) {
            if (file.getName().toLowerCase().endsWith(".class")) {
                Loader loader = new Loader(file);
                Class clazz = loader.loadClass("");
                hiddenClasses.add(clazz);
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        for (Class clazz : hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
               try {
                   Constructor constructor = clazz.getDeclaredConstructor();
                   constructor.setAccessible(true);
                   Object obj = constructor.newInstance();
                   return (HiddenClass) obj;
               } catch (Exception e) {}
            }
        }

        return null;
    }

    public static class Loader extends ClassLoader {
        private File file;

        public Loader(File file) {
            super();
            this.file = file;
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] buffer = null;
            try {
                buffer = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

//            String nm = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("javarush") - 4, file.getAbsolutePath().indexOf(".class"))
//                    .replace("\\", ".");

//            System.out.println("Defining: " + nm);
            return defineClass(null, buffer, 0, buffer.length);
        }
    }
}