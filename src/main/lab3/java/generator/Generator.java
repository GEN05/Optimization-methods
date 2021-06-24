package generator;

import format.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class Generator {
    protected String dirName;

    protected int n;
    protected Vector<Double>[] matrix;
    protected Vector<Integer> profileInfo;
    protected Vector<Double> diagonal;
    protected Vector<Double> lowerTriangleElementsByLine;
    protected Vector<Double> lowerTriangleElementsByColumns;
    protected Vector<Double> rightSideVector;

    protected Random random;
    protected int diff;

    protected void write() {
        Path dir = Path.of(dirName);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                System.err.println("Cannot create path. " + e.getMessage());
            }
        }

        writeProfileInfo();
        writeDoubleArray(diagonal, dirName + "diagonal");
        writeDoubleArray(lowerTriangleElementsByLine, dirName + "lowerTriangleElementsByLine");
        writeDoubleArray(lowerTriangleElementsByColumns, dirName + "lowerTriangleElementsByColumns");
        writeDoubleArray(rightSideVector, dirName + "rightSideVector");
    }

    protected Vector<Double> generateRightSideVectorOneN() {
        Vector<Double> rightSideVector = new Vector<>(n);
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n; j++) {
                double cur;
                if (i == j - 1) {
                    cur = diagonal.get(i);
                } else {
                    cur = matrix[i].get(j - 1);
                }
                rightSideVector.set(i, rightSideVector.get(i) + cur * j);
            }
        }
        return rightSideVector;
    }

    protected Vector<Double> generateRightSideVector() {
        Vector<Double> rightSideVector = new Vector<>(n);
        IntStream.range(0, n).forEach(i -> rightSideVector.set(i, random.nextDouble() * random.nextInt(diff)));
        return rightSideVector;
    }

    protected Vector<Double> countDiagonal() {
        Vector<Double> diagonal = new Vector<>(n);
        IntStream.range(0, n).forEach(i -> diagonal.set(i, matrix[i].get(i)));
        return diagonal;
    }

    protected int generateN() {
        int min = 10;
        int max = 20;
        int diff = max - min;
        int n = random.nextInt(diff + 1);
        n += min;
        return n;
    }

    protected Vector<Double>[] generateMatrix() {
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

    protected Vector<Double>[] generateDiagonallyDominantMatrix() {
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

    protected void generateFirstNotZero(final int i, final int firstNotZeroPosition) {
        matrix[firstNotZeroPosition].set(i, randomNotZeroDouble() * (random.nextInt(diff) + 1));
        if (random.nextBoolean()) {
            matrix[firstNotZeroPosition].set(i, -matrix[firstNotZeroPosition].get(i));
        }
    }

    protected double randomNotZeroDouble() {
        return random.nextDouble() + 0.000001;
    }

    protected void generateProfile(final int i, final int j) {
        int next = random.nextInt(3);
        double d = randomNotZeroDouble() * (random.nextInt(diff) + 1);
        switch (next) {
            case 1 -> matrix[i].set(j, -d);
            case 2 -> matrix[i].set(j, d);
            default -> matrix[i].set(j, 0D);
        }
    }

    protected Vector<Integer> countProfile() {
        Vector<Integer> ia = new Vector<>(n + 1);
        ia.set(0, 1);
        ia.set(1, 1);
        for (int i = 1; i < n; i++) {
            int tmp = 0;
            for (int j = 0; j < i; j++) {
                if (tmp == 0 && matrix[i].get(j) == 0) {
                    continue;
                }
                tmp++;
            }
            ia.set(i + 1, ia.get(i) + tmp);
        }
        return ia;
    }

    protected void writeProfileInfo() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(dirName + "profileInfo.txt"))) {
            for (int i = 0; i < profileInfo.size(); i++) {
                out.write(profileInfo.get(i) + " ");
            }
        } catch (IOException e) {
            System.err.println("Couldn't write to profileInfo.txt. " + e.getMessage());
        }
    }

    protected void writeDoubleArray(Vector<Double> array, String path) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path + ".txt"))) {
            for (int i = 0; i < array.size(); i++) {
                out.write(array.get(i) + " ");
            }
        } catch (IOException e) {
            System.err.println("Couldn't write to " + path + ". " + e.getMessage());
        }
    }

    protected void writeMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i].get(j) + " ");
            }
            System.out.println();
        }
    }
}
