package com.javarush.task.task21.task2101;

/* 
Определяем адрес сети
*/
public class Solution {
    public static void main(String[] args) {
        byte[] ip = new byte[]{(byte) 192, (byte) 168, 1, 2};
        byte[] mask = new byte[]{(byte) 255, (byte) 255, (byte) 254, 0};
        byte[] netAddress = getNetAddress(ip, mask);
//        for (byte el : netAddress) {
//            System.out.print(el + " ");
//        }
        print(ip);          //11000000 10101000 00000001 00000010
        print(mask);        //11111111 11111111 11111110 00000000
        print(netAddress);  //11000000 10101000 00000000 00000000
    }

    public static byte[] getNetAddress(byte[] ip, byte[] mask) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (byte) (ip[i] & mask[i]);
        }
        return result;
    }

    public static void print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            int tmp = b;
            if (tmp < 0) tmp += 256; // tmp [0..255]

            for (int i = 128; i >= 1; i /= 2) {
                sb.append(tmp / i);
                tmp = tmp % i;
            }
            sb.append(" ");
        }

        System.out.println(sb.toString().trim());
    }
}
