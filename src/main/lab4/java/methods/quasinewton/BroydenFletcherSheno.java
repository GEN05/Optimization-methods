package methods.quasinewton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class BroydenFletcherSheno extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps, boolean log, BufferedWriter writer) throws IOException {
        Vector x = new Vector(start), p, d, nextX, nextGrad, gradient = new Vector(functions.gradientValue(x));
        int n = gradient.getCoordinates().length;
        double[][] h = identityMatrix(n);
        if (log) {
            writer.write(x + System.lineSeparator());
        }
        double alpha;
        while (norm(gradient) >= eps && counter < limit) {
            counter++;
            p = Vector.negative(Vector.multiply(h, gradient));
            alpha = getLambda(functions, x, p);
            d = Vector.multiply(p, alpha);
            nextX = Vector.plus(x, d);
            nextGrad = new Vector(functions.gradientValue(nextX));
            h = getNextH(h, Vector.minus(nextX, x), Vector.minus(nextGrad, gradient));
            x = nextX;
            gradient = nextGrad;
            if (log) {
                writer.write(x + System.lineSeparator());
            }
        }
        return x;
    }

    private double[][] getNextH(final double[][] H, final Vector vectorS, final Vector vectorY) {
        double[] s = vectorS.getCoordinates();
        double[] y = vectorY.getCoordinates();
        int n = s.length;
        double p = 1 / IntStream.range(0, n).mapToDouble(i -> y[i] * s[i]).sum();
        return Vector.plus(
                Vector.multiply(
                        Vector.multiply(
                                Vector.minus(
                                        identityMatrix(n),
                                        Vector.multiply(Vector.multiply(s, y), p)
                                ), H
                        ), Vector.minus(
                                identityMatrix(n),
                                Vector.multiply(Vector.multiply(y, s), p)
                        )
                ), Vector.multiply(Vector.multiply(s, s), p)
        );
    }
}
