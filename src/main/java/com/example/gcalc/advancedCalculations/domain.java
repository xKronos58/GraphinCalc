package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

public class domain {
    public domain(String inequality) {
        if(!inequality.substring(util.untilInequality(0, inequality)).equals("0")) inequality = convertToZero(inequality);
    }

    public static void create() {

    }

    public static String convertToZero(String inequality) {

        Inequality type = getType(inequality);
        int endingStatement = util.untilInequality(0, inequality) == '!' ? util.untilInequality(0, inequality) : util.untilInequality(0, inequality) + 1;
        String ending = inequality.substring(endingStatement + 1);
        StringBuilder _final = new StringBuilder();

        _final.append(inequality.substring(util.untilInequality(0, inequality) - 1));

        if(ending.charAt(0) == '-') _final.append(ending.substring(1));
        else _final.append("-").append(ending);
        return String.valueOf(_final);
    }

    public static Inequality getType(String x) {
        return switch (x.charAt(util.untilInequality(0, x))) {
            case '>' -> Inequality.GREATER_THAN;
            case '<' -> Inequality.LESS_THAN;
            case '=' -> Inequality.EQUAL;
            case '!' ->
                switch (x.charAt(util.untilInequality(0, x)) + 1) {
                    case '>' -> Inequality.NOT_GREATER_THAN;
                    case '<' -> Inequality.NOT_LESS_THAN;
                    case '=' -> Inequality.NOTEQUAL;
                    default -> throw new IllegalArgumentException();
                };
            default -> throw new IllegalArgumentException();
        };
    }

    public enum Inequality {
        GREATER_THAN,
        LESS_THAN,
        EQUAL,
        NOTEQUAL,
        NOT_GREATER_THAN,
        NOT_LESS_THAN
    }
}
