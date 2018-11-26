package com.example.taskmanager.util;

/**
 * Created by 76952 on 2018/11/25.
 */

public class Base64 {
    public static String encode(String oldStr){
        return android.util.Base64.encodeToString(oldStr.getBytes(), android.util.Base64.NO_WRAP);
    }
    public static String decode(String secrectStr) {
        return new String(android.util.Base64.decode(secrectStr, android.util.Base64.NO_WRAP));
    }
}
