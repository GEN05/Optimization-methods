package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class Method {
    double preciseness = 0.01;

    public abstract Point calculate();

    public Point calculateNewPoint(Point previous, double lambda, Point gradient) {
        return new Point(IntStream
                .range(0, previous.getCoordinates().length)
                .mapToDouble(i -> previous.getCoordinates()[i] - lambda * gradient.getCoordinates()[i]).toArray());
    }

    public double module(Point point) {
        return Math.sqrt(Arrays.stream(point.getCoordinates()).map(x -> x * x).sum());
    }
}
