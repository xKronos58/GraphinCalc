package com.example.gcalc.advancedCalculations;

import java.util.ArrayList;
import java.util.List;

public class Solve {
    public static double solve(String rawEquation) {
        // Types of equations that are supported view solveSupported.md

        String equation = rawEquation.substring(5, rawEquation.length() - 4);
        char variable = rawEquation.charAt(rawEquation.length() - 2);

        switch (findEquationType(equation)) {
            case LINEAR -> {
                return equationSolutions.LINEAR(equation, variable);
            } case QUADRATIC -> {
                return equationSolutions.QUADRATIC(equation, variable);
            } case POLYNOMIAL -> {
                return equationSolutions.POLYNOMIAL(equation, variable);
            } case EXPONENTIAL -> {
                return equationSolutions.EXPONENTIAL(equation, variable);
            } case LOGARITHMIC -> {
                return equationSolutions.LOGARITHMIC(equation, variable);
            } case TRIGONOMETRIC -> {
                return equationSolutions.TRIGONOMETRIC(equation, variable);
            } case SIMULTANEOUS -> {
                return equationSolutions.SIMULTANEOUS(equation, variable);
            } case OTHER -> {
                return equationSolutions.OTHER(equation, variable);
            } case INEQUALITY -> {
                return equationSolutions.INEQUALITY(equation, variable);
            } case PARAMETRIC -> {
                return equationSolutions.PARAMETRIC(equation, variable);
            } default -> {
                return 0.0; // This will never be hit, is just there to silence an error
            }
        }
    }

    public static Equation findEquationType(String equation) {
        if (equation.matches("[-+]?[0-9]*x\\s*[-+]?\\s*[0-9]+\\s*=\\s*0")) {
            return Equation.LINEAR;
        } else if (equation.matches("[-+]?[0-9]*x\\^2\\s*[-+]?\\s*[0-9]*x\\s*[-+]?\\s*[0-9]+\\s*=\\s*0")) {
            return Equation.QUADRATIC;
        } else if (equation.matches("ALKSDJSANKLD" /* TODO: Figure out how to determine polynomial*/)) {
            return Equation.POLYNOMIAL;
        } else if (equation.contains("e^(")) {
            return Equation.EXPONENTIAL;
        } else if (equation.contains("log(")) {
            return Equation.LOGARITHMIC;
        } else if (equation.contains("sin(") || equation.contains("cos(") || equation.contains("tan(")){
            return Equation.TRIGONOMETRIC;
        } else if (equation.contains("\n") || equation.contains(";")) {
            return Equation.SIMULTANEOUS;
        } else if (equation.contains("<") || equation.contains(">") || equation.contains("<=") || equation.contains(">=")) {
            return Equation.INEQUALITY;
        } else if (equation.matches("[xy]=.*")) {
            return Equation.PARAMETRIC;
        } else {
            return Equation.OTHER;
        }
    }

    public enum Equation {
        LINEAR,
        QUADRATIC,
        POLYNOMIAL,
        EXPONENTIAL,
        LOGARITHMIC,
        TRIGONOMETRIC,
        SIMULTANEOUS, /* HOW THE FUCK DO YOU SPELL IT??? */
        OTHER,
        INEQUALITY,
        PARAMETRIC;
    }
}

class equationSolutions {
    public static Double LINEAR(String equation, char variable) {
        return 0.0;
    }
    public static Double QUADRATIC(String equation, char variable) {
        return 0.0;
    }
    public static Double POLYNOMIAL(String equation, char variable) {
        return 0.0;
    }
    public static Double EXPONENTIAL(String equation, char variable) {
        return 0.0;
    }
    public static Double LOGARITHMIC(String equation, char variable) {
        return 0.0;
    }
    public static Double TRIGONOMETRIC(String equation, char variable) {
        return 0.0;
    }
    public static Double SIMULTANEOUS(String equation, char variable) {
        return 0.0;
    }
    public static Double OTHER(String equation, char variable) {
        return 0.0;
    }
    public static Double INEQUALITY(String equation, char variable) {
        return 0.0;
    }
    public static Double PARAMETRIC(String equation, char variable) {
        return 0.0;
    }
}

class _Solve {
    public static List<varType> vars = new ArrayList<>();
    public static List<Integer> power = new ArrayList<>();

    public static double[] solve(String eq) {

        String equation = eq.substring(5, eq.length() - 4);
        char solvable = eq.charAt(eq.length() - 3);

        if(equation.length() == 0 || !Character.isLetter(solvable))
            throw new IllegalArgumentException("Equation was invalid");


//        return FindType(equation, solvable).findType(new double[]{});
        return conv(FindType(eq, solvable)).solveEquation(eq, vars, power);
    }

