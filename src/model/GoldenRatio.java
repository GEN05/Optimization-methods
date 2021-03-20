package model;

public class GoldenRatio extends Method {
    private static final double phi = (1 + Math.sqrt(5)) / 2;

    @Override
    public double calculate() {
        double x1 = right - (right - left) / phi,
                y1 = Function.calculate(x1),
                x2 = left + (right - left) / phi,
                y2 = Function.calculate(x2);
        while (Math.abs(right - left) / 2 > preciseness) {
            if (y1 < y2) {
                right = x2;
                x2 = x1;
                y2 = y1;
                x1 = right - (right - left) / phi;
                y1 = Function.calculate(x1);
            } else {
                left = x1;
                x1 = x2;
                y1 = y2;
                x2 = left + (right - left) / phi;
                y2 = Function.calculate(x2);
            }
        }
        return (left + right) / 2;
    }
}
