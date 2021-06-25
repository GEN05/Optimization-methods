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

public class Main {
    public static void main(String[] args) {
        // Тестирование матриц с диагональным преобладанием
        for (int k = 0; k < 10; k++) {
            for (int n = 10; n <= 1000; n *= 10) {
                new DiagonalDominanceMatrixGenerator("test", n, k);
                ProfileMatrix matrixProfile = new ProfileMatrix("test");
                matrixProfile.decompositionUL();
                Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
                System.out.println(n + " & " + k + " & " + norm(solution) + " \\\\");
            }
        }

        // Тестирование матриц Гильберта
        for (int n = 2; n < 10; n++) {
            new GilbertMatrixGenerator("test", n);
            ProfileMatrix matrixProfile = new ProfileMatrix("test");
            matrixProfile.decompositionUL();
            Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
            System.out.println(n + " & " + norm(solution));
        }
        for (int n = 10; n < 20; n += 2) {
            new GilbertMatrixGenerator("test", n);
            ProfileMatrix matrixProfile = new ProfileMatrix("test");
            matrixProfile.decompositionUL();
            Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
            System.out.println(n + " & " + norm(solution));
        }
        for (int n = 20; n <= 100; n += 10) {
            new GilbertMatrixGenerator("test", n);
            ProfileMatrix matrixProfile = new ProfileMatrix("test");
            matrixProfile.decompositionUL();
            Vector<Double> solution = new LUDecomposition().solve(matrixProfile);
            System.out.println(n + " & " + norm(solution));
        }

        // Сравнение метода Гаусса по точности получаемого решения и по количеству действий с реализованным прямым методом LU-разложения
        Random random = new Random();
        for (int n = 970; n <= 10000; n += 500) {
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

            new DenseProfileFormatMatrixGenerator("test", m);
            ProfileMatrix matrixProfile = new ProfileMatrix("test");
            matrixProfile.decompositionUL();
            Vector<Double> LUSolution = new LUDecomposition().solve(matrixProfile);
            Matrix matrix = new CommonMatrix(m);
            Vector<Double> gaussPivotSolution = new Gauss().solve(matrix);

            System.out.println(n + " & " + norm(LUSolution) + " & " + norm(gaussPivotSolution) + " \\\\");
        }
    }

    private static String norm(final Vector<Double> solution) {
        double sumSub = 0;
        double sumX = 0;
        for (int i = 0; i <= solution.size(); i++) {
            if (i != solution.size()) {
                sumSub += (solution.get(i) - (i + 1)) * (solution.get(i) - (i + 1));
            }
            if (i != 0) {
                sumX += i * i;
            }
        }
        return String.format("%.20f", Math.sqrt(sumSub) / Math.sqrt(sumX)) + " ";
    }
}
