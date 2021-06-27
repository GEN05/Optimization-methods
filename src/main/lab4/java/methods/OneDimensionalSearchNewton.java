package methods;

import util.Functions;
import util.Vector;

import java.util.Arrays;
import java.util.function.Function;

public class OneDimensionalSearchNewton extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps) {
        Vector d, s, x = new Vector(start);
        do {
            System.out.println(x);
            d = slay(functions.hessianValue(x), Arrays.stream(functions.gradientValue(x)).map(y -> -y).toArray());
            double r = getLambda(functions, x, d);
            System.out.println("Одномерное значение " + r);
            s = Vector.multiplyOnNumber(d, r);
            x.plus(s);
        } while (norm(s) >= eps);
        return x;
    }

    private double getLambda(final Functions functions, Vector x, Vector d) {
        Function<Double, Double> f = l -> functions.functionValue(Vector.plus(x, Vector.multiplyOnNumber(d, l)));
        final double PHI = 2 - (1 + Math.sqrt(5)) / 2;
        final double EPS = 1E-5D;
        double a = -1000, b = 1000, x1, x2, f1, f2;
        x1 = a + PHI * (b - a);
        x2 = b - PHI * (b - a);
        f1 = f.apply(x1);
        f2 = f.apply(x2);
        while (Math.abs(b - a) > EPS) {
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = a + PHI * (b - a);
                f1 = f.apply(x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = b - PHI * (b - a);
                f2 = f.apply(x2);
            }
        }
        return (x1 + x2) / 2;
    }
}
