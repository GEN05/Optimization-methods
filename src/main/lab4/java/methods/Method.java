package methods;

import util.Functions;
import util.Vector;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class Method {
    public abstract Vector calculate(Functions functions, Vector start, double eps);

    Vector slay(double[][] hessian, double[] gradient) {
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

    double norm(final Vector p) {
        return Math.sqrt(Arrays.stream(p.getCoordinates()).map(x -> x * x).sum());
    }
}
