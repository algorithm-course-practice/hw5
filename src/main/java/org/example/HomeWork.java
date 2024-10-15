package org.example;

import java.util.Stack;

public class HomeWork {

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
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // Уменьшаем, так как цикл увеличивает
            }else if (ch == '(') {
                ops.push(ch);
            } else if (ch == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop(); // Удаляем '('
            } else if (isOperator(ch)) {
                while (!ops.isEmpty() && precedence(ch) <= precedence(ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(ch);
            } else if (expr.startsWith("sqr(", i)) {
                int end = findClosingParen(expr, i + 4);
                values.push(Math.pow(calculate(expr.substring(i + 4, end)), 2));
                i = end; // Устанавливаем i на конец функции
            } else if (expr.startsWith("pow(", i)) {
                int end = findClosingParen(expr, i + 4);
                String[] parts = expr.substring(i + 4, end).split(",");
                values.push(Math.pow(calculate(parts[0]), calculate(parts[1])));
                i = end;
            } else if (expr.startsWith("sin(", i)) {
                int end = findClosingParen(expr, i + 4);
                values.push(Math.sin(calculate(expr.substring(i + 4, end))));
                i = end;
            } else if (expr.startsWith("cos(", i)) {
                int end = findClosingParen(expr, i + 4);
                values.push(Math.cos(calculate(expr.substring(i + 4, end))));
                i = end;
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return 0;
    }

    private static int findClosingParen(String expr, int start) {
        int count = 1;
        for (int i = start; i < expr.length(); i++) {
            if (expr.charAt(i) == '(') count++;
            if (expr.charAt(i) == ')') count--;
            if (count == 0) return i;
        }
        return -1; // Ошибка, если нет закрывающей скобки
    }

}
