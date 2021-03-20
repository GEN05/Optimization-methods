package model;

import static java.lang.Math.abs;
/*
    TODO: в табличках нужно считать количество вызовов "Function.calculate()"
 */

public class Parabola extends Method {
    @Override
    public double calculate() {
        beginTable();
        double y1 = Function.calculate(left, left, right),
                x2 = (right + left) / 2,
                y2 = Function.calculate(x2, left, right),
                y3 = Function.calculate(right, left, right),
                previous = 0,
                x;
        while (true) {
            double a1 = (y2 - y1) / (x2 - left),
                    a2 = (((y3 - y1) / (right - left)) - ((y2 - y1) / (x2 - left))) / (right - x2);
            x = (left + x2 - a1 / a2) / 2;
            double y = Function.calculate(x, left, right);
            if (abs(previous - x) < preciseness)
                return x;

            if (left < x && x < x2 && x2 < right)
                if (y >= y2) {
                    left = x;
                    y1 = y;
                } else {
                    right = x2;
                    x2 = x;
                    y3 = y2;
                    y2 = y;
                }
            else if (left < x2 && x2 < x && x < right)
                if (y2 >= y) {
                    left = x2;
                    x2 = x;
                    y1 = y2;
                    y2 = y;
                } else {
                    right = x;
                    y3 = y;
                }
            previous = x;
        }
    }
}
