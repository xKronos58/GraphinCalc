package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;

public class Solve {
    public static double solve(String rawEquation) {
        // Types of equations that are supported view solveSupported.md

        String equation = rawEquation.substring(util.until(0, rawEquation, '(') + 1, rawEquation.length() - 3);
        char variable = rawEquation.charAt(rawEquation.length() - 2);
        Equation eqType = findEquationType(equation);
        System.out.println(eqType.toString());
        return eqType.solve(equation, variable);
    }

    public static Equation findEquationType(String equation) {
        if (equation.matches("[-+]?[0-9]*x\\s*[-+]?\\s*[0-9]+\\s*=\\s*[-+]?\\s*[0-9]+\n")) {
            return Equation.LINEAR;
        } else if (equation.matches("[-+]?[0-9]*x\\^2\\s*[-+]?\\s*[0-9]*x\\s*[-+]?\\s*[0-9]+\\s*=\\s*0")) {
            return Equation.QUADRATIC;
        } else if (equation.matches("ALKSDJSANKLD" /* TODO: Figure out how to determine polynomial*/)) {
            return Equation.POLYNOMIAL;
        } else if (equation.contains("e^")) {
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
        LINEAR {
            @Override
            public Double solve(String equation, char variable) {
                int x1 = util.until(0, equation, variable);
                double a, b;
                a = Double.parseDouble(equation.substring(0, x1));
                b = Double.parseDouble(equation.substring(x1, equation.length() - 1));
                if(a != 0)
                    return -b/a;
                else
                    throw new ArithmeticException("No real solutions for this Linear equation");
            }
        },
        QUADRATIC {
            @Override
            public Double solve(String equation, char variable) {
                // Split the equation into variables that can be put into the quadratic formula
                int x1 = util.until(0, equation, 'x'), x2 = util.until(x1, equation, 'x');
                double a, b, c;
                a = Double.parseDouble(equation.substring(0, x1));
                b = Double.parseDouble(equation.substring(x1+1, x2));
                c = Double.parseDouble(equation.substring(x2 + 1, equation.length() - 1));

                double discriminant = b * b - 4 * a * c;
                if (discriminant > 0) {
                    double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                    return root1;
                    //TODO handle second root
                } else if (discriminant == 0) {
                    return (-b / (2 * a));
                } else {
                    throw new ArithmeticException("No real solutions for this quadratic equation.");
                }
            }
        },
        POLYNOMIAL {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0; // Note this does not need to be written until a regex can be created for a polynomial function
            }
        },
        EXPONENTIAL {
            @Override
            public Double solve(String equation, char variable) {
                // Equation type a + e^(bx) = c
                int c1 = util.untilSymbol(1, equation);
                double a = Double.parseDouble(equation.substring(0, c1)), // Grabs the first value
                        b = Double.parseDouble(equation.substring(util.until(c1, equation, '(') + 1, util.until(c1, equation, variable) -1)), // Grabs the coefficient to the value inside the exponent
                        c = Double.parseDouble(equation.substring(util.until(0, equation, '=') + 1)); // Grabs the final value
                if(a > 0 && b > 0 && c >= 0) // Checks if a & b are non-zero and if c is non-negative
                    return Math.log(c - a) * (b / a); // TODO needs to be reworked.
                throw new ArithmeticException("There is no solution to this equation");
            }
        },
        LOGARITHMIC {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            }
        },
        TRIGONOMETRIC {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            }
        },
        SIMULTANEOUS {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            }
        },
        OTHER {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            }
        },
        INEQUALITY {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            }
        },
        PARAMETRIC {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            }
        };

        public abstract Double solve(String equation, char variable);
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
