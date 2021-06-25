package generator;

import format.Vector;

import java.io.File;
import java.util.stream.IntStream;

public class DenseProfileFormatMatrixGenerator extends Generator {
    public DenseProfileFormatMatrixGenerator(String dir, Vector<Double>[] m) {
        dirName = dir + File.separator;
        n = m.length;
        matrix = m;
        diagonal = countDiagonal();
        profileInfo = countProfile();
        rightSideVector = new Vector<>(n);
        IntStream.range(0, rightSideVector.size()).forEach(i -> rightSideVector.set(i, m[i].get(n)));
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
