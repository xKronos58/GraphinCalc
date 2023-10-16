package com.example.gcalc.advancedCalculations;

public class Solve {


    public static double[] solve(String eq) {
        String equation = eq.substring(5, eq.length() - 4);
        char solvable = eq.charAt(eq.length() - 3);

        if(equation.length() == 0 || !Character.isLetter(solvable))
            throw new IllegalArgumentException("Equation was invalid");

        double[] sol = FindType(equation, solvable).solveEquation(new double[]{});

        return sol;
    }

    public static EquationType FindType(String equation, char solve) {
        return null;
    }


    public enum EquationType {
        LINEAR {
            @Override
            public double[] solveEquation(double[] coefficients) {
                double a = coefficients[0];
                double b = coefficients[1];
                if (a != 0) {
                    return new double[] {(-b / a)};
                } else {
                    throw new ArithmeticException("No solution for this linear equation.");
                }
            }
        },

        QUADRATIC {
            @Override
            public double[] solveEquation(double[] coefficients) {
                double a = coefficients[0];
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
                }
            }
        },

        BASIC {
            @Override
            public double[] solveEquation(double[] coefficients) {
                return new double[] {};
            }
        };

        public abstract double[] solveEquation(double[] coefficients);
    }


}