    private static SolveEquationType conv(EquationType et) {
        switch (et) {
            case LINEAR -> {
                return SolveEquationType.LINEAR;
            }
            case POLYNOMIAL -> {
                return SolveEquationType.POLYNOMIAL;
            }
            case QUADRATIC -> {
                return SolveEquationType.QUADRATIC;
            }
            default -> {
                return SolveEquationType.OTHER;
            }
        }
    }
    public static EquationType FindType(String equation, char solve) {
        int posIn = 0;

        for(int i = 0; i < equation.length(); i++){
            if(Character.isLetter(equation.charAt(i)) && equation.charAt(i) == solve) {
                // First check if it is to the power of something. Important for equations like quadratic
                posIn++;
                char c = i + 2 < equation.length() && equation.charAt(i + 1) == '^' ? equation.charAt(i + 2) : 1;
                vars.add(new varType(equation.charAt(i), c, i, posIn));
                power.add((int) c);
            }
        }

        return evalType(vars, power);
    }

    public static EquationType evalType(List<varType> varsTypes, List<Integer> power) {
        if (varsTypes.size() == 2 && power.size() == 2) {
            if (power.get(0) == 2 && power.get(1) == 1) {
                return EquationType.QUADRATIC;
            } else if (power.get(0) == 1) {
                return EquationType.LINEAR;
            } else {
                return EquationType.BASIC;
            }
        } else if (varsTypes.size() > 2) {
            return EquationType.POLYNOMIAL;
        } else {
            return EquationType.OTHER;
        }
    }

/*    public static EquationType _FindType(String equation, char solve) {

        // Find numbers, variables, operators and equals
        // | I know that this is a large amount of different stacks, i hope to simplify it once the logic is sound
        Stack<Double[]> coefficients = new Stack<>();
        Stack<proneumerals> pronemuerals = new Stack<>();
        Stack<Character> operators = new Stack<>();
        boolean rightHandSide = false, lastWasCoefficient = false;
        Stack<Double[]> solveCoefficients = new Stack<>();
        Stack<proneumerals> solvePronemuerals = new Stack<>();
        Stack<Character> solveOperators = new Stack<>();

        for(int i = 0; i < equation.length(); i++){
            if (equation.charAt(i) == '=')
                rightHandSide = true;
            else if(Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.'){
                StringBuilder num = new StringBuilder();
                while(Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.') {
                    num.append(equation.charAt(i));
                    i++;
                }
                if(!rightHandSide)
                    coefficients.push(new Double[]{ Double.parseDouble(num.toString()), (double) i });
                else
                    solveCoefficients.push(new Double[]{ Double.parseDouble(num.toString()), (double) i });
                lastWasCoefficient = true;
            }
            else if (Character.isLetter(equation.charAt(i))) {
                proneumerals item = new proneumerals(equation.charAt(i), lastWasCoefficient, i);
                if (!rightHandSide)
                    pronemuerals.push(item);
                else
                    solvePronemuerals.push(item);
                lastWasCoefficient = false;
            }
        }


        return EquationType.FindType;
    }*/

    public enum EquationType {
        QUADRATIC,
        POLYNOMIAL,
        LINEAR,
        BASIC,
        OTHER;
    }

    public enum SolveEquationType {
        LINEAR {
            @Override
            public double[] solveEquation(String equation, List<varType> proNumerals, List<Integer> powers) {

                /*double a = coefficients[0];
                double b = coefficients[1];
                if (a != 0) {
                    return new double[] {(-b / a)};
                } else {
                    throw new ArithmeticException("No solution for this linear equation.");
                }*/
                return new double[] {};
            }
        },

        QUADRATIC {
            @Override
            public double[] solveEquation(String equation, List<varType> proNumerals, List<Integer> powers) {
               /* double a = coefficients[0];
                double b = coefficients[1];
                double c = coefficients[2];

                double discriminant = b * b - 4 * a * c;
                if (discriminant > 0) {
                    double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                    return new double[] {root1, root2};
                    // You may want to return both roots or handle other cases as needed
                } else if (discriminant == 0) {
                    return new double[] {(-b / (2 * a))};
                } else {
                    throw new ArithmeticException("No real solutions for this quadratic equation.");
                }*/
                return new double[] {};
            }
        },

        POLYNOMIAL {
            @Override
            public double[] solveEquation(String equation, List<varType> proNumerals, List<Integer> powers) {
                return new double[] {};
            }
        },
        OTHER {
            @Override
            public double[] solveEquation(String equation, List<varType> proNumerals, List<Integer> powers) { return new double[] {};}
        };

        public abstract double[] solveEquation(String equation, List<varType> proNumerals, List<Integer> powers);
    }
}

record varType(char type, int power, int posEq, int posIn) {
}
