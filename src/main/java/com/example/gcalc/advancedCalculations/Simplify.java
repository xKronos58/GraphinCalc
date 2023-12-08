package com.example.gcalc.advancedCalculations;

import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Simplify {

    public static equation simplifyRaw(String rawEquation) {
        rawEquation = rawEquation.replaceAll("ans", String.valueOf(HandleStack.ans));
        equationTerms equation = stringEquationToTerm(rawEquation);

        return new equation("k");
    }

    public static equation simplifyToQuadratic(String equation) {
        // Take the equation 8x+4-9x=4x^2 We want to convert it into the form ax^2+bx+c=0
        // Step 1 make it = 0; this can be done by subtracting the second half of the equation from the equals side
        Simplify.equationSplit sp = new equationSplit(equation.substring(0, util.until(0, equation, '=')), equation.substring(util.until(0, equation, '=') + 1));
        Simplify.equation zero = eqZero(sp);

        List<term> _terms = parseStringTerm(equation, zero);
        StringBuilder finalEq = new StringBuilder();

        for(int a = 0; a < _terms.size(); a++) {
            for(int b = 0; b < _terms.size(); b++) {
                if(a == b) break;

                if(canSimplify(_terms.get(a), _terms.get(b)))
                    finalEq.append(operateTerms(_terms.get(a), _terms.get(b)));

            }
        }


        return new equation("k");
    }

    public static boolean isCoefficient(String eq, int startIndex, int ci) {
        return !util.hasPower(startIndex, eq) || util.until(startIndex, eq, '^') > ci;
    }

    private static List<term> parseStringTerm(String equation, equation zero) {
        List<term> temp = new ArrayList<>();
        int lastOp = 0;
        for(int i = 0; i < zero.equation.length() - 2; i++)
            if(util.isOperator(zero.equation.charAt(i))) {

                String e = zero.equation.substring(lastOp, i);
                StringBuilder c = new StringBuilder(), pn = new StringBuilder(), po = new StringBuilder(), o = new StringBuilder();

                for(int k = 0; k < e.length(); k++)
                    if(Character.isDigit(e.charAt(k)) && isCoefficient(equation, 0, i)) c.append(e.charAt(k));
                    else if (Character.isLetter(e.charAt(k))) pn.append(e.charAt(k));
                    else if (Character.isDigit(k) && !isCoefficient(equation, 0, i)) po.append(e.charAt(k));

                temp.add(new term(Double.parseDouble(c.toString()), pn.toString(), Double.parseDouble(po.isEmpty() ? "1" : po.toString()), zero.equation.charAt(i)));
            }

        return temp;
    }

    private static boolean canSimplify(term a, term b) {
        return a.proNumeral.equals(b.proNumeral) && a.power == b.power;
    }

    private static String operateTerms(term a, term br) {
        double b = br.operator == '-' ? br.coefficient * -1 : br.coefficient;
        double coefficient = switch (a.operator) {
            case '+' -> (a.coefficient + b);
            case '-' -> (a.coefficient - b);
            default -> 1;
        };
        return ";";
    }

    private static String simplifyExpandedTerm(List<term> terms) {
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

    public static equation eqZero(equationSplit eqs) {
        return new equation(eqs.left + "-(" + eqs.right + ")=0");
    }

    public static String equationToString(equation e) {
        return e.equation;
    }

    private record term(double coefficient, String proNumeral, double power, char operator) {
        //Default is 1
        boolean hasCoefficient() {return coefficient != 1;}
        //Default is _
        boolean hasProNumeral() {return !Objects.equals(proNumeral, "_");}
        //Default is 1
        boolean hasPower() {return power != 1;}
    }

    // Redone version of parseStringToTerm() as this handles more information
    public static equationTerms stringEquationToTerm(String equation) {
        int lastOp = util.untilNum(0, equation), eqPos = util.until(0, equation, '=');
        List<term> left = new ArrayList<>(), right = new ArrayList<>();
        for(int i = 0; i < equation.length(); i++){
            String temp;
            if(util.isOperator(equation.charAt(i))) {
                temp = equation.substring(lastOp, i);
            } else continue;

            if(i > eqPos)
                right.add(stringTermToTerm(temp));
            else
                left.add(stringTermToTerm(temp));
        }

        return new equationTerms(left, right);
    }
    private static term stringTermToTerm(String term) {
        StringBuilder coefficient = new StringBuilder(), power = new StringBuilder();
        char operator = '_';
        StringBuilder proNumeral = new StringBuilder();
        for(int i = 0; i < term.length(); i++){
            if(util.isOperator(term.charAt(i)))
                operator = term.charAt(i);
            else if(Character.isLetter(term.charAt(i))) {
                while (Character.isLetter(term.charAt(i))) {
                    proNumeral.append(term.charAt(i));
                    if(i++ == term.length()) break;
                }
                i--;
            } else if (Character.isDigit(term.charAt(i))) {
                if(i == 0 || term.charAt(i - 1) != '^'){ // coefficient
                    while(Character.isDigit(term.charAt(i))){
                        coefficient.append(term.charAt(i));
                        if(i++ == term.length()) break;
                    }
                    i--;
                } else { // Power
                    while(i < term.length() && Character.isDigit(term.charAt(i))) {
                        power.append(term.charAt(i));
                        if(i++ == term.length()) break;
                    }
                }
            }
        }
        if(coefficient.toString().isEmpty())
            coefficient.append("1");
        else if (power.toString().isEmpty())
            power.append("1");
        else if (proNumeral.toString().isEmpty())
            proNumeral.append("_");


        return new term(Double.parseDouble(coefficient.toString()), proNumeral.toString(), Double.parseDouble(power.toString()), operator);
    }

    public record equation(String equation) {}
    public record equationSplit(String left, String right) {
    }
    public record equationTerms(List<term> left, List<term> right) {};


}
