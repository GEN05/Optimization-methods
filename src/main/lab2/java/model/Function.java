package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Function {
    public static double calculate(double[][] A, double[] B, double C, Point point) {
        return 0.5d * multiply(multiply(point.getCoordinates(), A), point.getCoordinates())
                + multiply(B, point.getCoordinates())
                + C;
    }

    public static Point gradient(double[][] A, double[] B, Point point) {
        return new Point(add(multiply(point.getCoordinates(), A), B));
    }

    public static double multiply(double[] a, double[] b) {
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] * b[i]).sum();
    }

    public static double[] multiply(double[] a, double b) {
        return Arrays.stream(a).map(v -> v * b).toArray();
    }

    public static double[] multiply(double[] a, double[][] b) {
        int size = a.length;
        double[] c = new double[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                c[i] += a[j] * b[j][i];
            }
        }
        return c;
    }

    public static double[] add(double[] a, double[] b) {
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] + b[i]).toArray();
    }

    public static double function(Point point) {
        double x = point.getCoordinates()[0];
        double y = point.getCoordinates()[1];
        return 4 * x * x + 6 * x * y + 4 * y * y + 8 * x + 10 * y + 1;
    }
}
