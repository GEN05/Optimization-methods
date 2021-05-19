package model;

public class Dichotomy extends Method {
    @Override
    public double calculate() {
        beginTable();
        double delta = preciseness / 10;
        while ((right - left) / 2 > preciseness) {
            double x1 = (left + right - delta) / 2,
                    x2 = (left + right + delta) / 2;
            if (Function.calculate(x1, left, right) > Function.calculate(x2, left, right)) left = x1;
            else right = x2;
        }
        return (left + right) / 2;
    }
}
