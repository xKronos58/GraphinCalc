package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Expand {
    public static String expand(String equation) {
        return multiply(storeElements(equation));
    }

    public static String multiply(Stack<bracket> elements){
        return "";
    }

    public static Stack<bracket> storeElements(String equation) {
        Stack<bracket> temp = new Stack<>();
        for(int i = 0; i < equation.length(); i++){
            if(equation.charAt(i) == '(')
                temp.push(countElements(equation, i));
        }

        return new Stack<>();
    }

    public static bracket countElements(String equation, int StartIndex) {
        List<Double> x1 = new ArrayList<>();
        List<Character> p1 = new ArrayList<>();
        String split = equation.substring(StartIndex + 1, util.until(StartIndex, equation, ')'));
        int lastOpPos = 0;
        for(int i = 0; i < split.length(); i++){
            if(util.isOperator(split.charAt(i))){
                // For this one specifically 2x should be used instead of 2*x
                String temp = split.substring(lastOpPos, i - 1);
                int hasChar = util.HasChar(temp, 0);
                if(hasChar != 0)
                    System.out.println(temp);
            }

        }

        return new bracket(x1, p1);
    }
}

record bracket(List<Double> x1, List<Character> p1) {};
