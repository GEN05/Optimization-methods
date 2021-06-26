import format.CommonMatrix;
import format.Matrix;
import format.ProfileMatrix;
import format.Vector;
import generator.DenseProfileFormatMatrixGenerator;
import generator.DiagonalDominanceMatrixGenerator;
import generator.GilbertMatrixGenerator;
import methods.Gauss;
import methods.LUDecomposition;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        // Тестирование матриц с диагональным преобладанием
        for (int k = 0; k < 10; k++) {
            for (int n = 10; n <= 1000; n *= 10) {
                new DiagonalDominanceMatrixGenerator("test1", n, k);
                ProfileMatrix matrixProfile = new ProfileMatrix("test1");
                matrixProfile.decompositionUL();
                Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
                System.out.println(n + " & " + k + " & " + norm(solution) + " \\\\");
            }
        }

        // Тестирование матриц Гильберта
        for (int n = 2; n < 10; n++) {
            new GilbertMatrixGenerator("test2", n);
            ProfileMatrix matrixProfile = new ProfileMatrix("test2");
            matrixProfile.decompositionUL();
            Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
            System.out.println(n + " & " + norm(solution));
        }
        for (int n = 10; n < 30; n += 2) {
            new GilbertMatrixGenerator("test3", n);
            ProfileMatrix matrixProfile = new ProfileMatrix("test3");
            matrixProfile.decompositionUL();
            Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
            System.out.println(n + " & " + norm(solution));
        }
        for (int n = 30; n <= 100; n += 10) {
            new GilbertMatrixGenerator("test4", n);
            ProfileMatrix matrixProfile = new ProfileMatrix("test4");
            matrixProfile.decompositionUL();
            Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
            System.out.println(n + " & " + norm(solution));
        }

        // Сравнение метода Гаусса по точности получаемого решения и по количеству действий с реализованным прямым методом LU-разложения
        Random random = new Random(System.currentTimeMillis());
        for (int n = 990; n <= 10000; n += 1000) {
            Vector<Double>[] m = new Vector[n];
            for (int i = 0; i < m.length; i++) {
                m[i] = new Vector<>(n + 1);
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    m[i].set(j, (random.nextDouble() + 0.0001) * (random.nextInt(100) + 1));
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 1; j <= n; j++) {
                    m[i].set(n, m[i].get(n) + m[i].get(j - 1) * j);
                }
            }

            new DenseProfileFormatMatrixGenerator("test5", m);
            ProfileMatrix matrixProfile = new ProfileMatrix("test5");
            matrixProfile.decompositionUL();
            Vector<Double> LUSolution = new LUDecomposition().solve(matrixProfile);
            Matrix matrix = new CommonMatrix(m);
            Vector<Double> gaussPivotSolution = new Gauss().solve(matrix);

            System.out.println(n + " & " + norm(LUSolution) + " & " + norm(gaussPivotSolution) + " \\\\");
        }
    }

    private static String norm(final Vector<Double> solution) {
        double sumSub = IntStream.rangeClosed(0, solution.size()).filter(i -> i != solution.size()).mapToDouble(i -> (solution.get(i) - (i + 1)) * (solution.get(i) - (i + 1))).sum();
        return String.format("%.20f", Math.sqrt(sumSub)) + " ";
    }
}
