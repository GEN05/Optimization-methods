package model;

public class GoldenRatio extends Method {
    private static final double phi = (1 + Math.sqrt(5)) / 2;

    @Override
    public double calculate() {
        double x1 = left + (right - left) / (phi + 1),
                y1 = Function.calculate(x1),
                x2 = right - (right - left) / (phi + 1),
                y2 = Function.calculate(x2);
        while ((right - left) / 2 > preciseness) {
            if (y1 < y2) {
                right = x2;
                x2 = x1;
                x1 = left + (right - left) / (phi + 1);
            } else {
                left = x1;
                x1 = x2;
                x2 = right - (right - left) / (phi + 1);
            }
        }
        return (left + right) / 2;
    }
}
