package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        //напишите тут ваш код
        StringBuilder result = new StringBuilder(number + " =");
        String str = getSignsString(number);

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0') {
                result.append(" " + str.charAt(i) + " " + (int)Math.pow(3, i));
            }
        }
        System.out.println(result.toString());
    }

    public static String getSignsString(int number) {
        if (number >=3) {
            int ostatok = number % 3;
            int next = number / 3;
            if (ostatok == 2) {
                return "-" + getSignsString(++next);
            } else if (ostatok == 1) return "+" + getSignsString(next);
            else return "0" + getSignsString(next);
        } else {
            if (number == 2) return "-+";
            else return "+";
        }
    }
}