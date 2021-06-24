package methods;

import format.Matrix;
import format.Vector;

import java.util.stream.IntStream;

public class Gauss extends Method {

    @Override
    public Vector<Double> solve(Matrix matrix) {
        int n = matrix.rowsCount(), m = matrix.columnsCount();

        int[] realRows = IntStream.range(0, n).toArray();

        for (int row = 0; row < n; row++) {
            int sel = row;
            for (int i = row + 1; i < n; i++) {
                if (Math.abs(matrix.get(realRows[row], row)) > Math.abs(matrix.get(realRows[sel], row))) {
                    sel = i;
                }
            }
            int tmp = realRows[sel];
            realRows[sel] = realRows[row];
            realRows[row] = tmp;

            double c;
            for (int i = row + 1; i < n; ++i) {
                c = matrix.get(realRows[i], row) / matrix.get(realRows[row], row);
                for (int j = row; j < m; ++j) {
                    matrix.set(realRows[i], j, matrix.get(realRows[i], j) - (matrix.get(realRows[row], j) * c));
                }
            }
        }
        Vector<Double> answer = new Vector<>(n);
        double sum;
        for (int i = n - 1; i >= 0; i--) {
            sum = 0;
            for (int j = n - 1; j > i; j--) {
                sum += matrix.get(realRows[i], j) * answer.get(j);
            }
            answer.set(i, (matrix.get(realRows[i], n) - sum) / matrix.get(realRows[i], i));
        }
        return answer;
    }
}
