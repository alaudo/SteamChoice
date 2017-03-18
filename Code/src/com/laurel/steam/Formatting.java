package com.laurel.steam;

/**
 * Created by alexg on 3/17/2017.
 */
public class Formatting {
    public static final int TAB_SIZE = 5;

    public static String getTabs(int n) {
        return getSpaces(n * TAB_SIZE);
    }

    public static String getSpaces(int n) {
        return String.format("%1$-"+ n +"s", "");
    }


    public static String padToWidth(String s1, int width, String s2) {
        int n = width - s2.length();
        if (n <= 0) {
            s1 = s1.substring(0,s1.length() + n - 1);
            n = 1;
        }
        return String.format("%1$-" + n + "s", s1) + s2;
    }
}
