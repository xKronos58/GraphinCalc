package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Expand {
    public static String expand(String equation) {
        List<bracket> ai = storeElements(equation);
        for(bracket b : ai)
            System.out.println(b);

        return opRawList(multiply(storeElements(equation)));
    }

    private static String simplify(List<term> terms) {
        StringBuilder sb = new StringBuilder();
        for(int t1 = 0; t1 < terms.size(); t1++) {
            for(int t2 = 0; t2 < terms.size(); t2++) {
                if(t1 == t2) break;
                if((terms.get(t1).hasProNumeral() && terms.get(t2).hasProNumeral())
                        && (terms.get(t1).proNumeral.equals(terms.get(t2).proNumeral))
                        && (terms.get(t1).power == terms.get(t2).power)) {
                    sb.append(terms.get(t1).coefficient + terms.get(t2).coefficient)
                            .append(terms.get(t1).proNumeral)
                            .append(terms.get(t1).hasPower() ? "^" + terms.get(t1).power : "")
                            .append("+ ");
                }
            }
        }


        return sb.toString();
    }

    private static String opRawList(List<term> terms) {
        StringBuilder sb = new StringBuilder();
        for(term t : terms)
            sb.append(t.coefficient)
                    .append(t.proNumeral)
                    .append(t.hasPower() ? "^" + t.power : "")
                    .append("+ ");

        return sb.toString();
    }

    private static List<term> multiply(List<bracket> elements){

        StringBuilder o = new StringBuilder();
        List<term> terms = new ArrayList<>();

        for(int i = 0; i < elements.size(); i++) {
            for(int k = 0; k < elements.size(); k++) {
                // Take element n of bracket k multi by n of l
                // ∑ -> n*(∑ n->e.l->k.l)* neg ? -1 : 1
                // n->e.l
                // e->k.l

                if(i == k) break;
                for(int n = 0; n < elements.get(i).length(); n++){
                    for(int b = 0; b < elements.get(k).length(); b++) {

                         boolean isSquared = elements.get(i).p1.get(n) == elements.get(k).p1.get(b);

                        terms.add(new term(
                                elements.get(i).x1.get(n)*elements.get(k).x1.get(b),
                                (isSquared ? String.valueOf(elements.get(i).p1.get(n)) : elements.get(i).p1.get(n) + "" + elements.get(k).p1.get(b)),
                                isSquared ? 2 : 1));
                    }
                }
            }
        }

        return terms;
    }

    private static int bcAmt = 0;

    private static List<bracket> storeElements(String equation) {
        List<bracket> temp = new ArrayList<>();
        boolean it2 = false;
        for(int i = 0; i < equation.length(); i++){
            if(equation.charAt(i) == '(') {
                temp.add(sortElements(equation, i, it2));
                i += bcAmt;
                bcAmt = 0;
                it2 = true;
            }
        }

        return temp;
    }

    private static bracket sortElements(String equation, int StartIndex, Boolean it2) {
        List<Double> x1 = new ArrayList<>();
        List<Character> p1 = new ArrayList<>();
        String split = equation.substring(StartIndex + (it2 ? 1 : 2), util.until(StartIndex, equation, ')') + 1);
        int lastOpPos = 0;
        //TODO : major rewrite needed as it is only evaluating the first element in the bracket, but i dont have enough caffeine to do that now

        int elementCount = util.countOperator(0, split), lastNumLocation = 0, itterations = 0;
        boolean numIsNegative = false;

        for(int i = 0; i < split.length(); i++){

            if((util.isOperator(split.charAt(i)) || split.charAt(i) == ')')){
                String temp = split.substring(lastNumLocation, i);
                StringBuilder sb = new StringBuilder();

                for(int o = 0; o < temp.length(); o++){
                    if(Character.isDigit(temp.charAt(o)) || temp.charAt(o) == '.')
                        sb.append(temp.charAt(o));
                    else if (Character.isLetter(temp.charAt(o)))
                        p1.add(temp.charAt(o));
                }
                x1.add(Double.parseDouble(sb.toString()) * (numIsNegative ? -1 : 1) /*TODO implement negatives*/);

                lastNumLocation = i;
                itterations++;
                i++;
            }
            bcAmt = i;
        }


        return new bracket(x1, p1);
    }

    public record bracket(List<Double> x1, List<Character> p1) {
        int length() {
            return Math.max(x1.size(), p1.size());
        }
    };

    public record term(double coefficient, String proNumeral, double power) {
        //Default is 1
        boolean hasCoefficient() {return coefficient != 1;}
        //Default is _
        boolean hasProNumeral() {return !Objects.equals(proNumeral, "_");}
        //Default is 1
        boolean hasPower() {return power != 1;}
    }
}

