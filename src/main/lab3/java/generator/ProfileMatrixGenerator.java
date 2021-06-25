package generator;

import format.Vector;

import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;

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

    private Vector<Double>[] generateMatrix() {
        Vector<Double>[] matrix = new Vector[n];
        Arrays.setAll(matrix, i -> new Vector<>(n));
        matrix[0].set(0, random.nextDouble() * random.nextInt(diff));
        diagonal.set(0, matrix[0].get(0));
        for (int i = 1; i < n; i++) {
            int firstNotZeroPosition = random.nextInt(i);
            generateFirstNotZero(firstNotZeroPosition, i);
            generateFirstNotZero(i, firstNotZeroPosition);

            for (int j = firstNotZeroPosition + 1; j <= i; j++) {
                generateProfile(i, j);
                generateProfile(j, i);
            }
        }
        return matrix;
    }

    private void generateProfile(final int i, final int j) {
        int next = random.nextInt(3);
        double d = randomNotZeroDouble() * (random.nextInt(diff) + 1);
        switch (next) {
            case 1 -> matrix[i].set(j, -d);
            case 2 -> matrix[i].set(j, d);
            default -> matrix[i].set(j, 0D);
        }
    }

    private void generateFirstNotZero(final int i, final int firstNotZeroPosition) {
        matrix[firstNotZeroPosition].set(i, randomNotZeroDouble() * (random.nextInt(diff) + 1));
        if (random.nextBoolean()) {
            matrix[firstNotZeroPosition].set(i, -matrix[firstNotZeroPosition].get(i));
        }
    }

    private double randomNotZeroDouble() {
        return random.nextDouble() + 0.000001;
    }

    private Vector<Double> generateRightSideVector() {
        Vector<Double> rightSideVector = new Vector<>(n);
        IntStream.range(0, n).forEach(i -> rightSideVector.set(i, random.nextDouble() * random.nextInt(diff)));
        return rightSideVector;
    }

    protected int generateN() {
        int min = 10;
        int max = 20;
        int diff = max - min;
        int n = random.nextInt(diff + 1);
        n += min;
        return n;
    }
}