package com.example.gcalc;

import javafx.scene.control.Alert;

public class util {

    /**
     * Takes start index and finds the nex point at which a car occurs and returns the position
     * */
    public static int until(int startIndex, String x, char until) {
        for(int i = startIndex; i < x.length(); i++)
            if(x.charAt(i) == until) return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilOperator(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(isOperator(x.charAt(i))) return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilNum(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isDigit(x.charAt(i))) return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static int untilLetter(int startIndex, String x) {
        for(int i = startIndex; i < x.length(); i++)
            if(Character.isLetter(x.charAt(i))) return i;

        throw new IllegalArgumentException("Char not found inside string");
    }

    public static boolean isOperator(char x) {
        return x == '+' || x == '-' || x == '*' || x == '/';
    }

    public static int countChar(int startIndex, String x, char c) {
        int o = 0;
        for(int i = startIndex; i < x.length(); i++)
            if(x.charAt(i) == c) o++;
        return o;
    }

    public static int countOperator(int startIndex, String x) {
        int o = 0;
        for(int i = startIndex; i < x.length(); i++)
            if(isOperator(x.charAt(i))) o++;
        return o;
    }

    public static char[] pullVars(String equation, String rawEquation) {
        boolean isTerm = equation.charAt(0) == 't' && equation.charAt(1) == '=', c2 = has2Chars(rawEquation, isTerm);
        char[] temp = new char[c2 ? 2 : 1];
        for(int i = 0; i < temp.length; i++)
            temp[i] = rawEquation.charAt(rawEquation.length() - (isTerm ? 3 + (c2 ? i : i - 1) : 2 + (c2 ? i : i - 1))); // Need to swap positions inside the array
        // if there are 2 terms it should set number 1 to length - isTerm ? 3 : 2 and then number 2 in the array to length - isTErm ? 4 : 3

        return temp;
    }

    // Sample raw term = solve(t=quadratic(x^2+2x-4,x)) || solve(x^2+2x-4,x)
    // Sample with 2 t = solve(t=system(6x+3y=8;8x+1y=3,xy)) || solve(6x+3y=8;8x+1y=3,xy)
    public static boolean has2Chars(String rawEquation, boolean isTerm) {
        return (Character.isLetter(rawEquation.charAt(
                    isTerm ? rawEquation.length() - 3 : rawEquation.length() - 2)) &&
                Character.isLetter(rawEquation.charAt(
                    isTerm ? rawEquation.length() - 4 : rawEquation.length() - 3)));
    }

    public static boolean hasPower(int startIndex, String x) {
        for(int i = 0; i < x.length(); i++)
            if(x.charAt(i) == '^') return true;

        return false;
    }

    public static void errorMessage(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
