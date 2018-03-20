package me.idarkyy.nanocore.utils;

public class ParseUtils {
    public static Integer parseInteger(String string) {
        try {
            int i = Integer.parseInt(string);
            return i;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
