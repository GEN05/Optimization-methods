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

    public static Vector plus(Vector vector1, Vector vector2) {
        return new Vector(IntStream.range(0, vector1.coordinates.length).mapToDouble(i -> vector1.coordinates[i] + vector2.coordinates[i]).toArray());
    }

    public static double[][] plus(double[][] matrix1, double[][] matrix2) {
        int n = matrix1.length;
        double[][] answer = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                answer[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return answer;
    }

    public static Vector minus(Vector vector1, Vector vector2) {
        return new Vector(IntStream.range(0, vector1.coordinates.length).mapToDouble(i -> vector1.coordinates[i] - vector2.coordinates[i]).toArray());
    }

    public static double[][] minus(double[][] matrix1, double[][] matrix2) {
        int n = matrix1.length;
        double[][] answer = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                answer[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return answer;
    }

    public static double multiply(Vector vector1, Vector vector2) {
        return IntStream.range(0, vector1.getCoordinates().length).mapToDouble(i -> vector1.get(i) * vector2.get(i)).sum();
    }

    public static Vector multiply(double[][] matrix, Vector vector) {
        int size = matrix.length;
        double[] answer = new double[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                answer[i] += matrix[i][j] * vector.coordinates[j];
            }
        }
        return new Vector(answer);
    }

    public static Vector multiply(Vector vector, double scalar) {
        return new Vector(Arrays.stream(vector.coordinates).map(coordinate -> coordinate * scalar).toArray());
    }

    public static double[][] multiply(double[] vector1, double[] vector2) {
        int n = vector1.length;
        double[][] m = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = vector1[i] * vector2[j];
            }
        }
        return m;
    }

    public static double[][] multiply(double[][] matrix, double number) {
        int n = matrix.length;
        double[][] answer = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                answer[i][j] = matrix[i][j] * number;
            }
        }
        return answer;
    }

    public static double[][] multiply(double[][] matrix1, double[][] matrix2) {
        int n = matrix1.length;
        double[][] answer = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    answer[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return answer;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public double get(int index) {
        return coordinates[index];
    }

    public void plus(final Vector vector) {
        IntStream.range(0, vector.coordinates.length).forEach(i -> coordinates[i] += vector.coordinates[i]);
    }

    @Override
    public String toString() {
        return Arrays.stream(coordinates).mapToObj(coordinate -> String.format("%.20f", coordinate) + " ").collect(Collectors.joining("", "Vector: {", "}"));
    }
}
