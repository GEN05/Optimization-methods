package model;

public class Function {
    private static int counter = 0;

    public static double calculate(double x, double left, double right) {
        double result = x * Math.sin(x) + 2 * Math.cos(x);
        toTable(++counter, left, right, x, result);
        return result;
    }

    public static void toTable(int i, double left, double right, double x, double y) {
        System.out.println(i + " & " +
                String.format("%.8f", left) + " & " +
                String.format("%.8f", right) + " & " +
                String.format("%.8f", right - left) + " & " +
                String.format("%.8f", x) + " & " +
                String.format("%.8f", y) + " \\\\");
    }

    public static void makeNullCounter() {
        counter = 0;
    }

    public static double getLeft() {
        return -6.0;
    }

    public static double getRight() {
        return -4.0;
    }
}
