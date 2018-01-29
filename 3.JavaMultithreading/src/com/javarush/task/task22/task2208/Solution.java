package com.javarush.task.task22.task2208;

import java.util.HashMap;
import java.util.Map;

/* 
Формируем WHERE
*/
public class Solution {
    public static void main(String[] args) {
//        StringBuilder sb = new StringBuilder("abcdefg");
//        sb.delete(sb.length() - 4, sb.length());
//        System.out.println(sb.toString());
        Map<String, String> where = new HashMap<>();
//        where.put("name", "Vasya");
//        where.put("age", "17");
//        where.put("country", "Russia");
//        where.put("city", null);
        where.put(null, null);

        System.out.println(getQuery(where));


    }
    public static String getQuery(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        if (params == null || params.isEmpty()) return "";
        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (pair.getValue() != null) {
                result.append(pair.getKey());
                result.append(" = '");
                result.append(pair.getValue());
                result.append("' and ");
            }
        }
        if (result.length() > 5) result.delete(result.length() - 5, result.length());

        return result.toString();
    }
}
