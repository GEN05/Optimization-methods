package methods;

import format.Matrix;
import format.ProfileMatrix;
import format.Vector;

public class LUDecomposition extends Method {
    public Vector<Double> solve(Matrix matrix1) {
        ProfileMatrix matrix = (ProfileMatrix) matrix1;
        int n = matrix.size();
        for (int i = 1; i < n; i++) {
            double cur = 0d;
            for (int j = 0; j < i; j++) {
                cur += matrix.get(i, j) * matrix.getRightSideVectorValue(j);
            }
            matrix.setRightSideVectorValue(i, matrix.getRightSideVectorValue(i) - cur);
        }

        for (int i = n - 1; i >= 0; i--) {
            double cur = 0;
            for (int j = n - 1; j > i; j--) {
                cur += matrix.get(i, j) * matrix.getRightSideVectorValue(j);
            }
            matrix.setRightSideVectorValue(i,
                    (matrix.getRightSideVectorValue(i) - cur)
                            / matrix.get(i, i));
        }
        return matrix.getRightSideVector();
    }
}
