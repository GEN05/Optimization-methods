package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class Method {
    protected double preciseness = 1E-18;
    protected double[][] A;
    protected double[] B;
    protected double C;
    protected Point point;
    protected Methods methods;
    protected long counter;
    protected int limit = 50000;

    public abstract Point calculate() throws Exception;

    public void writeCounter() {
        System.out.println("Количество итераций: " + counter);
    }

    protected Point calculateNewPoint(Point previous, double λ, Point gradient) {
        return new Point(IntStream
                .range(0, previous.getCoordinates().length)
                .mapToDouble(i -> previous.getCoordinates()[i] - λ * gradient.getCoordinates()[i]).toArray());
    }

    protected double module(Point point) {
        return Math.sqrt(Arrays.stream(point.getCoordinates()).map(x -> x * x).sum());
    }

    protected void checkLimit() throws Exception {
        counter++;
        if (counter >= limit) {
            throw new Exception("Превышен лимит итераций");
        }
    }
}
