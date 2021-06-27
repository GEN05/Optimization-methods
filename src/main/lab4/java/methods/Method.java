package methods;

import util.Functions;
import util.Vector;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class Method {
    public abstract Vector calculate(Functions functions, Vector start, double eps);

    protected Vector slay(double[][] hessian, double[] gradient) {
        int n = gradient.length;
        int[] realRows = IntStream.range(0, n).toArray();
        for (int row = 0; row < n; row++) {
            int lastSelected = row;
            for (int i = row + 1; i < n; i++) {
                if (Math.abs(hessian[realRows[i]][row]) > Math.abs(hessian[realRows[lastSelected]][row])) {
                    lastSelected = i;
                }
            }
            int temp = realRows[lastSelected];
            realRows[lastSelected] = realRows[row];
            realRows[row] = temp;
            temp = realRows[lastSelected];
            realRows[lastSelected] = realRows[row];
            realRows[row] = temp;
            for (int i = row + 1; i < n; ++i) {
                double c = hessian[realRows[i]][row] / hessian[realRows[row]][row];
                for (int j = row; j < n; ++j) {
                    hessian[realRows[i]][j] -= hessian[realRows[row]][j] * c;
                }
                gradient[realRows[i]] -= gradient[realRows[row]] * c;
            }
        }
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = n - 1; j > i; j--) {
                sum += hessian[realRows[i]][j] * solution[j];
            }
            solution[i] = (gradient[realRows[i]] - sum) / hessian[realRows[i]][i];
        }
        return new Vector(solution);
    }

    protected double norm(final Vector p) {
        return Math.sqrt(Arrays.stream(p.getCoordinates()).map(x -> x * x).sum());
    }

    protected double getLambda(final Functions functions, Vector x, Vector d) {
        Function<Double, Double> f = l -> functions.functionValue(Vector.plus(x, Vector.multiply(d, l)));
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

    protected double[][] identityMatrix(int n) {
        double[][] H = new double[n][n];
        IntStream.range(0, H.length).forEach(i -> H[i][i] = 1);
        return H;
    }
}
