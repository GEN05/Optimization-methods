package model;

import java.util.ArrayList;

public class Fibonacci extends Method {

    private final ArrayList<Long> fibonacciNumbers = new ArrayList<>();

    @Override
    public double calculate() {
        fibonacciNumbers.add(1L);
        fibonacciNumbers.add(1L);
        while ((right - left) / preciseness >= fibonacciNumbers.get(fibonacciNumbers.size() - 1))
            fibonacciNumbers
                    .add(fibonacciNumbers
                            .get(fibonacciNumbers.size() - 1) + fibonacciNumbers
                            .get(fibonacciNumbers.size() - 2));
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
