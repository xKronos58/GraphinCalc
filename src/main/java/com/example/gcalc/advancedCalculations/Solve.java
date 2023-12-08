package com.example.gcalc.advancedCalculations;

import com.example.gcalc.Calculator.HandleStack;
import com.example.gcalc.util;
import javafx.scene.control.Alert;

import java.util.Arrays;

public class Solve {

    public static boolean has2Sol = false;

    public static String sol2 = "";
    public static char Var2 = 'y';
    public static double solve(String rawEquation) {
        // Types of equations that are supported view solveSupported.md

        String equation = rawEquation.substring(util.until(0, rawEquation, '(') + 1, rawEquation.length() - 3);
        equation = equation.replaceAll("ans", String.valueOf(HandleStack.ans));
        char[] vars = util.pullVars(equation, rawEquation); // vars[0] will always be the first variable.
        if(vars.length == 2) Var2 = vars[1];

        return findEquationType(equation, (equation.charAt(0) == 't')).solve((equation.charAt(0) == 't') ?
                equation.substring(util.until(0, equation, '(')+1, equation.length() - 1) : equation, vars[0]);
    }

    public static Equation findEquationType(String equation, boolean predefined) {

        if(predefined) {
            String type = equation.substring(0, util.until(0, equation, '('));
            return switch (type.toLowerCase()) {
                case "t=linear" -> Equation.LINEAR;
                case "t=quadratic" -> Equation.QUADRATIC;
                case "t=polynomial" -> Equation.POLYNOMIAL;
                case "t=exponential" -> Equation.EXPONENTIAL;
                case "t=logarithmic" -> Equation.LOGARITHMIC;
                case "t=trigonometric" -> Equation.TRIGONOMETRIC;
                case "t=simultaneous", "t=system" -> Equation.SIMULTANEOUS;
                case "t=inequality" -> Equation.INEQUALITY;
                case "t=parametric" -> Equation.PARAMETRIC;
                default -> throw new IllegalArgumentException(type + " is not supported" + unsupportedEquation(type));
            };
        }

        if (equation.matches("[-+]?[0-9]*x\\s*[-+]?\\s*[0-9]+\\s*=\\s*[-+]?\\s*[0-9]+\n"))
            return Equation.LINEAR;
         else if (equation.matches("[-+]?[0-9]*x\\^2\\s*[-+]?\\s*[0-9]*x\\s*[-+]?\\s*[0-9]+\\s*=\\s*0"))
            return Equation.QUADRATIC;
//         else if (equation.matches("" /* NOTE: POLYNOMIAL can not be defined in this context and thus */))
//            return Equation.POLYNOMIAL;        Must be specified with the t= function
         else if (equation.contains("e^"))
            return Equation.EXPONENTIAL;
         else if (equation.contains("log("))
            return Equation.LOGARITHMIC;
         else if (equation.contains("sin(") || equation.contains("cos(") || equation.contains("tan("))
            return Equation.TRIGONOMETRIC;
         else if (equation.contains(";"))
            return Equation.SIMULTANEOUS;
         else if (equation.contains("<") || equation.contains(">") || equation.contains("<=") || equation.contains(">="))
            return Equation.INEQUALITY;
         else if (equation.matches("[xy]=.*"))
            return Equation.PARAMETRIC;
         else if (equation.matches("|x|[><=[<=][>=]][0-9]]"))
             return Equation.ABSOLUTE_INEQUALITY;
         else
            return Equation.OTHER;

    }

    public static String unsupportedEquation(String eqType) {
        util.errorMessage(eqType + " is not supported, please view the docs for supported equation types", "Unsupported equation");
        return "";
    }

    public enum Equation {
        LINEAR {
            @Override
            public Double solve(String equation, char variable) {
                int x1 = util.until(0, equation, variable);
                double a, b;
                a = Double.parseDouble(equation.substring(0, x1));
                b = Double.parseDouble(equation.substring(util.untilNum(x1, equation)));
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

                if(!equation.substring(util.until(0, equation, '=')).equals("=0"))
                    equation = Simplify.equationToString(Simplify.simplifyToQuadratic(equation));
                // NOTE still no unexpanded recognition yet.

                System.out.println(equation);

                equation = equation.substring(0, equation.length() - 2); // removes "=0" as that is assumed later in the code
                // Split the equation into variables that can be put into the quadratic formula
                double[] coefficients = getCoefficients(equation);
                double a = coefficients[0], b = coefficients[1], c = coefficients[2];

                double discriminant = b * b - 4 * a * c;
                if (discriminant > 0) {
                    double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                    sol2 = " " + root1 + ", " + root2;
                    return root1;
                } else if (discriminant == 0) {
                    return (-b / (2 * a));
                } else {
                    sol2 = "NO REAL SOLUTIONS";
                    return -0.0;
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

                sol2 = String.format("(%s = %s); (%s = %s);",variable,x,Var2,x);
                return x;

            }
        },
        OTHER {
            @Override
            public Double solve(String equation, char variable) {
                return 0.0;
            } //TODO implement brute force method
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

                // First grab elements of the equation.


                return 0.0;
            }
        },
        ABSOLUTE_INEQUALITY {
            @Override
            public Double solve(String equation, char variable) {
                // First determine distance between the 2 absolute values
                return 0.0;
            }
        };

        public abstract Double solve(String equation, char variable);
    }

    public static double[] getCoefficients(String equation) {
        equation = equation.replaceAll(" ", ""); // Remove spaces

        String[] parts = equation.split("(?=[-+])"); // Split at + or - signs

        double a = 0, b = 0, c = 0;

        for (String part : parts)
            if (part.endsWith("x^2"))
                a += parseCoefficient(part);
            else if (part.endsWith("x"))
                b += parseCoefficient(part);
            else
                c += parseCoefficient(part);

        return new double[]{a, b, c};
    }

    public static double parseCoefficient(String term) {
        if(term.contains("^2"))
            term = term.replace("^2", "");

        if (term.contains("x")) {
            if (term.equals("x")) {
                return 1;
            } else if (term.equals("-x")) {
                return -1;
            } else {
                return Double.parseDouble(term.replace("x", ""));
            }
        }
        return Double.parseDouble(term);
    }

}
