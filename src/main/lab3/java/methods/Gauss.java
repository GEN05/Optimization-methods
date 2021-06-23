package methods;

import java.util.stream.IntStream;

public class Gauss {
    public double[] solve(double[][] matrix) {
        int n = matrix.length, m = matrix.length + 1;

        int[] realRows = IntStream.range(0, n).toArray();

        for (int row = 0; row < n; row++) {
            int sel = row;
            for (int i = row + 1; i < n; i++) {
                if (Math.abs(matrix[realRows[row]][row]) > Math.abs(matrix[realRows[sel]][row])) {
                    sel = i;
                }
            }
            int tmp = realRows[sel];
            realRows[sel] = realRows[row];
            realRows[row] = tmp;

            double c;
            for (int i = row + 1; i < n; ++i) {
                c = matrix[realRows[i]][row] / matrix[realRows[row]][row];
                for (int j = row; j < m; ++j) {
                    matrix[realRows[i]][j] -= matrix[realRows[row]][j] * c;
                }
            }
        }
        double[] answer = new double[n];
        double sum;
        for (int i = n - 1; i >= 0; i--) {
            sum = 0;
            for (int j = n - 1; j > i; j--) {
                sum += matrix[realRows[i]][j] * answer[j];
            }
            answer[i] = (matrix[realRows[i]][n] - sum) / matrix[realRows[i]][i];
        }
        return answer;
    }
}
