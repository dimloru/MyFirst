package com.javarush.task.task32.task3211;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/* 
Целостность информации
*/

public class Solution {
    public static void main(String... args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new String("test string"));
        oos.flush();
        System.out.println(compareMD5(bos, "5a47d12a2e3f9fecf2d9ba1fd98152eb")); //true

    }

    public static boolean compareMD5(ByteArrayOutputStream byteArrayOutputStream, String md5) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        byte[] bytes = byteArrayOutputStream.toByteArray();
        StringBuffer hash = new StringBuffer(32);
        byte checksum[] = messageDigest.digest(bytes);
        for (byte b : checksum) {
//            альтернативная запись
//            hash.append(String.format("%02x", b));
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hash.append('0');
            }
            hash.append(hex);
        }
//        BigInteger i = new BigInteger(1, messageDigest.digest(byteArrayOutputStream.toByteArray()));
//        return String.format("%032x", i).equals(md5);

        return hash.toString().equals(md5);
    }
}
