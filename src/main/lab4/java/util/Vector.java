package util;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Vector {
    private final double[] coordinates;

    public Vector(double... coordinates) {
        this.coordinates = coordinates;
    }

    public Vector(Vector vector) {
        this.coordinates = new double[vector.coordinates.length];
        System.arraycopy(vector.coordinates, 0, coordinates, 0, vector.coordinates.length);
    }

    public static Vector negative(Vector vector) {
        return new Vector(Arrays.stream(vector.coordinates).map(a -> -a).toArray());
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public double get(int index) {
        return coordinates[index];
    }

    public static Vector plus(Vector vector1, Vector vector2) {
        return new Vector(IntStream.range(0, vector1.coordinates.length).mapToDouble(i -> vector1.coordinates[i] + vector2.coordinates[i]).toArray());
    }

    public void plus(final Vector vector) {
        IntStream.range(0, vector.coordinates.length).forEach(i -> coordinates[i] += vector.coordinates[i]);
    }

    public static double multiply(Vector x, Vector y) {
        return IntStream.range(0, x.getCoordinates().length).mapToDouble(i -> x.get(i) * y.get(i)).sum();
    }

    @Override
    public String toString() {
        return Arrays.stream(coordinates).mapToObj(coordinate -> String.format("%.20f", coordinate) + " ").collect(Collectors.joining("", "Point: {", "}"));
    }

    public static Vector multiplyOnNumber(Vector vector, double scalar) {
        return new Vector(Arrays.stream(vector.coordinates).map(coordinate -> coordinate * scalar).toArray());
    }
}
