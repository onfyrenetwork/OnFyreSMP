package net.onfyre.onfyresmp.utils;

public class StringFormatUtil {

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
