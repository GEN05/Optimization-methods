package model;

public class GradientDescent extends Method {
    private final double[][] A;
    private final double[] B;
    private final double C;
    private final Point point;

    public GradientDescent(double[][] A, double[] B, double C, Point point) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.point = point;
    }

    @Override
    public Point calculate() {
        Point current = point, gradient;
        double currentValue = Function.calculate(A, B, C, current);
        double lambda = 0.01;
        while (true) {
            gradient = Function.gradient(A, B, current);
            if (module(gradient) < preciseness) {
                return current;
            }
            while (true) {
                Point next = calculateNewPoint(current, lambda, gradient);
                double nextValue = Function.calculate(A, B, C, next);
                if (nextValue < currentValue) {
                    current = next;
                    currentValue = nextValue;
                    break;
                } else {
                    lambda = lambda / 2;
                }
            }
        }
    }
}
