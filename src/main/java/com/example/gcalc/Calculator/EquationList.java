package com.example.gcalc.Calculator;

import com.example.gcalc.GCController;
import com.example.gcalc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EquationList {

    // Physics equations
    public static double predefinedEquation(String _equation, String type) {
        String equation = _equation.substring(5, _equation.length() -1);
        int firstBreak = util.until(0, equation, ',');
        double a = HandleStack.evaluate(equation.substring(0, firstBreak)),
                b = HandleStack.evaluate(equation.substring(firstBreak + 1));
        double c = 0;
        if(type.equals("wrk")){
            c = HandleStack.evaluate(equation.substring(util.until(firstBreak + 1, equation, ','), equation.length() - 1));
        }
        char eqType = _equation.charAt(3);

        HandleStack.ans = switch (type) {
            case "mfd" -> (Constants.pico0 / (2 * Constants.pi)) * (a/b);
            case "tev" -> Math.sqrt((2 * Constants.protonCharge * a) / b);
            case "spd" -> a/b;
            case "sop" -> ((Constants.protonMass * a) / (Constants.protonCharge * b))/4;
            case "mas" -> eqType == '0' ? a/b : a*b;
            case "wrk" -> a * b * c == 0 ? 1 : Math.cos(c);
            case "kne" -> (1.0/2.0) * a * Math.pow(b, 2);
            case "dpe" -> a * Constants.gravity * b;
            default -> {
                util.errorMessage("Invalid equation type: " + type, "Invalid Equation");
                throw new IllegalArgumentException("Unexpected Arg");
            }
        };

        return HandleStack.ans;
    }

    public static double isExpression(String element) {
        if(element.charAt(0) == 'e')
            try {
                return HandleStack.evaluate(element.substring(2, element.length() - 1));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        else if(element.charAt(0) == 'm')
            try {
                return SimpleArithmetic.findMass(element.substring(2, element.length() - 1));
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        return Double.parseDouble(element);
    }
}
