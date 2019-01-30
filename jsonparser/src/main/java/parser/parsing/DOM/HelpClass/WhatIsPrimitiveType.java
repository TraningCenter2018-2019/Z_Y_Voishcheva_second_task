package parser.parsing.DOM.HelpClass;

public class WhatIsPrimitiveType {
    public static Class<?> getType(String str) {
        if(isInteger(str))
            return Integer.class;
        if(isDouble(str))
            return Double.class;
        if(isDouble(str))
            return Double.class;
        if(isFloat(str))
            return Float.class;
        if(isBoolean(str))
            return Boolean.class;
        return null;
    }

    private static boolean isInteger(String s) {
        s = s.trim();
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String s) {
        s = s.trim();
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String s) {
        s.trim();
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isBoolean(String s) {
        s = s.trim();
        try {
            Boolean.parseBoolean(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
