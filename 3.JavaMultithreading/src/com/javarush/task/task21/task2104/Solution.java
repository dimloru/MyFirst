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

    public boolean equals(Solution n) {
//        return n.first.equals(first) && n.last.equals(last);
        if (this == n) return true;
        if (getClass() != n.getClass()) return false;
        if (first != null ? !first.equals(n.first) : n.first != null) return false;
        if (last != null ? !last.equals(n.last) : n.last != null) return false;
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
        Solution sol1 = new Solution("Donald", "Duck");
        Solution sol2 = new Solution("Donald", "Duck");
        System.out.println(sol1.hashCode());
        System.out.println(sol2.hashCode());
    }
}
