package format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class ProfileMatrix extends Matrix {
    private Vector<Integer> profileInfo;
    private Vector<Double> diagonal;
    private Vector<Double> lowerTriangleElementsByLine;
    private Vector<Double> lowerTriangleElementsByColumns;
    private Vector<Double> rightSideVector;
    private BufferedReader reader;

    public ProfileMatrix(String directoryName) {
        directoryName = directoryName + File.separator;
        try {
            reader = getReader(directoryName, "profileInfo");
            profileInfo = new Vector<>(Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).boxed());

            reader = getReader(directoryName, "diagonal");
            diagonal = read();

            reader = getReader(directoryName, "lowerTriangleElementsByLine");
            lowerTriangleElementsByLine = read();

            reader = getReader(directoryName, "lowerTriangleElementsByColumns");
            lowerTriangleElementsByColumns = read();

            reader = getReader(directoryName, "rightSideVector");
            rightSideVector = read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(reader).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        return diagonal.size();
    }

    public Vector<Double> getRightSideVector() {
        return rightSideVector;
    }

    public double getRightSideVectorValue(int index) {
        return rightSideVector.get(index);
    }

    public void setRightSideVectorValue(int index, double value) {
        rightSideVector.set(index, value);
    }

    private BufferedReader getReader(String directoryName, String diagonal) throws IOException {
        return Files.newBufferedReader(Path.of(directoryName + diagonal + ".txt"));
    }

    private Vector<Double> read() throws IOException {
        return new Vector<>(Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).boxed());
    }

    @Override
    protected double getImpl(int i, int j) {
        boolean isUpperTriangle = false;
        if (i == j) {
            return diagonal.get(i);
        } else if (j > i) {
            isUpperTriangle = true;
            int swap = j;
            j = i;
            i = swap;
        }
        int realPosition = i - (profileInfo.get(i + 1) - profileInfo.get(i));
        if (j < realPosition) { // beginning zero
            return 0d;
        }
        int position = profileInfo.get(i) - 1 + j - realPosition;
        return isUpperTriangle ? lowerTriangleElementsByColumns.get(position) : lowerTriangleElementsByLine.get(position);
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        boolean isUpperTriangle = false;
        if (i == j) {
            diagonal.set(i, value);
            return;
        } else if (j > i) {
            isUpperTriangle = true;
            int swap = j;
            j = i;
            i = swap;
        }
        int realPosition = i - (profileInfo.get(i + 1) - profileInfo.get(i));
        if (j < realPosition) { // beginning zero
            return;
        }
        int position = profileInfo.get(i) - 1 + j - realPosition;
        if (isUpperTriangle) {
            lowerTriangleElementsByColumns.set(position, value);
        } else {
            lowerTriangleElementsByLine.set(position, value);
        }
    }

    public void decompositionUL() {
        int n = diagonal.size();
        IntStream.range(1, n).forEach(j -> set(j, 0, get(j, 0) / get(0, 0)));
        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += get(i, k) * get(k, j);
                }
                set(i, j, get(i, j) - sum);
            }
            for (int j = i + 1; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += get(j, k) * get(k, i);
                }
                set(j, i, (get(j, i) - sum) / get(i, i));
            }
        }
    }

    @Override
    public int rowsCount() {
        return diagonal.size();
    }

    @Override
    public int columnsCount() {
        return diagonal.size();
    }

    @Override
    public String toString() {
        return "ProfileMatrix{" +
                "profileInfo=" + profileInfo +
                ", diagonal=" + diagonal +
                ", lowerTriangleElementsByLine=" + lowerTriangleElementsByLine +
                ", lowerTriangleElementsByColumns=" + lowerTriangleElementsByColumns +
                ", rightSideVector=" + rightSideVector +
                ", reader=" + reader +
                '}';
    }
}
