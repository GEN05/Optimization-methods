package generator;

import format.Vector;

import java.io.File;
import java.util.Arrays;

public class DiagonalDominanceMatrixGenerator extends Generator {
    public DiagonalDominanceMatrixGenerator(String dir, int n, int k) {
        dirName = dir + File.separator;
        this.n = n;
        matrix = generateDiagonallyDominantMatrix();
        diagonal = new Vector<>(n);
        for (int i = 0; i < n; i++) {
            int diag = 0;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                diag += matrix[i].get(j);
            }
            diagonal.set(i, (i == 0 ? Math.pow(10, -k) : 0) - diag);
        }
        profileInfo = countProfile();
        rightSideVector = generateRightSideVectorOneN();

        lowerTriangleElementsByLine = new Vector<>(profileInfo.get(n) - 1);
        lowerTriangleElementsByColumns = new Vector<>(profileInfo.get(n) - 1);
        int pos = 0;
        for (int i = 0; i < n; i++) {
            int cnt = profileInfo.get(i + 1) - profileInfo.get(i);
            for (int j = i - cnt; j < i; j++) {
                lowerTriangleElementsByLine.set(pos, matrix[i].get(j));
                lowerTriangleElementsByColumns.set(pos, matrix[j].get(i));
                pos++;
            }
        }
        write();
    }

    private Vector<Double>[] generateDiagonallyDominantMatrix() {
        Vector<Double>[] matrix = new Vector[n];
        Arrays.setAll(matrix, i -> new Vector<>(n));
        for (int i = 1; i < n; i++) {
            int posOfFirstNotZero = random.nextInt(i);

            matrix[i].set(posOfFirstNotZero, 0D);
            matrix[posOfFirstNotZero].set(i, 0D);
            while (matrix[i].get(posOfFirstNotZero) == 0 || matrix[posOfFirstNotZero].get(i) == 0) {
                matrix[i].set(posOfFirstNotZero, (double) -random.nextInt(5));
                matrix[posOfFirstNotZero].set(i, (double) -random.nextInt(5));
            }

            for (int j = posOfFirstNotZero + 1; j <= i; j++) {
                matrix[i].set(j, (double) -random.nextInt(5));
                matrix[j].set(i, (double) -random.nextInt(5));
            }
        }
        return matrix;
    }
}
