package com.javarush.task.task34.task3411;

/* 
Ханойские башни
*/

public class Solution {
    public static void main(String[] args) {
        int count = 3;
        moveRing('A', 'B', 'C', count);
    }

    public static void moveRing(char a, char b, char c, int count) {
        //напишите тут ваш код
//        if (count == 1) System.out.println("from " + a + " to " + b);
//        else if (count == 2) {
//            System.out.println("from " + a + " to " + c);
//            System.out.println("from " + a + " to " + b);
//            System.out.println("from " + c + " to " + b);
//        }
//        else {
//            if (count % 2 == 0){ // even
//                moveRing(a, c, b, (count - 1));
//                System.out.println("from " + a + " to " + b);
//                moveRing(c, b, a, (count - 1));
//            }
//            else { // odd
//                moveRing(a, c, b, (count - 1));
//                System.out.println("from " + a + " to " + b);
//                moveRing(c, b, a, (count - 1));
//            }
//        }

        // second edition
        if (count > 0) {
            moveRing(a, c, b, count - 1);
            System.out.println("from " + a + " to " + b);
            moveRing(c, b, a, count - 1);
        }
    }
}