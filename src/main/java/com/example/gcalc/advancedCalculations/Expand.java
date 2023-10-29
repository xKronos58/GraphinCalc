package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Expand {
    public static String expand(String equation) {
        Stack<bracket> ai = storeElements(equation);
        for(bracket b : ai)
            System.out.println(b);

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

        return temp;
    }

    private static bracket sortElements(String equation, int StartIndex) {
        List<Double> x1 = new ArrayList<>();
        List<Character> p1 = new ArrayList<>();
        String split = equation.substring(StartIndex + 1, util.until(StartIndex, equation, ')'));
        int lastOpPos = 0;
        //TODO : major rewrite needed as it is only evaluating the first element in the bracket, but i dont have enough caffeine to do that now

        int elementCount = util.countOperator(0, split), lastNumLocation = 0, itterations = 0;
        boolean numIsNegative = false;

        for(int i = 0; i < split.length(); i++){

            if((util.isOperator(split.charAt(i)) || split.charAt(i) == ')') && itterations < elementCount + 1 ){
                if(split.charAt(i) == '-')
                    numIsNegative = true;

                String temp = split.substring(lastNumLocation, i);
                StringBuilder sb = new StringBuilder();

                for(int o = 0; o < temp.length(); o++){
                    if(Character.isDigit(temp.charAt(o)) || temp.charAt(o) == '.')
                        sb.append(temp.charAt(o));
                    else if (Character.isLetter(temp.charAt(o)))
                        p1.add(temp.charAt(o));
                }
                x1.add(Double.parseDouble(sb.toString()) * (numIsNegative ? -1 : 1));

                lastNumLocation = i;
                itterations++;
                i++;
            }

        }

        return new bracket(x1, p1);
    }

    public record bracket(List<Double> x1, List<Character> p1) {};
}

