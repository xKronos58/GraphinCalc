package com.example.gcalc.Calculator;

public class ConvertCoPx {
    public static double convertX(double X) {
        // x -> -15 -> 15 = 150 -> 750
        // x +15 -> 0 -> 30 = 0 -> 600
        // x *20 -> 0 -> 600 = 0 -> 600
        // x +150 -> 150 -> 750 = 150 -> 750
        return (X+15)*20+150;

    }

    public static double convertY(double Y) {
        //  Range is where 0 is greatest.
        //  If Y = -7.5 px -> 300
        return -1*((Y-7.5)*20);
    }
}
