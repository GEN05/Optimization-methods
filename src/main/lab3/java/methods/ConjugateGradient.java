package methods;

import format.Matrix;
import format.SparseMatrix;
import format.Vector;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class ConjugateGradient extends Method {
    @Override
    public Vector<Double> solve(Matrix matrix1) {
        int n = matrix1.rowsCount();
        SparseMatrix matrix = (SparseMatrix) matrix1;
        Vector<Double> curX = getX0(n), curGradient = getGradient(curX, matrix);
        Vector<Double> curP = multiply(curGradient, -1);
        final double EPS = 1E-7D;
        do {
            for (int i = 0; i < n && getMod(curGradient) > EPS; i++) {
                Vector<Double> curApK = multiply(matrix, curP);
                double alfa = Math.pow(getMod(getGradient(curX, matrix)), 2) /
                        multiply(curApK, curP);
                Vector<Double> newX = countNew(curX, curP, alfa);
                Vector<Double> newGradient = countNew(curGradient, curApK, alfa);
                double betta = (i == 0) ? 0 : Math.pow(getMod(getGradient(newX, matrix)), 2) /
                        Math.pow(getMod(getGradient(curX, matrix)), 2);
                Vector<Double> newP = add(
                        multiply(newGradient, -1),
                        multiply(curP, betta));

                curX = newX;
                curP = newP;
                curGradient = newGradient;
            }
        } while (getMod(curGradient) > EPS);
        return curX;
    }

    private Vector<Double> countNew(Vector<Double> p, Vector<Double> grad, double lambda) {
        int n = p.size();
        Vector<Double> coordinates = new Vector<>(n);
        for (int i = 0; i < n; i++) {
            coordinates.set(i, p.get(i) + lambda * grad.get(i));
        }
        return coordinates;
    }

    private Vector<Double> getX0(final int n) {
        return new Vector<>(DoubleStream.generate(() -> 1).limit(n).boxed());
    }

    private Vector<Double> getGradient(final Vector<Double> x, final SparseMatrix matrix) {
        return minus(multiply(matrix, x), matrix.getRightSideVector());
    }

    private double getMod(final Vector<Double> x) {
        return IntStream.range(0, x.size()).mapToDouble(i -> Math.sqrt(x.get(i) * x.get(i))).sum();
    }

    private Vector<Double> add(final Vector<Double> x, final Vector<Double> y) {
        int n = x.size();
        Vector<Double> res = new Vector<>(n);
        for (int i = 0; i < n; i++) {
            res.set(i, x.get(i) + y.get(i));
        }
        return res;
    }

    public Vector<Double> multiply(final SparseMatrix m, final Vector<Double> x) {
        int n = m.rowsCount();
        Vector<Double> c = new Vector<>(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, c.get(i) + m.get(i, j) * x.get(j));
            }
        }
        return c;
    }

    private double multiply(final Vector<Double> x, final Vector<Double> y) {
        double res = 0;
        for (int i = 0; i < x.size(); i++) {
            res += x.get(i) * y.get(i);
        }
        return res;
    }

    private Vector<Double> multiply(final Vector<Double> x, final double i) {
        Vector<Double> answer = new Vector<>(x.size());
        IntStream.range(0, answer.size()).forEach(j -> answer.set(j, x.get(j) * i));
        return answer;
    }

    private Vector<Double> minus(final Vector<Double> x, final Vector<Double> y) {
        int n = x.size();
        Vector<Double> res = new Vector<>(n);
        for (int i = 0; i < n; i++) {
            res.set(i, x.get(i) - y.get(i));
        }
        return res;
    }
}
