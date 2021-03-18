package model;

import static java.lang.Math.abs;

public class Parabola extends Method {
    @Override
    public double calculate() {
        double x1 = left,
                y1 = Function.calculate(x1),
                x2 = (right + left) / 2,
                y2 = Function.calculate(x2),
                x3 = right,
                y3 = Function.calculate(x3),
                previous = 0,
                x;
        while (true) {
            double a1 = (y2 - y1) / (x2 - x1),
                    a2 = (((y3 - y1) / (x3 - x1)) - ((y2 - y1) / (x2 - x1))) / (x3 - x2);
            x = (x1 + x2 - a1 / a2) / 2;
            double y = Function.calculate(x);
            if (abs(previous - x) < preciseness)
                return x;

            if (x1 < x && x < x2 && x2 < x3)
                if (y >= y2) {
                    x1 = x;
                    y1 = y;
                } else {
                    x3 = x2;
                    x2 = x;
                    y3 = y2;
                    y2 = y;
                }
            else if (x1 < x2 && x2 < x && x < x3)
                if (y2 >= y) {
                    x1 = x2;
                    x2 = x;
                    y1 = y2;
                    y2 = y;
                } else {
                    x3 = x;
                    y3 = y;
                }
            previous = x;
        }
    }
}
