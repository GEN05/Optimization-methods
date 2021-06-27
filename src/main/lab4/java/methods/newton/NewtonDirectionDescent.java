package methods.newton;

import methods.Method;
import util.Functions;
import util.Vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class NewtonDirectionDescent extends Method {
    @Override
    public Vector calculate(Functions functions, Vector start, double eps, boolean log, BufferedWriter writer) throws IOException {
        Vector x = new Vector(start);
        if (log) {
            writer.write(x + System.lineSeparator());
        }
        Vector d;
        Vector s;
        do {
            counter++;
            Vector g = new Vector(functions.gradientValue(x));
            s = slay(functions.hessianValue(x), Arrays.stream(new Vector(g.getCoordinates()).getCoordinates()).map(y -> -y).toArray());
            d = Vector.multiply(s, g) < 0 ? s : Vector.negative(g);
            s = getVector(functions, x, d);
            if (log) {
                writer.write(x + System.lineSeparator());
            }
        } while (norm(s) >= eps && counter < limit);
        return x;
    }

    private Vector getVector(Functions functions, Vector x, Vector d) {
        double r;
        Vector s;
        r = getLambda(functions, x, d);
        s = Vector.multiply(d, r);
        x.plus(s);
        return s;
    }
}
