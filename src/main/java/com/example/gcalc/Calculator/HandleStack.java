package com.example.gcalc.Calculator;

import com.example.gcalc.GCController;
import com.example.gcalc.advancedCalculations.diffirentiate;
import com.example.gcalc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HandleStack {
    public static List<Variable> variables = new ArrayList<>();
    public static double ans = 0.0;

    public static double evaluate(String _expression) {
        if(!variables.isEmpty())
            _expression = replaceVariables(_expression);
        _expression = _expression.replace("ans", String.valueOf(ans));

        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        String expression = _expression.toLowerCase();

        boolean lastTokenWasOperator = true, negative = false;

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar) || (currentChar == '.')) {
                StringBuilder operand = new StringBuilder(16);
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) { //TODO: Optimize
                    operand.append(expression.charAt(i));
                    i++;
                }
                i--; // Move back one step to account for the loop increment
                double number = Double.parseDouble(operand.toString()); //TODO: Optimize
                if (negative) {
                    negative = false;
                    operandStack.push(number * -1);
                    if (i + 1 >= expression.length()) break;
                    else continue;
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
                operatorStack.pop();
                lastTokenWasOperator = false;
            } else if (currentChar == '|') {
                if (expression.charAt(i + 1) == '[') { // matrix
                    return 0.0; /// OGGOLY BOOGOLY
                } else {
                    int end = util.until(i += 1, expression, '|');
                    operandStack.push(Math.abs(evaluate(expression.substring(i, end))));
                    i = end;
                }
            } else if (expression.length() > i + 2 && (currentChar == 'i' && expression.charAt(i + 1) == 'n' && expression.charAt(i + 2) == 'f')) {
                operandStack.push(Double.POSITIVE_INFINITY);
            } else if (currentChar == 'r' && (i + 1) < expression.length() && expression.charAt(i + 1) == '(') {
                i += 2; // Skip "r("
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && expression.charAt(i) != ')') {
                    operand.append(expression.charAt(i));
                    i++;
                }
                operandStack.push(Math.sqrt(evaluate(operand.toString())));
                lastTokenWasOperator = false;
            } else if (expression.length() > i + 8 && (currentChar == 'c' && expression.charAt(i + 1) == 'b' && expression.charAt(i + 2) == 'r')) { //cbrt[]() (len = 8)
                // First grab contents of [x]
                int expEnd = util.until(i, expression, '}');
                String rootPow = expression.substring(util.until(i, expression, '[') + 1, util.until(i, expression, ']') - 1);
                String root = expression.substring(util.until(i, expression, '{') + 1, expEnd);
                i += expEnd;
                operandStack.push(Math.pow(HandleStack.evaluate(root), 1 / Double.parseDouble(rootPow)));
            } else if (expression.startsWith("derive")) {
                System.out.println(diffirentiate.derive(expression));
                return 0.0;
            } else if (expression.startsWith("arc", i)) {
                switch (expression.substring(i, i + 6)) {
                    case "arcsin" -> operandStack.push(Math.asin(evaluate(expression.substring(i + 7, util.until(i + 7, expression, ')')))));
                    case "arccos" -> operandStack.push(Math.acos(evaluate(expression.substring(i + 7, util.until(i + 7, expression, ')')))));
                    case "arctan" -> operandStack.push(Math.atan(evaluate(expression.substring(i + 7, util.until(i + 7, expression, ')')))));
                    default -> util.errorMessage("Invalid arc function", "Invalid arc function");
                }
                lastTokenWasOperator = false;
                i+= util.until(i + 7, expression, ')');
            } else if (i + 4 < expression.length() && (
                    (currentChar == 's' && expression.charAt(i + 1) == 'i')
                            || (currentChar == 'c' && expression.charAt(i + 1) == 'o')
                            || (currentChar == 't' && expression.charAt(i + 1) == 'a')
                            || (currentChar == 'l' && expression.charAt(i + 1) == 'o')
                            || (currentChar == '_' && expression.charAt(i + 1) == 'l'))) {
                int o = i;
                i += 4; // Skips over "sin()" to grab the number and return the sqrt(x) version
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && expression.charAt(i) != ')') {
                    operand.append(expression.charAt(i));
                    i++;
                }
                switch (expression.substring(o, o + 3)) { //TODO: Optimize
                    case "sin" -> operandStack.push(Math.sin(evaluate(operand.toString())));
                    case "cos" -> operandStack.push(Math.cos(evaluate(operand.toString())));
                    case "tan" -> operandStack.push(Math.tan(evaluate(operand.toString())));
                    case "log" -> operandStack.push(Math.log10(evaluate(operand.toString())));
                    case "_ln" -> operandStack.push(Math.log(evaluate(operand.toString())));
                }
                lastTokenWasOperator = false;
            } else if (expression.length() > i + 5 && (expression.charAt(i) == 'c' && expression.charAt(i + 1) == 'q')) {
                String StringValue = expression.substring(
                        util.until(i + 5, expression, ']') + 2,
                        util.until(i + 5, expression, '}'));
                double value = HandleStack.evaluate(StringValue);
                if(value > 0)
                    operandStack.push(Math.pow(value,
                            1 / Double.parseDouble(expression.substring(i + 5, util.until(i + 5, expression, ']')))));
                else
                    operandStack.push((Math.pow(value * -1,
                            1 / Double.parseDouble(expression.substring(i + 5, util.until(i + 5, expression, ']'))))) * -1);
                i = util.until(i+5, expression, '}');
            } else if (expression.length() > i + 8 && expression.startsWith("let[", i)) {
                variables.add(new Variable(expression.substring(i + 4, util.until(i + 4, expression, ']')), evaluate(expression.substring(util.until(i, expression, '=') + 1))));
                return evaluate(expression.substring(util.until(i, expression, '=') + 1));
            } else if (isConst(currentChar, i + 1 == expression.length() ? '0' : expression.charAt(i + 1))) {
                final boolean c = expression.length() > i + 1 && expression.charAt(i + 1) == 'c';
                boolean canPush = expressionCheck(i, expression);
                switch (currentChar) {
                    case 'e' -> operandStack.push(Constants.e);
                    case 'g' -> operandStack.push(Constants.gravity);
                    case 'p' -> {
                        if (canPush && expression.charAt(i + 1) == 'i')
                            operandStack.push(Constants.pi);
                        else if (canPush && (expression.charAt(i + 1) == 'h'))
                            operandStack.push(Constants.phi);
                        else if(canPush && expression.charAt(i + 1) == 'm')
                            operandStack.push(Constants.protonMass);
                        else if(canPush && expression.charAt(i + 1) == 'c')
                            operandStack.push(Constants.protonCharge);
                    } case '_' -> {
                        if (canPush && expression.charAt(i + 1) == 'm')
                            operandStack.push(Constants.pico0);
                    } case 't' -> {
                        if(canPush && expression.charAt(i + 1) == 'u')
                            operandStack.push(Constants.tau);
                    } case 's' -> {
                        if(canPush && expression.charAt(i + 1) == 'g')
                            operandStack.push(Constants.superGoldenRatio);
                    } case 'm' -> {
                        if(c)
                            operandStack.push(Constants.CCHL);
                    } case 'k' -> {
                        if(canPush && expression.charAt(i + 1) == 'b')
                            operandStack.push(Constants.KBC);
                    } case 'w' -> {
                        if(c)
                            operandStack.push(Constants.WC);
                    } case 'n' -> {
                        if (canPush && expression.charAt(i + 1) == 'm')
                            operandStack.push(Constants.neutronMass);
                    }
                }
                lastTokenWasOperator = false;
            }
        }

        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack); //TODO: Optimize
        }

        if (operandStack.size() == 1) {
            ans = operandStack.pop();
            return ans;
        } else {
            GCController.invalidEquation = true;
            return -0.0;
        }
    }

    public static boolean expressionCheck(int i, String expression) {
        return expression.length() >= i + 1;
    }


    public static double evaluateGraphX(String equation, double x) {
        return evaluate(equation.replace("x", Double.toString(x)));
    }

    public static double evaluateGraphY(String equation, double y) {
        return evaluate(equation.replace("y", Double.toString(y)));
    }

    public static String replaceVariables(String equation) {
        String newEquation = equation;
        for(Variable v : HandleStack.variables)
            newEquation = newEquation.replace(":" + v.name(), String.valueOf(v.value()));
        System.out.println(newEquation);
        return newEquation;
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
                || (c == 'g')              // gravity
                || (c == 'p' && c1 == 'm') // proton mass
                || (c == 'n' && c1 == 'm') // neutron mass
                || (c == 'p' && c1 == 'c') // proton charge
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

        if(operandStack.isEmpty() || operandStack.size() == 1) {
            util.errorMessage("Operator stack is empty", "Operator stack is empty");
            return;
        }

        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();

        switch (operator) {
            case '+' -> operandStack.push(operand1 + operand2);
            case '-' -> operandStack.push(operand1 - operand2);
            case '*' -> operandStack.push(operand1 * operand2);
            case '/' -> {
                if (operand2 == 0) throw new ArithmeticException("Division by zero");
                operandStack.push(operand1 / operand2);
            }
            case '^' -> {
                if(operand1 < 0 && operand2 < 1)
                    operandStack.push(Math.pow(operand1 * -1, operand2) * -1);
                else
                    operandStack.push(Math.pow(operand1, operand2));
            }
        }
    }



    public record Variable(String name, double value) { }
}