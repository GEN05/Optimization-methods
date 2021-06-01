package model;

import java.util.stream.IntStream;

import static model.Function.*;

public class ConjugateGradient extends Method {
    public ConjugateGradient(double[][] a, double[] b, Point point) {
        this.A = a;
        this.B = b;
        this.point = point;
        this.counter = 0L;
    }

    @Override
    public Point calculateNewPoint(Point previous, double λ, Point gradient) {
        return new Point(IntStream
                .range(0, previous.getCoordinates().length)
                .mapToDouble(i -> previous.getCoordinates()[i] + λ * gradient.getCoordinates()[i]).toArray());
    }

    @Override
    public Point calculate() throws Exception {
        Point x = point;
        Point gradient = gradient(A, B, x);
        Point point = new Point(multiply(gradient.getCoordinates(), -1D));
        Point nextX, nextGradient, nextPoint;
        double[] apK;
        double α, β;
        do {
            for (int i = 0; i < A.length && module(gradient) > preciseness; i++) {
                checkLimit();
                apK = multiply(point.getCoordinates(), A);
                α = Math.pow(module(gradient(A, B, x)), 2D)
                        /
                        multiply(apK, point.getCoordinates());
                nextX = calculateNewPoint(x, α, point);
                nextGradient = calculateNewPoint(gradient, α, new Point(apK));
                β = (i == 0) ? 0D : Math.pow(module(gradient(A, B, x)), 2D) /
                        Math.pow(module(gradient(A, B, x)), 2D);
                nextPoint = new Point(
                        add(
                                multiply(
                                        nextGradient.getCoordinates(), -1D
                                ),
                                multiply(
                                        point.getCoordinates(), β
                                )
                        )
                );
                x = nextX;
                point = nextPoint;
                gradient = nextGradient;
            }
        } while (module(gradient) > preciseness);
        return x;
    }
}
