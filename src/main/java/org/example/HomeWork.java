package org.example;

import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.regex.Pattern;


public class HomeWork {

    private static final Map<String, BiFunction<Double, Double, Double>> FUNCTIONS = Map.of(
                "+" , Double::sum,
                "-", (x, y) -> y - x,
                "*", (x, y) -> y * x,
                "/", (x, y) -> y / x,
                "sin", (x, y) -> Math.sin(x),
                "cos", (x,y) -> Math.cos(x),
                "sqr", (x,y) -> Math.pow(x, 2),
                "pow", Math::pow
        );

    private static final Set<String> OPERATORS = Set.of(
            "+", "-", "/", "*"
    );

    private static final Set<String> BRACKETS = Set.of(
            "(", ")"
    );

   public final static Pattern MATH_FUNC = Pattern.compile("(sin\\(.*\\))|(cos\\(.*\\))|(sqr\\(.*\\))|(pow\\(.*\\))", Pattern.CASE_INSENSITIVE);

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    double calculate(String expr) {

        String[] tokens = expr.trim().split(" ");

        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {

            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    values.push(FUNCTIONS.get(operators.pop())
                            .apply(values.pop(), values.pop()));
                }
                operators.pop();
            } else if (OPERATORS.contains(token)) {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    values.push(FUNCTIONS.get(operators.pop())
                            .apply(values.pop(), values.pop()));
                }
                operators.push(token);
            } else if (MATH_FUNC.matcher(token).matches()) {
                String[] funcAndArgs = token.split("\\(");
                String func = funcAndArgs[0].trim();
                double arg1;
                double arg2 = 0d;
                String[] args = funcAndArgs[1].replaceAll("\\)", "").split(",");
                if(args.length == 2) {
                    arg1 = Double.parseDouble(args[0].trim());
                    arg2 = Double.parseDouble(args[1].trim());
                } else {
                    arg1 = Double.parseDouble(args[0].trim());
                }
                values.push(FUNCTIONS.get(func).apply(arg1, arg2));
            }

            else {
                values.push(Double.parseDouble(token));
            }
        }

        while (!operators.isEmpty()) {
            values.push(FUNCTIONS.get(
                    operators.pop()).apply(values.pop(),
                    values.pop()));
        }

        return values.pop();
    }

    private static boolean hasPrecedence(String operator1, String operator2) {
        if (BRACKETS.contains(operator2)) return false;
        return (!operator1.equals("*") && !operator1.equals("/")) || (!operator2.equals("+") && !operator2.equals("-"));
    }

}
