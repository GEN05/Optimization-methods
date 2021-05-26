package controller;

import model.GradientDescent;
import model.Methods;
import model.Point;
import model.SteepestDescent;

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
        SteepestDescent steepestDescent = new SteepestDescent(A, B, C, point, Methods.parabola);
        System.out.println(steepestDescent.calculate());
        steepestDescent = new SteepestDescent(A, B, C, point, Methods.dichotomy);
        System.out.println(steepestDescent.calculate());
        steepestDescent = new SteepestDescent(A, B, C, point, Methods.fibonacci);
        System.out.println(steepestDescent.calculate());
        steepestDescent = new SteepestDescent(A, B, C, point, Methods.goldenRatio);
        System.out.println(steepestDescent.calculate());
        steepestDescent = new SteepestDescent(A, B, C, point, Methods.brent);
        System.out.println(steepestDescent.calculate());
    }
}
