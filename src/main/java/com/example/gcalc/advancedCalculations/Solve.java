package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.Arrays;

public class Solve {

    public static boolean has2Sol = false;

    public static String sol2 = "";
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
                has2Sol = true;
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
                    sol2 = " " + root1 + ", " + root2;
                    return root1;
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
                int c1 = util.untilOperator(1, equation);
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
                has2Sol = true;
                // Splits the equitation into a string array to be compared
                int splitPoint = util.until(0, equation, ';');
                String[] sim = {equation.substring(0, splitPoint), equation.substring(splitPoint + 1)};

                // Equation cleaning
                if(sim[0].isEmpty() || sim[1].isEmpty())
                    throw new IllegalArgumentException("The equation was not recognised");

                /*
                * Plan :
                * Split equations into a_1 b_1 c_1 a_2 b_2 c_2
                * Then use determinant multiplication to evaluate them
                *
                * a1x |+-| b1y = c1
                * a2x |+-| b2y = c2
                *
                *
                *    |c1 b1|
                *    |c2 b2|    c1*b2 - c2*b2
                * x = ------ = ---------------
                *    |a1 b1|    a1*b2 - a2*b1
                *    |a2 b2|
                *
                *    |a1 c1|
                *    |a2 c2|    a1*c2 - a2*c1
                * y = ------ = ---------------
                *    |a1 b1|    a1*b2 - a2*b1
                *    |a2 b2|
                *
                * */

                double[] elements = new double[6];
                Arrays.fill(elements, 1.0);
                int element = 0;
                boolean isNegative = false;
                for (String s : sim) {
                    for (int i = 0; i < s.length(); i++) {
                        if (Character.isDigit(s.charAt(i))) {
                            if(i - 1 >= 0)
                                isNegative = s.charAt(i-1) == '-';
                            StringBuilder temp = new StringBuilder();

                            while (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') {
                                temp.append(s.charAt(i));
                                i++;
                                if(i >= s.length())
                                    break;
                            }
                            elements[element] = Double.parseDouble(temp.toString()) * (isNegative ? -1 : 1);
                            element++;
                            isNegative = false;
                        }
                        if(element == 0 && Character.isLetter(s.charAt(i)))
                            element++;
                    }
                }
                double divisor = (elements[0] * elements[4] - elements[3] * elements[1]),
                        x = (elements[2] * elements[4] - elements[5] * elements[1]) / divisor,
                        y = (elements[0] * elements[5] - elements[3] * elements[2]) / divisor;

                sol2 = "(x = " + x + "; y = " + y + ")";
                return x;

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
