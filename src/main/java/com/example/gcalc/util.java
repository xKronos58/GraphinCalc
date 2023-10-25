package com.example.gcalc;

public class util {

    /**
     * Takes start index and finds the nex point at which a car occurs and returns the position
     * */
    public static int until(int startIndex, String x, char until) {
        for(int i = startIndex; i < x.length(); i++)
            if(x.charAt(i) == until)
                return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilSymbol(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(isOperator(x.charAt(i)))
                return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilNum(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isDigit(x.charAt(i)))
                return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilLetter(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isLetter(x.charAt(i)))
                return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static boolean isOperator(char x) {
        return x == '+' || x == '-' || x == '*' || x == '/';
    }

    public static int HasChar(String x, int startIndex) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isLetter(x.charAt(i)))
                return i;
        return 0;
    }


}
