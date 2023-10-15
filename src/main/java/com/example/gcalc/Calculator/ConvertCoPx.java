package com.example.gcalc.Calculator;

public class ConvertCoPx {
    public static double convertX(double X) {
        //  x.0 == 450px
        //  x.min(-15) == 150px
        //  x.max(15) == 750px
        // x = -15 -> 15


        return X == 0 ? 315 : (315 + ( X));


    }

    public static double convertY(double Y) {
        return Y; // ?? TODO Fix the co-ords system
    }
}
