package com.javarush.task.task19.task1927;

/* 
Контекстная реклама
*/

import java.io.*;

public class Solution {
    public static TestString testString = new TestString();

    public static void main(String[] args) {
        PrintStream consoleStream = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(baos);
        System.setOut(stream);
        testString.printSomething();
        System.setOut(consoleStream);

        // Проверить на System line.separator
        // после второго вставить рекламу
        String input = baos.toString();
//        // Конверт baos to InputStream, обернут BufferedReader
//        InputStream is = new ByteArrayInputStream(input.getBytes());
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

//        Еще варинат:
//        baos.toByteArray() -> bais.

        String[] split = baos.toString().split(System.lineSeparator());
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (String el : split) {
            sb.append(el + System.lineSeparator());
            if (++counter % 2 == 0) { sb.append("JavaRush - курсы Java онлайн" + System.lineSeparator()); }
        }
        System.out.print(sb.toString());

//        // Вариант с чтением строки построчно)
//        Scanner scanner = new Scanner(baos.toString());
//        for(int count=1;scanner.hasNext();count++) {
//            System.out.println(scanner.nextLine());
//            if (count % 2 == 0)
//                System.out.println("JavaRush - курсы Java онлайн");
//        }




    }

    public static class TestString {
        public void printSomething() {
            System.out.println("first");
            System.out.println("second");
            System.out.println("third");
            System.out.println("fourth");
            System.out.println("fifth");
        }
    }
}
