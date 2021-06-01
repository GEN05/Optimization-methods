package model;

public class GradientDescent extends Method {
    public GradientDescent(double[][] A, double[] B, double C, Point point) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.point = point;
        this.counter = 0L;
    }

    @Override
    public Point calculate() throws Exception {
        Point current = point, gradient;
        double currentValue = Function.calculate(A, B, C, current);
        double λ = 0.01D;
        while (true) {
            gradient = Function.gradient(A, B, current);
            if (module(gradient) < preciseness) {
                return current;
            }
            while (true) {
                checkLimit();
                Point next = calculateNewPoint(current, λ, gradient);
                double nextValue = Function.calculate(A, B, C, next);
                if (nextValue < currentValue) {
                    current = next;
                    currentValue = nextValue;
                    break;
                } else {
                    λ = λ / 2D;
                }
            }
        }
    }
}
