package model;

import java.util.TreeMap;

public class Function {
    public static TreeMap<Integer, ChartIteration> map = new TreeMap<>();
    private static int counter = 0;

    public static double calculate(double x, double left, double right) {
        double result = x * Math.sin(x) + 2 * Math.cos(x);
//        double result = -1 * Math.pow(x, Math.cos(x)) * Math.exp(Math.sin(x));
        toTable(++counter, left, right, x, result);
//        leftArray.add(left);
//        rightArray.add(-right);
        map.put(counter, new ChartIteration(left, right, x, result));
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

    public static class ChartIteration {
        public double left;
        public double right;
        public double minimumX;
        public double minimumY;

        public ChartIteration(double left, double right, double minimumX, double minimumY) {
            this.left = left;
            this.right = right;
            this.minimumX = minimumX;
            this.minimumY = minimumY;
        }

    }
}
