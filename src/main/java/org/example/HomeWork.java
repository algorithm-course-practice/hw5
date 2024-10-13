package org.example;

import java.util.*;

import static java.util.Arrays.asList;

public class HomeWork {

    static List<String> OPERATORS = asList("+", "-", "*", "/");

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
    public double calculate(String expr) {
        Stack<Double> numbers = new Stack<>();
        String[] input = expr.split(" ");
        for (String token : input){
            if (isNumber(token)){
                numbers.push(Double.valueOf(token));
                continue;
            }
            double y =numbers.pop();
            switch (token) {
                case ("sin"): {
                    numbers.push(Math.sin(y));
                    continue;
                }
                case ("cos"): {
                    numbers.push(Math.cos(y));
                    continue;
                }
                case ("sqr"): {
                    numbers.push(Math.pow(y, 2));
                }
                continue;
            }
            double x = numbers.pop();
            switch (token) {
                case ("pwr"): {
                    numbers.push(Math.pow(x, y));
                    continue;
                }
                case ("*"): {
                    numbers.push (x * y);
                    continue;
                }
                case ("/"): {
                    numbers.push (x / y);
                    continue;
                }
                case ("+"): {
                    numbers.push (x + y);
                    continue;
                }
                case ("-"): {
                    numbers.push (x - y);
                }
            }

        }
        return numbers.pop();
    }



    /**
     * Алгоритм сортировочной станции
     * c функциями
     * @see <a href="https://ru.wikipedia.org/wiki/Алгоритм_сортировочной_станции">https://ru.wikipedia.org/wiki/Алгоритм_сортировочной_станции</a>
     * @param inputString
     * @return
     */
    static String translate(String inputString) {
        String[] input = inputString.split(" ");
        List<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        for (String cur : input) {
            //Если токен — число, то добавить его в очередь вывода.
            if (isNumber(cur)) {
                output.add(cur);
                continue;
            }
            //Если токен — функция, то поместить его в стэк:
            if(cur.length() > 1){
                stack.push(cur);
                continue;
            }
            //Если токен — разделитель аргументов функции(запятая):
            if (",".equals(cur)){
                while(!stack.isEmpty() && !"(".equals(stack.peek())){
                    output.add(stack.pop());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка, то в выражении пропущена скобка.
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Missing '(' in expression");
                }
            }
            //Если токен — оператор op1, то:
            if (OPERATORS.contains(cur)) {
                //Пока присутствует на вершине стека токен оператор op2, чей приоритет выше или равен приоритету op1,
                // и при равенстве приоритетов op1 является левоассоциативным:
                while (!stack.isEmpty() && !"(".equals(stack.peek())  && priority(stack.peek()) >= priority(cur)) {
                    //Переложить op2 из стека в выходную очередь;
                    output.add(stack.pop());
                }
                //Положить op1 в стек.
                stack.push(cur);
            }
            //Если токен — открывающая скобка, то положить его в стек.
            if ("(".equals(cur)) {
                stack.push(cur);
            }
            //Если токен — закрывающая скобка:
            if (")".equals(cur)) {
                //Пока токен на вершине стека не открывающая скобка
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    //Переложить оператор из стека в выходную очередь.
                    output.add(stack.pop());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка, то в выражении пропущена скобка.
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Missing '(' in expression");
                }
                //Выкинуть открывающую скобку из стека, но не добавлять в очередь вывода.
                stack.pop();
                //Если токен на вершине стэка функция, то переложить ее в выходную очередь:
                if (!stack.isEmpty() && stack.peek().length() > 1){
                    output.add(stack.pop());
                }
            }
        }
        //Если больше не осталось токенов на входе:
        //Пока есть токены операторы в стеке:
        while (!stack.isEmpty()) {
            //Если токен оператор на вершине стека — открывающая скобка, то в выражении пропущена скобка.
            if ("(".equals(stack.peek())) {
                throw new IllegalArgumentException("Missing ')' in expression");
            }
            //Переложить оператор из стека в выходную очередь.
            output.add(stack.pop().toString());
        }
        return String.join(" ", output);
    }

    private static boolean isNumber(String number) {
        try{
            Integer.valueOf(number);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private static int priority(String operator) {
        return ("+".equals(operator) || "-".equals(operator)) ? 1 : 2;
    }

}
