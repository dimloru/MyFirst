package com.javarush.task.task21.task2106;

import java.util.Date;
import java.util.Objects;

/* 
Ошибка в equals/hashCode
*/
public class Solution {
    private int anInt;
    private String string;
    private double aDouble;
    private Date date;
    private Solution solution;

    public Solution(int anInt, String string, double aDouble, Date date, Solution solution) {
        this.anInt = anInt;
        this.string = string;
        this.aDouble = aDouble;
        this.date = date;
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Solution)) return false;

        Solution solution1 = (Solution) o;

        if (Double.compare(solution1.aDouble, aDouble) != 0) return false;
        if (anInt != solution1.anInt) return false;
        if (!(date != null ? date.equals(solution1.date) : solution1.date == null)) return false;

        if (!(
                (solution != null && solution1.solution != null) ?
                solution.equals(solution1.solution) :
                (solution1.solution == null && solution == null)
        )) return false;

        if (!Objects.equals(string, solution1.string)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = anInt;
        temp = aDouble != +0.0d ? Double.doubleToLongBits(aDouble) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (solution != null ? solution.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (string != null ? string.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) {
        Date d = new Date();
        Solution s1 = new Solution(13, "abc", 3.14, d, null);
        Solution s2 = new Solution(13, "abc", 3.14, d, null);
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode() + " / " + s2.hashCode());

    }
}
