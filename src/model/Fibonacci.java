package model;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Fibonacci extends Method {

    private final ArrayList<Long> fibonacciNumbers = new ArrayList<>();

    public static long generateFibonacciNumbers(int n) {
        switch (n) {
            case 0:
                return 0;
            case 1:
            case 2:
                return 1;
        }
        int result = 1;
        for (int i = 0, previous = -1, sum; i <= n; i++) {
            sum = result + previous;
            previous = result;
            result = sum;
        }
        return result;
    }

    @Override
    public double calculate() {
        IntStream
                .iterate(0, i ->
                        fibonacciNumbers
                                .get(fibonacciNumbers.size() - 1) > (right - left) / preciseness, i ->
                        i + 1)
                .forEach(i ->
                        fibonacciNumbers
                                .add(generateFibonacciNumbers(i)));
        int size = fibonacciNumbers.size();
        double x1 = left + (right - left) * ((double) fibonacciNumbers.get(size - 3) / fibonacciNumbers.get(size - 1)),
                y1 = Function.calculate(x1),
                x2 = left + (right - left) * ((double) fibonacciNumbers.get(size - 2) / fibonacciNumbers.get(size - 1)),
                y2 = Function.calculate(x2);

        for (int i = 1; i < size - 2; i++) {
            if (y1 > y2) {
                left = x1;
                x1 = x2;
                x2 = left + (right - left) * ((double) fibonacciNumbers.get(size - i - 2) / fibonacciNumbers.get(size - i - 1));
                y1 = y2;
                y2 = Function.calculate(x2);
            } else {
                right = x2;
                x2 = x1;
                x1 = left + (right - left) * ((double) fibonacciNumbers.get(size - i - 3) / fibonacciNumbers.get(size - i - 1));
                y2 = y1;
                y1 = Function.calculate(x1);
            }
        }

        return (left + right) / 2;
    }
}
