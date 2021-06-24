package generator;

import format.Vector;

import java.io.File;

public class ProfileMatrixGenerator extends Generator {
    public ProfileMatrixGenerator(String dir) {
        dirName = dir + File.separator;

        n = generateN();
        matrix = generateMatrix();
        diagonal = countDiagonal();
        profileInfo = countProfile();
        rightSideVector = generateRightSideVector();

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
}