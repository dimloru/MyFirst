package com.javarush.task.task21.task2104;

import java.util.HashSet;
import java.util.Objects;
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
        if (n == null) return false;
        if (getClass() != n.getClass()) return false;
//        if(!n.getClass().getSimpleName().equals("Solution")) return false;

        Solution sol = (Solution) n;

        if (first != null ? !first.equals(sol.first) : sol.first != null) return false;
        if (last != null ? !last.equals(sol.last) : sol.last != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = first == null ? 0 : first.hashCode();
        result += last == null ? 0 : last.hashCode();
        return 31 * result;
    }




    public static void main(String[] args) {
        Set<Solution> s = new HashSet<>();
        s.add(new Solution("Donald", "Duck"));
//        System.out.println(s.contains(new Solution("Donald", "Duck")));
        ///
        Solution sol1 = new Solution("D", null);
        Solution sol2 = new Solution("D", null);
        System.out.println(sol1.equals(sol2));

    }
}
