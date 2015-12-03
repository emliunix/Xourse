package org.xourse;

/**
 * Utility to generate bunches of year data
 * Created by Liu Yuhui on 2015/12/3.
 */
public class YearDataGenerator {
    public static void main(String[] args) {
        for(int i = 2001; i < 2020; ++i) {
            System.out.println("\"" + Integer.toString(i) + "-" + Integer.toString(i + 1) + "\",");
        }
    }
}
