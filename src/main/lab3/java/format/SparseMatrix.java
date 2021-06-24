package format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class SparseMatrix extends Matrix {
    private Vector<Integer> profileInfoI;
    private Vector<Integer> profileInfoJ;
    private Vector<Double> diagonal;
    private Vector<Double> lowerTriangleElementsByLine;
    private Vector<Double> lowerTriangleElementsByColumns;
    private Vector<Double> rightSideVector;
    private BufferedReader reader;

    public SparseMatrix(String directoryName) {
        directoryName = directoryName + File.separator;
        try {
            reader = getReader(directoryName, "profileInfoI");
            profileInfoI = new Vector<>(Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).boxed());

            reader = getReader(directoryName, "profileInfoJ");
            profileInfoJ = new Vector<>(Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).boxed());


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

    public Vector<Double> getRightSideVector() {
        return rightSideVector;
    }

    private BufferedReader getReader(String directoryName, String diagonal) throws IOException {
        return Files.newBufferedReader(Path.of(directoryName + diagonal + ".txt"));
    }

    private Vector<Double> read() throws IOException {
        return new Vector<>(Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).boxed());
    }

    @Override
    protected double getImpl(int i, int j) {
        boolean isLowerTriangle = false;
        if (i == j) {
            return diagonal.get(i);
        } else if (i > j) {
            isLowerTriangle = true;
        } else {
            int swap = i;
            i = j;
            j = swap;
        }
        final int realPosition = profileInfoI.get(i + 1) - profileInfoI.get(i);
        int offset = 0;
        while (offset < realPosition) {
            if (profileInfoJ.get(profileInfoI.get(i) + offset) == j) {
                break;
            } else if (profileInfoJ.get(profileInfoI.get(i) + offset) > j) {
                return 0D;
            } else {
                offset++;
            }
        }
        return (offset == realPosition) ? 0D :
                isLowerTriangle ? lowerTriangleElementsByLine.get(profileInfoI.get(i) + offset) :
                        lowerTriangleElementsByColumns.get(profileInfoI.get(i) + offset);
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        boolean isLowerTriangle = false;
        if (i == j) {
            diagonal.set(i, value);
        } else if (i > j) {
            isLowerTriangle = true;
        } else {
            int swap = i;
            i = j;
            j = swap;
        }
        final int realPosition = profileInfoI.get(i + 1) - profileInfoI.get(i);
        int offset = 0;
        while (offset < realPosition) {
            if (profileInfoJ.get(profileInfoI.get(i) + offset) == j) {
                break;
            } else if (profileInfoJ.get(profileInfoI.get(i) + offset) > j) {
                return;
            } else {
                offset++;
            }
        }
        if (offset != realPosition) {
            if (isLowerTriangle) {
                lowerTriangleElementsByLine.set(profileInfoI.get(i) + offset, value);
            } else {
                lowerTriangleElementsByColumns.set(profileInfoI.get(i) + offset, value);
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
}
