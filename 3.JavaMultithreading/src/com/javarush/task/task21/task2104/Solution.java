package com.javarush.task.task21.task2104;

import java.util.HashSet;
import java.util.Set;

/* 
Equals and HashCode
*/
public class Solution {
    private final String first, last;

    public Solution(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public boolean equals(Object n) {
//        return n.first.equals(first) && n.last.equals(last);
        if (this == n) return true;
        System.out.println(getClass() + " - " + n.getClass());
        if (getClass() != n.getClass()) return false;
        Solution sol = (Solution) n;
        if (first != null ? !first.equals(sol.first) : sol.first != null) return false;
        if (last != null ? !last.equals(sol.last) : sol.last != null) return false;
        return true;
    }

    public int hashCode() {
        return 31 * first.hashCode() + last.hashCode();
    }

    public static void main(String[] args) {
        Set<Solution> s = new HashSet<>();
        s.add(new Solution("Donald", "Duck"));
        System.out.println(s.contains(new Solution("Donald", "Duck")));
        ///
        Solution sol1 = new Solution("D", "Duck");
        Solution sol2 = new Solution("D", "Duck");
        System.out.println(sol1.hashCode());
        System.out.println(sol2.hashCode());
    }
}
