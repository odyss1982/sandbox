package com.king.codingtest.utils;

/**
 * Created by Odyss on 18/06/2014.
 */
public final class StringUtils {

    public final static String FORWARD_SLASH = "/";
    public final static String LINE_SEPERATOR = "\n";

    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    public static Integer convertToUnsignedInteger(final String integer) throws NumberFormatException {
        return Integer.parseUnsignedInt(integer);
    }

}
