package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Expand {
    public static String expand(String equation) {
        return multiply(storeElements(equation));
    }

    private static String multiply(Stack<bracket> elements){
        return "";
    }

        private static Stack<bracket> storeElements(String equation) {
        Stack<bracket> temp = new Stack<>();
        for(int i = 0; i < equation.length(); i++){
            if(equation.charAt(i) == '(')
                temp.push(sortElements(equation, i));
        }

        return new Stack<>();
    }

    private static bracket sortElements(String equation, int StartIndex) {
        List<Double> x1 = new ArrayList<>();
        List<Character> p1 = new ArrayList<>();
        String split = equation.substring(StartIndex + 1, util.until(StartIndex, equation, ')'));
        int lastOpPos = 0;

        // Splits the elements into their respective Lists<> then pushes them as a Bracket (Type)
        for(int i = 0; i < split.length(); i++){
            if(util.isOperator(split.charAt(i))){
                int p1Len = p1.size();
                // For this one specifically 2x should be used instead of 2*x
                String temp = split.substring(lastOpPos, i - 1);
                StringBuilder sb = new StringBuilder();

                // Splits up the string into the pro numeral and coefficient
                for(int j = 0; j < temp.length(); j++){
                    if(Character.isDigit(temp.charAt(j)) || temp.charAt(j) == '.')
                        sb.append(temp.charAt(j));
                    else if (Character.isLetter(temp.charAt(j)))
                        p1.add(temp.charAt(j));
                }
                if(p1.size() == p1Len)
                    p1.add('1');
                x1.add(Double.parseDouble(sb.toString()));
            }
        }

        return new bracket(x1, p1);
    }

    public record bracket(List<Double> x1, List<Character> p1) {};
}

