package com.example.gcalc.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    // Class list:
    /*
     * HandleStack - Used for evaluating the equation inside the stack
     * EquationList - Used for soring all the equations
     * EvalEquation - Used to evaluate the equation given the equation list
     * SimpleCalculations - Parsed into the HandleStack class to evaluate basic arithmetic
     * Constants - Used for referencing
     */
    public HandleStack handleStack;
    public EquationList equationList;
    public EvalEquation evalEquation;
    public SimpleArithmetic simpleArithmetic;
    public Constants constants;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Please input your equation, type help for a list of usages | type x or exit to leave");

        String equation = reader.readLine();
        if(equation == null || equation.equals(""))
            System.out.println("Please input an equation");
        else if(equation.equalsIgnoreCase("help")) {
            Help(reader);
            main(null);
        } else if (equation.equalsIgnoreCase("exit") || equation.equalsIgnoreCase("x"))
            return;

        assert equation != null;
        //checks if the equation is a predefined equation and calls the relevant methods
        if(HandleStack.handlePredefinedEquation(equation))
            return; // If the equation was a predefined equation the program will stop here to avoid conflicts
                    // with the later methods that evaluate the equation

        try {
            double result = HandleStack.evaluate(equation);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void Help(BufferedReader reader) throws IOException {
        System.out.println("To view available constants, type 'c', \nto view available equations, type 'eq', \nFor prebuilt equations types, type 'ty' \nto exit type 'x'");
        String type = reader.readLine();

        switch (type) {
            case "eq" -> System.out.println("The equations are:\nx + x (addition); \nx - x (subtraction)");
            case "ty" ->
                    System.out.println("Prebuilt Equations are: \nCircumference of a circle (2πr) \"cir0\"\nArea of a circle (πr^2) \"cir1\"");
            case "c" ->
                    System.out.println("To use predefined constants type what is in the place of the \"\nPredefined Constants are: \n\"pi\": 3.14.. (π)\n\"e\": 2.72..\n\"_m\": 1.2566 * 10^-6 (µ0) Magnetic flux constant in a vacuum");
            default -> {
            }
        }
    };

    public static double tfEval(String equation) {
        return 0;
    }
}



