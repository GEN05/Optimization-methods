package model;

public class GradientDescent extends Method {
    private final double[][] A;
    private final double[] B;
    private final double C;
    private final Point point;

    public GradientDescent(double[][] a, double[] b, double c, Point point) {
        A = a;
        B = b;
        C = c;
        this.point = point;
    }

    @Override
    public Point calculate() {
        Point cur = point, gradient;
        double curValue = Function.calculate(A, B, C, cur);
        double lambda = 0.01;
        while (true) {
            gradient = Function.gradient(A, B, cur);
            if (module(gradient) < preciseness) {
                return cur;
            }
            while (true) {
                Point next = calculateNewPoint(cur, lambda, gradient);
                double nextValue = Function.calculate(A, B, C, next);
                if (nextValue < curValue) {
                    cur = next;
                    curValue = nextValue;
                    break;
                } else {
                    lambda = lambda / 2;
                }
            }
        }
    }
}
