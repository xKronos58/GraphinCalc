package com.example.gcalc.Calculator;

import java.io.IOException;
import java.util.Stack;

public class HandleStack {
    public static boolean handlePredefinedEquation(String equation) throws IOException {

        switch (equation) {
            case "mfd0", "mfd1", "mfd2" -> {
                System.out.printf("The magnetic flux density is "
                    + EquationList.LoadMFD(equation.charAt(equation.length() - 1))
                    + (equation.charAt(equation.length() - 1) == '0' ? "T\n"
                    : (equation.charAt(equation.length() - 1) == '1' ? "m\n" : "A\n")));
                return true; }
            case "spd0" -> {
                System.out.println("The speed is " + EquationList.speed(equation.charAt(equation.length() - 1)) + "m/s");
                return true; }
            case "mas0" -> {
                System.out.println("The density of the object is " + EquationList.mass(equation.charAt(equation.length() - 1)) + " kg/m^3");
                return true; }
            case "sop0" -> {
                System.out.println("The radius of the particle is " + EquationList.speedOfParticleInVacuum(equation.charAt(equation.length() - 1)) + " m");
                return true; }
            case "conv" -> {
                System.out.println("Conversion is " + SimpleArithmetic.Convert());
                return true;
            }
            case "tev0" -> {
                System.out.println("The terminal velocity is " + EquationList.terminalVelocity());
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public static double evaluate(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                i--; // Move back one step to account for the loop increment
                operandStack.push(Double.parseDouble(operand.toString()));
            } else if (currentChar == 'r' && expression.charAt(i + 1 < expression.length() ? i + 1 : 0) == '('){
                i += 2; // Skips over the "r(" to grab the number and return the sqrt(x) version
                StringBuilder operand = new StringBuilder();
                while(i < expression.length() && expression.charAt(i) != ')') { // Checks if it is inside the sqrt expression
                    operand.append(expression.charAt(i));
                    i++;
                }                       //  vv Note this is a recursive call although should not run into issues as long as r(r()) is not called.
                operandStack.push(Math.sqrt(evaluate(operand.toString()))); // Takes the returned number and pushes the sqrt version   | Note r(r(x)) does not
                //                          ^^ Evaluates the number to allow for interpolated root equations ex : r(2*8) will return 4 | Work, use cases are minimal
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.pop(); // Pop the '('
            } else if (isOperator(currentChar)) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(currentChar)) {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.push(currentChar);
            } else if (isConst(currentChar, i + 1 == expression.length() ? '0' : expression.charAt(i + 1))){
                switch (currentChar) {
                    case 'e' -> operandStack.push(Constants.e);
                    case 'p' -> { if(expression.length() >= i + 1 && expression.charAt(i + 1) == 'i') operandStack.push(Constants.pi); }
                    case '_' -> { if(expression.length() >= i + 1 && expression.charAt(i + 1) == 'm') operandStack.push(Constants.pico0); }
//                    case 'l' -> { if(expression.charAt(i + 1) == '(') {
//                        int j = i;
//                        while (j < expression.length() && expression.charAt(j) != ')') {
//                            j++;
//                        }
//                        operandStack.push(Math.log(Double.parseDouble(expression.substring(i, j))));
//                        i = j;
//                    }}
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack);
        }

        if (operandStack.size() == 1) {
            return operandStack.pop();
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    public static double evaluateGraph(String equation, int point) {
        int i = 0;
        while(equation.charAt(i) != 'x')
            i++;

        String subX = equation.substring(2, i) + point + equation.substring(i + 1);

        System.out.println(subX);

        return evaluate(subX);
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == 'r';
    }

    private static boolean isConst(char c, char c1) {
        return c == 'e' || (c == 'p' && c1 == 'i') || (c == '_' && c1 == 'm')/* || c == 'l'*/;
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '^')
            return 3;
        return 0;
    }

    private static void performOperation(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char operator = operatorStack.pop();
        // Check if the operator stack is empty before accessing it
        if (operandStack.isEmpty()) {
            if (operator == '-') {
                // Unary negation
                operandStack.push(0.0); // Push 0 for negation
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
        }
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();

        switch (operator) {
            case '+' -> operandStack.push(operand1 + operand2);
            case '-' -> {
                // Check if the '-' is a binary subtraction or a unary negation
                if (operatorStack.isEmpty() || operatorStack.peek() == '(') {
                    // Unary negation
                    operandStack.push(-operand2);
                } else {
                    // Binary subtraction
                    operandStack.push(operand1 - operand2);
                }
            }
            case '*' -> operandStack.push(operand1 * operand2);
            case '/' -> {
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                operandStack.push(operand1 / operand2);
            }
            case '^' -> operandStack.push(Math.pow(operand1, operand2));
        }
    }
}
