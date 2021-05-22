package model;

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

    private static double multiply(double[] a, double[] b) {
        return IntStream.range(0, a.length).mapToDouble(i -> a[i] * b[i]).sum();
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
}
