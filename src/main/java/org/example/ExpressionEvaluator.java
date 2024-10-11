package org.example;

import java.util.*;
import java.util.function.Function;

public class ExpressionEvaluator {

    private static final Map<String, Integer> OPERATOR_PRECEDENCE = new HashMap<>();
    private static final Map<String, Function<Double, Double>> FUNCTIONS = new HashMap<>();

    static {
        OPERATOR_PRECEDENCE.put("+", 1);
        OPERATOR_PRECEDENCE.put("-", 1);
        OPERATOR_PRECEDENCE.put("*", 2);
        OPERATOR_PRECEDENCE.put("/", 2);

        FUNCTIONS.put("sin", ExpressionEvaluator::getSin);
        FUNCTIONS.put("cos",ExpressionEvaluator::getCos);
        FUNCTIONS.put("sqr", x -> x * x);
    }

    public static double getSin(Double degree) {
        return Math.sin(Math.toRadians(degree));
    }

    public static double getCos(Double degree) {
        return Math.cos(Math.toRadians(degree));
    }

    public static double evaluate(String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<String> tokens = tokenize(expression);
        List<String> postfix = infixToPostfix(tokens);
        return evaluatePostfix(postfix);
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/,", true);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (!token.isEmpty()) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private static List<String> infixToPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (FUNCTIONS.containsKey(token)) {
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop(); // Remove the '('
                if (!operators.isEmpty() && FUNCTIONS.containsKey(operators.peek())) {
                    output.add(operators.pop());
                }
            } else if (OPERATOR_PRECEDENCE.containsKey(token)) {
                while (!operators.isEmpty() && OPERATOR_PRECEDENCE.containsKey(operators.peek())
                        && OPERATOR_PRECEDENCE.get(token) <= OPERATOR_PRECEDENCE.get(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (FUNCTIONS.containsKey(token)) {
                double arg = stack.pop();
                stack.push(FUNCTIONS.get(token).apply(arg));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                }
            }
        }

        return stack.pop();
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
