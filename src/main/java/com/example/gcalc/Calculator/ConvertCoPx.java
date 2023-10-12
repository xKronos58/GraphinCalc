package com.example.gcalc.Calculator;

public class ConvertCoPx {
    public static double convertX(double X) {
        //  x.0 == 450px
        //  x.min(-15) == 150px
        //  x.max(15) == 750px
        // x = -15 -> 15

        return X == 0 ? 450 : (450 + (20 * X));

    }

    public static double convertY(double Y) {
        return (Y * 20) - 150;
    }
}
