package generator;

import format.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    protected Random random = new Random(System.currentTimeMillis());
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

    protected Vector<Double> countDiagonal() {
        Vector<Double> diagonal = new Vector<>(n);
        IntStream.range(0, n).forEach(i -> diagonal.set(i, matrix[i].get(i)));
        return diagonal;
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

    private void writeProfileInfo() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(dirName + "profileInfo.txt"))) {
            for (int i = 0; i < profileInfo.size(); i++) {
                out.write(profileInfo.get(i) + " ");
            }
        } catch (IOException e) {
            System.err.println("Couldn't write to profileInfo.txt. " + e.getMessage());
        }
    }

    private void writeDoubleArray(Vector<Double> array, String path) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path + ".txt"))) {
            for (int i = 0; i < array.size(); i++) {
                out.write(array.get(i) + " ");
            }
        } catch (IOException e) {
            System.err.println("Couldn't write to " + path + ". " + e.getMessage());
        }
    }
}
