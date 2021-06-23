package methods;

import format.ProfileMatrix;

public class LU {
    public double[] solve(ProfileMatrix matrix) {
        int n = matrix.size();
        for (int i = 1; i < n; i++) {
            double cur = 0d;
            for (int j = 0; j < i; j++) {
                cur += matrix.getLUValue(i, j) * matrix.getRightSideVectorValue(j);
            }
            matrix.setRightSideVectorValue(i, matrix.getRightSideVectorValue(i) - cur);
        }

        for (int i = n - 1; i >= 0; i--) {
            double cur = 0;
            for (int j = n - 1; j > i; j--) {
                cur += matrix.getLUValue(i, j) * matrix.getRightSideVectorValue(j);
            }
            matrix.setRightSideVectorValue(i, (matrix.getRightSideVectorValue(i) - cur) / matrix.getLUValue(i, i));
        }
        return matrix.getRightSideVector();
    }
}
