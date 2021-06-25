package generator;

import format.Vector;

import java.io.File;

public class GilbertMatrixGenerator extends Generator {
    public GilbertMatrixGenerator(String dir, int n) {
        dirName = dir + File.separator;
        this.n = n;
        matrix = generateGilbertMatrix();
        diagonal = countDiagonal();
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

    protected Vector<Double>[] generateGilbertMatrix() {
        Vector<Double>[] matrix = new Vector[n];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new Vector<>(n);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double temp = i + j + 1;
                matrix[j].set(i, 1 / temp);
                matrix[i].set(j, matrix[j].get(i));
            }
        }
        return matrix;
    }
}