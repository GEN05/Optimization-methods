package format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class ProfileMatrix {
    private int[] profileInfo;
    private double[] diagonal;
    private double[] lowerTriangleElementsByLine;
    private double[] lowerTriangleElementsByColumns;
    private double[] rightSideVector;
    private BufferedReader reader;

    public ProfileMatrix(String directoryName) {
        directoryName = directoryName + File.separator;
        try {
            reader = getReader(directoryName, "profileInfo");
            profileInfo = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

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
        return diagonal.length;
    }

    public double getLUValue(int i, int j) {
        boolean isUpperTriangle = false;
        if (i == j) {
            return diagonal[i];
        } else if (j > i) {
            isUpperTriangle = true;
            int swap = j;
            j = i;
            i = swap;
        }
        int iStringProfile = profileInfo[i + 1] - profileInfo[i]; // profile of i-th string
        int firstPosition = profileInfo[i] - 1; // position of first element of i-th string in al/au
        int realPosition = i - iStringProfile;
        if (j < realPosition) { // beginning zero
            return 0d;
        }
        int position = firstPosition + j - realPosition;
        return isUpperTriangle ? lowerTriangleElementsByColumns[position] : lowerTriangleElementsByLine[position];
    }

    public double[] getRightSideVector() {
        return rightSideVector;
    }

    public double getRightSideVectorValue(int index) {
        return rightSideVector[index];
    }

    public void setRightSideVectorValue(int index, double value) {
        rightSideVector[index] = value;
    }

    private BufferedReader getReader(String directoryName, String diagonal) throws IOException {
        return Files.newBufferedReader(Path.of(directoryName + diagonal + ".txt"));
    }

    private double[] read() throws IOException {
        return Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
    }
}
