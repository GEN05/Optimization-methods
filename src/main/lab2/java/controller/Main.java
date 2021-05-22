package controller;

import model.GradientDescent;
import model.Point;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        double[][] A = new double[][]{
                {8, 6},
                {6, 8}
        };
        double[] B = new double[]{8, 10};
        double C = 1;
        Point point = new Point(1, -1);
        GradientDescent gradientDescent = new GradientDescent(A, B, C, point);
        System.out.println(gradientDescent.calculate());
    }
}
