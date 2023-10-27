package com.example.gcalc.Calculator;

import com.example.gcalc.GCController;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Stack;

public class HandleStack {
    public static boolean handlePredefinedEquation(String equation) throws IOException {

        /*switch (equation) {
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
        }*/
        return false;
    }

    public static double evaluate(String _expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        String expression = _expression.toLowerCase();

        boolean lastTokenWasOperator = true, negative = false;

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar) || (currentChar == '.')) {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                i--; // Move back one step to account for the loop increment
                double number = Double.parseDouble(operand.toString());
                if(negative) {
                    operandStack.push(number * -1);
                    break;
                }

                operandStack.push(number);
                lastTokenWasOperator = false;
            } else if (isOperator(currentChar)) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(currentChar)) {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.push(currentChar);
                lastTokenWasOperator = true;
            } else if (currentChar == '-') {
                if (lastTokenWasOperator) {
                    negative = true;
                } else {
                    while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(currentChar)) {
                        performOperation(operandStack, operatorStack);
                    }
                    operatorStack.push(currentChar);
                }
                lastTokenWasOperator = true;
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
                lastTokenWasOperator = true;
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.pop(); // Pop the '('
                lastTokenWasOperator = false;
            } else if (currentChar == 'r' && (i + 1) < expression.length() && expression.charAt(i + 1) == '(') {
                i += 2; // Skip "r("
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && expression.charAt(i) != ')') {
                    operand.append(expression.charAt(i));
                    i++;
                }
                operandStack.push(Math.sqrt(evaluate(operand.toString())));
                lastTokenWasOperator = false;
            } else if (i + 4 < expression.length() && (
                    (currentChar == 's' && expression.charAt(i + 1) == 'i')
                            || (currentChar == 'c' && expression.charAt(i + 1) == 'o')
                            || (currentChar == 't' && expression.charAt(i + 1) == 'a')
                            || (currentChar == 'l' && expression.charAt(i + 1) == 'o')
                            || (currentChar == '_' && expression.charAt(i + 1) == 'l'))) {
                int o = i;
                i += 4; // Skips over "sin(" to grab the number and return the sqrt(x) version
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && expression.charAt(i) != ')') {
                    operand.append(expression.charAt(i));
                    i++;
                }
                switch (expression.substring(o, o + 3)) {
                    case "sin" -> operandStack.push(Math.sin(evaluate(operand.toString())));
                    case "cos" -> operandStack.push(Math.cos(evaluate(operand.toString())));
                    case "tan" -> operandStack.push(Math.tan(evaluate(operand.toString())));
                    case "log" -> operandStack.push(Math.log10(evaluate(operand.toString())));
                    case "_ln" -> operandStack.push(Math.log(evaluate(operand.toString())));

                }
                lastTokenWasOperator = false;
            } else if (isConst(currentChar, i + 1 == expression.length() ? '0' : expression.charAt(i + 1))) {
                final boolean c = expression.length() >= i + 1 && expression.charAt(i + 1) == 'c';
                switch (currentChar) {
                    case 'e' -> operandStack.push(Constants.e);
                    case 'p' -> {
                        if (expression.length() >= i + 1 && expression.charAt(i + 1) == 'i')
                            operandStack.push(Constants.pi);
                        else if (expression.length() >= i + 1 && (expression.charAt(i + 1) == 'h'))
                            operandStack.push(Constants.phi);
                    } case '_' -> {
                        if (expression.length() >= i + 1 && expression.charAt(i + 1) == 'm')
                            operandStack.push(Constants.pico0);
                    } case 't' -> {
                        if(expression.length() >= i + 1 && expression.charAt(i + 1) == 'u')
                            operandStack.push(Constants.tau);
                    } case 's' -> {
                        if(expression.length() >= i + 1 && expression.charAt(i + 1) == 'g')
                            operandStack.push(Constants.superGoldenRatio);
                    } case 'm' -> {
                        if(c)
                            operandStack.push(Constants.superGoldenRatio);
                    } case 'k' -> {
                        if(expression.length() >= i + 1 && expression.charAt(i + 1) == 'b')
                            operandStack.push(Constants.KBC);
                    } case 'w' -> {
                        if(c)
                            operandStack.push(Constants.WC);
                    }
                }
                lastTokenWasOperator = false;
            }
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack);
        }

        if (operandStack.size() == 1) {
            return operandStack.pop();
        } else {
            GCController.invalidEquation = true;
            return -0.0;
        }
    }


    public static double evaluateGraph(String equation, double point) {
        StringBuilder finalEquation = new StringBuilder();
        int i = 0, j = 0;
        while(i < equation.length()) {
            if(equation.charAt(i) == 'x')
            {
                finalEquation.append(equation, j, i);
                finalEquation.append(point);
                j = i + 1;
            }
            i++;
        }

        return evaluate(String.valueOf(finalEquation));
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '*' || c == '/' || c == '^';
    }

    private static boolean isConst(char c, char c1) {
        return c == 'e'                    // e
                || (c == 'p' && c1 == 'i') // π
                || (c == '_' && c1 == 'm') // µ0
                || (c == 't' && c1 == 'u') // tau
                || (c == 'p' && c1 == 'h') // phi
                || (c == 's' && c1 == 'g') // sgr
                || (c == 'm' && c1 == 'c') // CCHL
                || (c == 'k' && c1 == 'b') // KBC
                || (c == 'w' && c1 == 'c') // WC
                ;
    }

    private static int precedence(char operator) {
        if (operator == '+') {
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

        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();

        switch (operator) {
            case '+' -> operandStack.push(operand1 + operand2);
            case '-' -> operandStack.push(operand1 - operand2);
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