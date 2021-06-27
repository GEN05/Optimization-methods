package methods.quasinewton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class Powell extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps, boolean log, BufferedWriter writer) throws IOException {
        Vector x = new Vector(start), w = Vector.negative(new Vector(functions.gradientValue(x))), p, nextX, nextW;
        int n = w.getCoordinates().length;
        double[][] H = identityMatrix(n);
        if (log) {
            writer.write(x + System.lineSeparator());
        }
        while (norm(w) >= eps && counter < limit) {
            counter++;
            p = Vector.multiply(H, w);
            nextX = Vector.plus(x, Vector.multiply(p, getLambda(functions, x, p)));
            nextW = Vector.negative(new Vector(functions.gradientValue(nextX)));
            H = getNextH(H, Vector.minus(nextX, x), Vector.minus(nextW, w));
            x = nextX;
            w = nextW;
            if (log) {
                writer.write(x + System.lineSeparator());
            }
        }
        return x;
    }

    private double[][] getNextH(final double[][] H, final Vector vectorS, final Vector vectorY) {
        return Vector.minus(
                H, Vector.multiply(
                        Vector.multiply(
                                vectorS.getCoordinates(),
                                vectorS.getCoordinates()
                        ), 1 / IntStream.range(0, vectorS.getCoordinates().length)
                                .mapToDouble(i -> vectorY.getCoordinates()[i] * vectorS.getCoordinates()[i]).sum()
                ));
    }
}
