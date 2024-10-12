package org.example;

import java.util.*;

public class ExpressionEvaluator {
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2,
            "^", 3
    );

    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/", "^");
    private static final Set<String> FUNCTIONS = Set.of("sin", "cos", "sqr", "pow");

    public static double evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        List<String> rpn = toRPN(tokens);
        return calculateRPN(rpn);
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                token.append(c);
            } else {
                if (!token.isEmpty()) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                if (c == ' ' || c == ',') {
                    continue;
                } else if (OPERATORS.contains(String.valueOf(c)) || c == '(' || c == ')') {
                    tokens.add(String.valueOf(c));
                } else if (Character.isLetter(c)) {
                    token.append(c);
                    while (i + 1 < expression.length() && Character.isLetter(expression.charAt(i + 1))) {
                        token.append(expression.charAt(++i));
                    }
                    tokens.add(token.toString());
                    token.setLength(0);
                }
            }
        }
        if (!token.isEmpty()) {
            tokens.add(token.toString());
        }
        return tokens;
    }

    private static List<String> toRPN(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (OPERATORS.contains(token)) {
                while (!operators.isEmpty() && isHigherPrecedence(operators.peek(), token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (FUNCTIONS.contains(token)) {
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop();
                if (!operators.isEmpty() && FUNCTIONS.contains(operators.peek())) {
                    output.add(operators.pop());
                }
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isHigherPrecedence(String op1, String op2) {
        if (!OPERATORS.contains(op1) || !OPERATORS.contains(op2)) {
            return false;
        }
        return OPERATOR_PRECEDENCE.get(op1) >= OPERATOR_PRECEDENCE.get(op2);
    }

    private static double calculateRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (OPERATORS.contains(token)) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> stack.push(a / b);
                    case "^" -> stack.push(Math.pow(a, b));
                }
            } else if (FUNCTIONS.contains(token)) {
                switch (token) {
                    case "sin" -> stack.push(Math.sin(stack.pop()));
                    case "cos" -> stack.push(Math.cos(stack.pop()));
                    case "sqr" -> stack.push(Math.pow(stack.pop(), 2));
                    case "pow" -> {
                        double exponent = stack.pop();
                        double base = stack.pop();
                        stack.push(Math.pow(base, exponent));
                    }
                }
            }
        }
        return stack.pop();
    }
}
