package com.example.gcalc.advancedCalculations;

import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFactor {
    private static double[] ab(String rawEquation) {
        String equation = rawEquation.substring(util.until(0, rawEquation, '('), util.until(0, rawEquation, ')') + 1);
        equation = equation.replaceAll("\\s", "");

        double mid = util.until(0, equation, ','),
                a = Double.parseDouble(equation.substring(util.until(0, equation, '(') + 1, (int) mid)),
                b = Double.parseDouble(equation.substring((int)mid + 1, util.until((int) mid, equation, ')')));
        return new double[]{a, b};
    }

    public static Double HCF(String rawEquation) {
        rawEquation = rawEquation.replaceAll("ans", String.valueOf(HandleStack.ans));
        double[] ab = ab(rawEquation);
        if(ab[0] == 0 || ab[1] == 0) {
            util.errorMessage("a | b cannot be 0", "0 exception");
            throw new IllegalArgumentException("0");
        }

        double hcf =0;
        for(int i = 1; i <= ab[0] || i <= ab[1]; i++)
            if( ab[0]%i == 0 && ab[1]%i == 0 )
                hcf = i;

        return hcf;
    }

    public static double LCM(String rawEquation) {
        rawEquation = rawEquation.replaceAll("ans", String.valueOf(HandleStack.ans));
        double[] ab = ab(rawEquation);
        return Math.abs(ab[0]*ab[1]) / HCF(rawEquation);
    }
}
