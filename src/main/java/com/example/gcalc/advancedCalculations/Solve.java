package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;

public class Solve {
    public static double solve(String rawEquation) {
        // Types of equations that are supported view solveSupported.md

        String equation = rawEquation.substring(util.until(0, rawEquation, '(') + 1, rawEquation.length() - 3);
        char variable = rawEquation.charAt(rawEquation.length() - 2);
        char var2 = rawEquation.charAt(rawEquation.length() - 3);
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
        } else if (equation.contains(";")) {
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
                    return Math.log(c - a) * (b / a); // TODO: needs to be reworked.
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
                // Splits the equitation into a string array to be compared
                int splitPoint = util.until(0, equation, ';');
                String[] sim = {equation.substring(0, splitPoint - 1), equation.substring(splitPoint + 1)};

                // Equation cleaning
                if(sim[0].isEmpty() || sim[1].isEmpty())
                    throw new IllegalArgumentException("The equation was not recognised");



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

record varType(char type, int power, int posEq, int posIn) {
}
