package com.example.gcalc.advancedCalculations;

import com.example.gcalc.util;

import java.util.ArrayList;
import java.util.List;

public class diffirentiate {

    public static double derive(String equation) {
//        DerivativeType type = switch (equation.substring(7, util.until(7, equation, ','))) {
//            case "t=chain" -> DerivativeType.CHAIN_RULE;
//            case "t=other" -> DerivativeType.OTHER;
//            default -> null;
//        };

        DerivativeEquationType equationType = findDerivativeType(equation.substring(util.until(7, equation, ',') + 1, equation.length() - 1));

        return equationType.derive(equation);
    }

    private static DerivativeEquationType findDerivativeType(String equation) {
        if(equation.matches("\\\\(.+\\\\)\\\\s*\\\\+\\\\s*\\\\(.+\\\\)")) return DerivativeEquationType.ADDITIVE;
        else if (equation.matches("\\(.+\\)\\s*-\\s*\\(.+\\)")) return DerivativeEquationType.SUBTRACTIVE;
        else if (equation.matches("\\(.+\\)\\s*\\*\\s*\\(.+\\)")) return DerivativeEquationType.MULTIPLICATIVE;
        else if (equation.matches("\\(.+\\)\\s*/\\s*\\(.+\\)")) return DerivativeEquationType.DIVISIVE;
        else if (equation.matches("\\(.+\\)\\s*\\^\\s*\\(.+\\)")) return DerivativeEquationType.BRACKET;
        else if (equation.matches("\\(.+\\)\\s*\\^\\s*\\d+")) return DerivativeEquationType.EXPONENTIAL;
        else if (equation.matches("\\\\ln\\\\(.+\\\\)")) return DerivativeEquationType.LOGARITHMIC;
        else if (equation.matches("\\\\sin\\\\(.+\\\\)")
                || equation.matches("\\\\cos\\\\(.+\\\\)")
                || equation.matches("\\\\tan\\\\(.+\\\\)")) return DerivativeEquationType.TRIGONOMETRIC;
        else if (equation.matches("\\\\sinh\\\\(.+\\\\)")
                || equation.matches("\\\\cosh\\\\(.+\\\\)")
                || equation.matches("\\\\tanh\\\\(.+\\\\)")) return DerivativeEquationType.HYPERBOLIC;
        else if (equation.matches("\\\\arcsin\\\\(.+\\\\)")
                || equation.matches("\\\\arccos\\\\(.+\\\\)")
                || equation.matches("\\\\arctan\\\\(.+\\\\)")) return DerivativeEquationType.INVERSE;
        else return DerivativeEquationType.OTHER;
    }

    public static List<DerivativeEquationElement> convertEquationToElements(String equation) {
        List<DerivativeEquationElement> temp = new ArrayList<>();
        boolean coefficientParsed = false;

        for(int i = 0; i < equation.length(); i++) {
            double tempCoefficient = 1.0, tempExponent = 1.0;
            Character tempVariable = null;
            if(Character.isDigit(equation.charAt(i))) {
                if(!coefficientParsed) {
                    tempCoefficient = Double.parseDouble(equation.substring(i, util.untilLetter(i, equation)));
                    i = util.untilLetter(i, equation);
                    coefficientParsed = true;
                } else if (equation.charAt(i - 1) == '^') {
                    tempExponent = Double.parseDouble(equation.substring(i, util.untilOperator(i, equation)));
                    i = util.untilOperator(i, equation);
                    coefficientParsed = false;
                }
            }
            if(Character.isLetter(equation.charAt(i))) {
                tempVariable = equation.charAt(i);
                i++;
            }

            temp.add(new DerivativeEquationElement(tempCoefficient, tempVariable, tempExponent));
        }
        return temp;
    }

    public enum DerivativeType {
        CHAIN_RULE { // Currently only one.
            @Override
            public Double derive(String equation, DerivativeEquationType type) {
                return null;
            }
        }, OTHER {
            @Override
            public Double derive(String equation, DerivativeEquationType type) {
                return null;
            }
        };
        public abstract Double derive(String equation, DerivativeEquationType type);
    }

    public enum DerivativeEquationType {
        ADDITIVE {
            @Override   // f(x) = f'(x)
            public Double derive(String equation) {
                List<DerivativeEquationElement> elements = new ArrayList<>();


                return null;
            }
        }, SUBTRACTIVE {
            @Override   // f(x) = f'(x)
            public Double derive(String equation) {

                return null;
            }
        }, MULTIPLICATIVE {
            @Override   // f(x) * g(x) = f'(x) * g(x) + f(x) * g'(x)
            public Double derive(String equation) {
                return null;
            }
        }, DIVISIVE {
            @Override   // (f(x)/g(x) = f'(x) * g(x) -(f(x) * g'(x))) / g(x)^2
            public Double derive(String equation) {
                return null;
            }
        }, BRACKET {
            @Override   // (f(x))^a = a(f(x))^(a-1) * f'(x)
            public Double derive(String equation) {
                return null;
            }
        }, EXPONENTIAL {
            @Override   // f(x)^g(x) = f(x)^g(x) * ln f(x)
            public Double derive(String equation) {
                return null;
            }
        }, LOGARITHMIC {
            @Override // f(x) = a ln x = d/dx [ a ln x ] = a/x
            public Double derive(String equation) {
                return null;
            }
        }, TRIGONOMETRIC {
            @Override
            public Double derive(String equation) {
                return null;
            }
        }, HYPERBOLIC {
            @Override
            public Double derive(String equation) {
                return null;
            }
        }, INVERSE {
            @Override
            public Double derive(String equation) {
                return null;
            }
        }, OTHER {
            @Override
            public Double derive(String equation) {
                return null;
            }
        };

        public abstract Double derive(String equation);
    }

    public record DerivativeEquationElement(Double coefficient, Character variable, Double exponent) {
        public DerivativeEquationElement(Double coefficient, Character variable) {
            this(coefficient, variable, 1.0);
        }
    }
}
