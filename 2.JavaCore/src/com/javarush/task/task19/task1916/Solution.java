package com.javarush.task.task19.task1916;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* 
Отслеживаем изменения
*/

public class Solution {
    public static List<LineItem> lines = new ArrayList<LineItem>();

    public static void main(String[] args) {
        String file1 = "", file2 = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            file1 = reader.readLine();
            file2 = reader.readLine();
        } catch (IOException e) {}

        try (
                BufferedReader f1Reader = new BufferedReader(new FileReader(file1));
                BufferedReader f2Reader = new BufferedReader(new FileReader(file2))
                ) {

            Queue<String> first = new LinkedList<>();
            Queue<String> second = new LinkedList<>();

            while (f1Reader.ready()) first.add(f1Reader.readLine());
            while (f2Reader.ready()) second.add(f2Reader.readLine());

            String f1str = first.poll();
            String f2str = second.poll();
            while (true) {
                if (f1str == null && f2str == null) break;
                if (f1str == null) { lines.add(new LineItem(Type.ADDED, f2str)); break; } // last line
                if (f2str == null) { lines.add(new LineItem(Type.REMOVED, f1str)); break; } //last line
                if (f1str.equals(f2str)) {
                    lines.add(new LineItem(Type.SAME, f1str));
                    f1str = first.poll();
                    f2str = second.poll();
                    continue;
                }
                if (f1str.equals(second.peek())) {
                    lines.add(new LineItem(Type.ADDED, f2str));
                    f2str = second.poll();
                    continue;
                }
                if (f2str.equals(first.peek())) {
                    lines.add(new LineItem(Type.REMOVED, f1str));
                    f1str = first.poll();
                    continue;
                }

//                //Реализовал логику с пустыми строками
//                if (line1 == null || line1.length() == 0) lines.add(new LineItem(Type.ADDED, line2));
//                if (line2 == null || line2.length() == 0) lines.add(new LineItem(Type.REMOVED, line1));
//                else if (line1.equals(line2)) lines.add(new LineItem(Type.SAME, line1));
            }
//            // Вывод для проверки
//            for (int i = 0; i < lines.size(); i++) {
//                System.out.println(i + 1 + ". " + lines.get(i));
//            }

        } catch (IOException e) {}

    }


    public static enum Type {
        ADDED,        //добавлена новая строка
        REMOVED,      //удалена строка
        SAME          //без изменений
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }
//        //для проверки
//        public String toString() {
//            String result = "";
//            switch (this.type) {
//                case SAME: result = "SAME " + this.line; break;
//                case ADDED: result = "ADDED " + this.line; break;
//                case REMOVED: result = "REMOVED " + this.line; break;
//            }
//            return result;
//        }
    }
}
