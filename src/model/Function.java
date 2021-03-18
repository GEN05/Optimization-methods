package model;

public class Function {
    public static double calculate(double x) {
        return x * Math.sin(x) + 2 * Math.cos(x);
    }

    public static double getLeft() {
        return -6.0;
    }

    public static double getRight() {
        return -4.0;
    }
}
